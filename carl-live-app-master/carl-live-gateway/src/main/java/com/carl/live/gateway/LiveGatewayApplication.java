package com.carl.live.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-24 21:50
 * @version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LiveGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(LiveGatewayApplication.class, args);
    }
}
