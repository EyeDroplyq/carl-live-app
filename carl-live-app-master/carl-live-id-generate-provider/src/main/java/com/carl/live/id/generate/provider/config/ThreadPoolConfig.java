package com.carl.live.id.generate.provider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-28 21:38
 * @version: 1.0
 */
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000, 1500, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("id-generate:" + ThreadLocalRandom.current().nextInt(100));
                return thread;
            }
        });
        return threadPoolExecutor;
    }
}
