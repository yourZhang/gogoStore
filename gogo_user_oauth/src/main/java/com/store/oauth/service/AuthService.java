package com.store.oauth.service;


import com.store.oauth.util.AuthToken;

/**
 *
 */
public interface AuthService {

    /**
     * 消费者登录
     * 使用oauth2协议密码模式, 封装请求头封装请求体, 发送给springSecurityOauth2框架规定的地址
     * 触发springSecurityOauth2框架去调用UserDetailsService实现类, 在整个实现类中,
     * 校验客户端id和客户端秘钥是否正确, 校验消费者用户名和密码是否正确, 如果都正确.
     * springSecurityOauth2框架根据秘钥库的私钥文件生成jwt, jti等数据返回
     * @param userName  消费者用户名
     * @param password  消费者密码
     * @param clientId  客户端id
     * @param clientSecurity    客户端秘钥
     */
    public AuthToken login(String userName, String password, String clientId, String clientSecurity);
}
