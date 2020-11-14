package com.store.oauth.service;



import com.store.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    //jti和jwt存入redis中的生存时间
    @Value("${auth.ttl}")
    private long ttl;

    @Override
    public AuthToken login(String userName, String password, String clientId, String clientSecurity) {
        //1. 到Eureka中获取认证服务器的ip地址和端口号
        ServiceInstance choose = loadBalancerClient.choose("user-auth");

        //2. 将认证微服务的ip和端口 + oauth2的认证地址拼接成完整的url请求路径
        String url = choose.getUri() + "/oauth/token";
       // String url = "http://localhost:9200/oauth/token";

        //3. 封装请求头, 使用httpBasic协议封装, 客户端id和秘钥
        MultiValueMap head = new LinkedMultiValueMap();
        head.add("Authorization", httpBaic(clientId, clientSecurity));

        //4. 封装请求体, 封装消费者用户名, 密码
        MultiValueMap body = new LinkedMultiValueMap();
        //设置认证模式为密码模式
        body.add("grant_type", "password");
        //设置消费者用户名
        body.add("username", userName);
        //设置消费者密码
        body.add("password", password);
        //5. 将请求头和请求体合成请求对象
        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(body, head);
        //6. 设置发送请求时候, 自定义异常处理信息
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            //设置发送请求遇到的异常处理, 如果遇到400或401代表权限不足, 不算异常不抛出
            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                //判断如果异常不等于400和401调用父级异常处理方法进行处理
                if (clientHttpResponse.getRawStatusCode() != 400 && clientHttpResponse.getRawStatusCode() != 401) {
                    super.handleError(clientHttpResponse);
                }
            }
        });
        //7. 发送请求到上面封装的url地址中, 携带请求对象, 返回响应, 如果认证成功, 响应中有jwt和jti等
        //参数1: 请求地址, 参数2:请求类型, 参数3: 请求实体对象, 参数4:返回值类型
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
        if (responseEntity == null) {
            throw new RuntimeException("请求异常, 请检查所有封装的参数, 有错误的地方");
        }
        //8. 判断jwt和jti是否为空, 如果不为空将jwt和jti封装成AuthToken实体对象
        Map<String, String> resultMap = responseEntity.getBody();
        if (resultMap == null) {
            throw new RuntimeException("请求异常, 请检查所有封装的参数, 有错误的地方");
        }
        if (StringUtils.isEmpty(resultMap.get("access_token"))
                || StringUtils.isEmpty(resultMap.get("refresh_token"))
                || StringUtils.isEmpty(resultMap.get("jti"))) {
            throw new RuntimeException("请求异常, 请检查所有封装的参数, 有错误的地方");
        }
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken(resultMap.get("access_token"));
        authToken.setRefreshToken(resultMap.get("refresh_token"));
        authToken.setJti(resultMap.get("jti"));
        //9. 将jti作为key, jwt作为value存入redis中保存
        redisTemplate.boundValueOps(authToken.getJti()).set(authToken.getAccessToken(), ttl, TimeUnit.SECONDS);
        //10. 返回AuthToken实体对象
        return authToken;
    }

    /**
     * 使用HttpBasic协议格式封装客户端id和秘钥
     * 封装完格式例如: Basic 客户端id(base64编码):客户端秘钥(base64编码)
     *
     * @param clientId       客户端id
     * @param clientSecurity 客户端秘钥
     * @return
     */
    private String httpBaic(String clientId, String clientSecurity) {
        String str = clientId + ":" + clientSecurity;
        //使用base64编码
        byte[] encode = Base64Utils.encode(str.getBytes());
        //组成httpBasic格式
        return "Basic " + new String(encode);
    }
}
