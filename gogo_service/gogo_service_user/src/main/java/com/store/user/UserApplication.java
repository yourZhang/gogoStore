package com.store.user;


import com.store.interceptor.FeignInterceptor;
import com.store.user.config.TokenDecode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.store.user.mapper"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run( UserApplication.class);
    }

    /**
     * 将tokenDecode工具类交给spring管理,
     * 用来从SpringSecurityOauth2框架中获取当前登录用户的用户名
     * @return
     */
    @Bean
    public TokenDecode tokenDecode() {
        return new TokenDecode();
    }

    /**
     * 将这个全局拦截器交给spring容器管理
     * 作用是将请求头中的jwt取出放入feign请求头中, 携带权限
     * @return
     */
    @Bean
    public FeignInterceptor feignInterceptor() {
        return  new FeignInterceptor();
    }
}
