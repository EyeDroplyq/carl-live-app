package com.carl.live.im.core.server.handler.impl;

import com.alibaba.fastjson2.JSON;
import com.carl.live.app.common.constants.MqSubscribeTopicConstants;
import com.carl.live.im.core.server.common.ChannelHandlerContextCache;
import com.carl.live.im.core.server.common.ImMsg;
import com.carl.live.im.core.server.handler.SimplyHandler;
import com.carl.live.im.core.server.util.ImContextUtils;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @description: 业务消息handler实现类
 * @author: 小琦
 * @createDate: 2024-04-01 20:40
 * @version: 1.0
 */
@Component
@Slf4j
public class BizHandlerImpl implements SimplyHandler {
    @Resource
    private MQProducer mqProducer;

    @Override
    public void doHandler(ChannelHandlerContext ctx, ImMsg msg) {
        long userId = ImContextUtils.getUserId(ctx);
        int appId = ImContextUtils.getAppId(ctx);
        if (ObjectUtils.isEmpty(userId)) {
            ctx.close();
            throw new IllegalArgumentException("用户id为空");
        }
        if (ObjectUtils.isEmpty(appId)) {
            ctx.close();
            ChannelHandlerContextCache.remove(userId);
            throw new IllegalArgumentException("appId为空");
        }
        byte[] body = msg.getBytes();
        if (ObjectUtils.isEmpty(body) || body.length == 0) {
            ctx.close();
            throw new IllegalArgumentException("body为空");
        }
        // 将消息通过消息中间件传递给下游系统
        Message message = new Message();
        message.setTopic(MqSubscribeTopicConstants.CARL_LIVE_IM_BIZ_MSG_TOPIC);
        message.setBody(body);
        try {
            SendResult sendResult = mqProducer.send(message);
            log.info("[SendResult] is {}", sendResult);
        } catch (Exception e) {
            log.error("send msg to mq is error, error is {}", JSON.toJSONString(e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }
}
