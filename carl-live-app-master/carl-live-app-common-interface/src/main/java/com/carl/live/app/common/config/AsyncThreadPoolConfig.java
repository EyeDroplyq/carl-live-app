package com.carl.live.app.common.config;

import java.util.concurrent.*;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-25 22:19
 * @version: 1.0
 */
public class AsyncThreadPoolConfig {

    public static ThreadPoolExecutor asyncThreadPool = new ThreadPoolExecutor(100,
            150,
            30,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread();
                    thread.setName("thread-pool-" + ThreadLocalRandom.current().nextInt(1000));
                    return thread;
                }
            });
}
