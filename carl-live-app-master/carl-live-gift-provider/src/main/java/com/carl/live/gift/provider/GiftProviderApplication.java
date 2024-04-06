package com.carl.live.gift.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 15:12
 * @version: 1.0
 */
@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
public class GiftProviderApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication();
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }
}
