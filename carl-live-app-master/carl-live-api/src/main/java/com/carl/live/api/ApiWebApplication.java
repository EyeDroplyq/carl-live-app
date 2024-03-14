package com.carl.live.api;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-14 21:39
 * @version: 1.0
 */
@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
public class ApiWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiWebApplication.class, args);
    }
}
