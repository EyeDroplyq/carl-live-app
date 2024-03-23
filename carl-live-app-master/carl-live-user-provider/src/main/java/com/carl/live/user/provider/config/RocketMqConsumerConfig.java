package com.carl.live.user.provider.config;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.carl.live.app.common.Constants.CacheConstants;
import com.carl.live.user.interfaces.constants.MqConstants;
import com.carl.live.user.interfaces.constants.UserMqDeleteCodeEnum;
import com.carl.live.user.interfaces.dto.UserCacheDeleteAsyncDTO;
import com.carl.live.user.interfaces.dto.UserDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
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
    private void initConsumer() {
        log.info("初始化消费者...");
        try {
            DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(consumerPropertiesConfig.getGroupName());
            defaultMQPushConsumer.setNamesrvAddr(consumerPropertiesConfig.getNameSrv());
            defaultMQPushConsumer.setConsumerGroup(consumerPropertiesConfig.getGroupName() + "_" + RocketMqConsumerConfig.class.getSimpleName());
            // 设置批量消费的最大数量
            defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
            // 设置开始消费的偏移量
            defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            defaultMQPushConsumer.subscribe(MqConstants.CacheDeleteAsyncTopic, "*");
            // 消费逻辑
            defaultMQPushConsumer.setMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    UserCacheDeleteAsyncDTO userCacheDeleteAsyncDTO = JSON.parseObject(new String(msgs.get(0).getBody()), UserCacheDeleteAsyncDTO.class);
                    log.info("消费rocketmq中的消息,userCacheDeleteAsyncDTO is {}", JSON.toJSONString(userCacheDeleteAsyncDTO));
                    if (Objects.nonNull(userCacheDeleteAsyncDTO)) {
                        //用户信息删除
                        if (UserMqDeleteCodeEnum.DELETE_USER_INFO.getCode() == userCacheDeleteAsyncDTO.getCode()) {
                            JSONObject jsonObject = JSONObject.parseObject(userCacheDeleteAsyncDTO.getJson());
                            String userId = (String) jsonObject.get("userId");
                            Assert.notBlank(userId, "用户id为空");
                            redisTemplate.delete(CacheConstants.USER_PROVIDER_KET + userId);
                            log.info("rocketmq消费删除用户信息，userId= {}", userId);
                        } else if (UserMqDeleteCodeEnum.DELETE_USER_TAG.getCode() == userCacheDeleteAsyncDTO.getCode()) {
                            JSONObject jsonObject = JSONObject.parseObject(userCacheDeleteAsyncDTO.getJson());
                            String userId = (String) jsonObject.get("userId");
                            Assert.notBlank(userId, "用户id为空");
                            redisTemplate.delete(CacheConstants.USER_TAG_PROVIDER_KET + userId);
                            log.info("rocketmq消费删除用户标签，userId= {}", userId);
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            defaultMQPushConsumer.start();
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
