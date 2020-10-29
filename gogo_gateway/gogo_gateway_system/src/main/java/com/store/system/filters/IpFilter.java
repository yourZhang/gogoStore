package com.store.system.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @program: gogo-store
 * @description: IpFilter ip过滤器
 * @author: xiaozhang6666
 * @create: 2020-10-29 17:06
 **/
@Component
@Order(0)
//, Ordered
public class IpFilter implements GlobalFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
        获取ip地址
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final String hostName = request.getRemoteAddress().getHostName();
        logger.info("当前请求的 ip 地址是 ===== {}", hostName);
        return chain.filter(exchange);
    }
}
