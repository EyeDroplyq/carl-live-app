package com.carl.live.user.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description: 用户中台服务提供者
 * @author: 小琦
 * @createDate: 2024-03-14 21:22
 * @version: 1.0
 */
@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
public class UserProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserProviderApplication.class, args);
    }
}
