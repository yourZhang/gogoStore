package com.store.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.store.business.mapper"})
public class BusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run( BusinessApplication.class);
    }
}
