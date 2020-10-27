package com.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @program: gogo-store
 * @description:
 * @author: xiaozhang6666
 * @create: 2020-10-27 11:28
 **/
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.store.mapper")
public class GoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
}