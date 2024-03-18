package com.carl.live.user.provider.config;

import com.alibaba.fastjson2.JSON;
import com.carl.live.user.interfaces.dto.UserDTO;
import jakarta.annotation.Resource;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-18 20:59
 * @version: 1.0
 */
@Configuration
@Slf4j
public class RocketMqConsumerConfig implements InitializingBean {
    @Resource
    private RocketMqConsumerPropertiesConfig consumerPropertiesConfig;

    @Resource
    private RedisTemplate<String, UserDTO> redisTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.initConsumer();
    }

    /**
     * 初始化消费者
     */
    private void initConsumer(){
        log.info("初始化消费者...");
        try {
            DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
            defaultMQPushConsumer.setNamesrvAddr(consumerPropertiesConfig.getNameSrv());
            defaultMQPushConsumer.setConsumerGroup(consumerPropertiesConfig.getGroupName());
            // 设置批量消费的最大数量
            defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
            // 设置开始消费的偏移量
            defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            defaultMQPushConsumer.subscribe("user-update", "");
            // 消费逻辑
            defaultMQPushConsumer.setMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                try {
                    UserDTO userDTO = JSON.parseObject(new String(msgs.get(0).getBody()), UserDTO.class);
                    if (Objects.nonNull(userDTO)){
                        String key = String.valueOf(userDTO.getUserId());
                        redisTemplate.delete(key);
                    }
                    defaultMQPushConsumer.start();
                } catch (MQClientException e) {
                    throw new RuntimeException(e);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
