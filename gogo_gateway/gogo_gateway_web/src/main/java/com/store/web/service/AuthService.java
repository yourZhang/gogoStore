package com.store.web.service;

import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 *
 */
public interface AuthService {

    /**
     * 从消费者cookie中获取jti短令牌
     * @param request
     * @return
     */
    public String findJtiByCookie(ServerHttpRequest request);

    /**
     * 根据jti短令牌到redis中获取jwt长令牌
     * @param jti 短令牌
     * @return
     */
    public String findJwtByJtiFromRedis(String jti);
}
