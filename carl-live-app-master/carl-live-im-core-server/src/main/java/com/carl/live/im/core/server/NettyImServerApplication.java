package com.carl.live.im.core.server;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-31 16:55
 * @version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class NettyImServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NettyImServerApplication.class, args);
    }

}
