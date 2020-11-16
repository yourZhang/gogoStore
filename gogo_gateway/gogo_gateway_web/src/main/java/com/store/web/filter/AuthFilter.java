package com.store.web.filter;

import com.store.web.service.AuthService;
import com.store.web.utils.UrlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 消费者鉴权过滤器
 * 拦截消费者所有请求, 判断是放行还是应该具有权限
 * 进行权限鉴定业务处理
 */
@Component
public class AuthFilter implements Ordered, GlobalFilter {

    @Autowired
    private AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1. 获取请求对象
        ServerHttpRequest request = exchange.getRequest();
        //2. 获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        //3. 获取用户请求的访问路径
        String path = request.getURI().getPath();
        //4. 判断如果是订单路径, 如果是购物车路径, 支付路径等拦截, 其他路径放行
        if (!UrlFilter.hasAuthorize(path)) {
            //放行
            return chain.filter(exchange);
        }
        //5. 从消费者浏览器cookie中获取jti短令牌
        String jti = authService.findJtiByCookie(request);
        //6. 判断jti短令牌是否为空, 为空重定向到认证微服务从新登录
        if (StringUtils.isEmpty(jti)) {
            //重定向到认证微服务的登录页面路径
            return sendRedirect(exchange, "http://web.changgou.com:8001/api/oauth/toLogin");
        }
        //7. 根据jti短令牌到redis中获取jwt长令牌
        String jwt = authService.findJwtByJtiFromRedis(jti);
        //8. 判断jwt长令牌是否为空, 如果为空重定向到认证微服务从新登录
        if (StringUtils.isEmpty(jwt)) {
            //重定向到认证微服务的登录页面路径
            return sendRedirect(exchange, "http://web.changgou.com:8001/api/oauth/toLogin");
        }
        //9. 将jwt长令牌放到当前消费者请求的请求头中放行到业务微服务
        request.mutate().header("authorization", "bearer "+ jwt);
        //10. 消费者请求放行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 封装重定向方法
     * @param exchange  请求响应对象
     * @param url   重定向的地址
     * @return
     */
    private Mono<Void> sendRedirect(ServerWebExchange exchange, String url) {
        //1. 获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        //2. 设置响应状态码
        response.setStatusCode(HttpStatus.SEE_OTHER);
        //3. 设置响应头中的重定向地址
        response.getHeaders().set("Location", url);
        //4. 返回响应
        return response.setComplete();
    }
}
