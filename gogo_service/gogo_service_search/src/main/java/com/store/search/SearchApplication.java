package com.store.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @program: gogo-store
 * @description: SearchApplication 启动类
 * @author: xiaozhang6666
 * @create: 2020-11-02 18:04
 **/
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.store.feign"})
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
