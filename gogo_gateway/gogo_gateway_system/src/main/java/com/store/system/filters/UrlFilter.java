package com.store.system.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @program: gogo-store
 * @description: UrlFilter url获取
 * @author: xiaozhang6666
 * @create: 2020-10-29 17:16
 **/
@Component
@Order(3)
public class UrlFilter implements GlobalFilter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final String path = request.getURI().getPath();
        logger.info("当前请求的 uri 地址是 ===== {}", path);
        return chain.filter(exchange);
    }
}
