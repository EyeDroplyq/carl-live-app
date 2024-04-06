package com.carl.live.sms.provider.config;

import com.alibaba.fastjson2.JSON;
import com.carl.live.app.common.constants.MqSubscribeTopicConstants;
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

import java.util.List;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-18 20:59
 * @version: 1.0
 */
@Configuration
@Slf4j
public class ImMsgConsumer implements InitializingBean {
    @Resource
    private RocketMqConsumerPropertiesConfig consumerPropertiesConfig;


    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(consumerPropertiesConfig.getGroupName());
        defaultMQPushConsumer.setNamesrvAddr(consumerPropertiesConfig.getNameSrv());
        defaultMQPushConsumer.setConsumerGroup(consumerPropertiesConfig.getGroupName() + "_" + ImMsgConsumer.class.getSimpleName());
        // 设置批量消费的最大数量
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
        // 设置开始消费的偏移量
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        defaultMQPushConsumer.subscribe(MqSubscribeTopicConstants.CARL_LIVE_IM_BIZ_MSG_TOPIC, "*");
        defaultMQPushConsumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                log.info("[ImMsgConsumer] consumer msg is {}", JSON.toJSONString(msgs.get(0).getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }
}
