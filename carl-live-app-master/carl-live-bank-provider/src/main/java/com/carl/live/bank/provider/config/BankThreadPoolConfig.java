package com.carl.live.bank.provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-14 16:47
 * @version: 1.0
 */
@Configuration
public class BankThreadPoolConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(9, 20, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1000));
    }
}
