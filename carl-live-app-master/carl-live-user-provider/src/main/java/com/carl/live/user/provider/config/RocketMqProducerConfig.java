package com.carl.live.user.provider.config;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.threadpool.ThreadPool;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-18 20:59
 * @version: 1.0
 */
@Configuration
@Slf4j
public class RocketMqProducerConfig {
    @Resource
    private RocketMqProducerPropertiesConfig producerPropertiesConfig;
    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public MQProducer mqProducer() {
        log.info("发送者初始化...");
        ThreadPoolExecutor asyncThreadPoolExecutor = new ThreadPoolExecutor(100,
                150,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread();
                thread.setName(applicationName + ":producer:" + ThreadLocalRandom.current().nextInt(1000));
                return thread;
            }
        });
        try {
            DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
            defaultMQProducer.setNamesrvAddr(producerPropertiesConfig.getNameSrv());
            defaultMQProducer.setProducerGroup(producerPropertiesConfig.getGroupName());
            // 如果没有发送成功发送给另外一个broker
            defaultMQProducer.setRetryAnotherBrokerWhenNotStoreOK(true);
            //设置重试次数
            defaultMQProducer.setRetryTimesWhenSendFailed(producerPropertiesConfig.getRetryCount());
            defaultMQProducer.setRetryTimesWhenSendAsyncFailed(producerPropertiesConfig.getRetryCount());
            // 设置发送消息的超时时间
            defaultMQProducer.setSendMsgTimeout(producerPropertiesConfig.getSendTimeOut());
            // 设置异步发送消息
            defaultMQProducer.setAsyncSenderExecutor(asyncThreadPoolExecutor);
            defaultMQProducer.start();
            return defaultMQProducer;
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
        }
        return null;
    }
}