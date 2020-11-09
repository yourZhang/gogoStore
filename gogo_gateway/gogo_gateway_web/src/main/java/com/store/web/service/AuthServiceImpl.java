package com.store.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String findJtiByCookie(ServerHttpRequest request) {
        HttpCookie httpCookie = request.getCookies().getFirst("jti");
        if (httpCookie != null) {
            return httpCookie.getValue();
        }
        return null;
    }

    @Override
    public String findJwtByJtiFromRedis(String jti) {
        Object objJwt = redisTemplate.boundValueOps(jti).get();
        if (objJwt != null) {
            return String.valueOf(objJwt);
        }
        return null;
    }
}
