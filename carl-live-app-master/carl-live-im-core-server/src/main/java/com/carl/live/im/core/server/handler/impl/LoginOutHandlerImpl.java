package com.carl.live.im.core.server.handler.impl;

import com.alibaba.fastjson2.JSON;
import com.carl.im.interfaces.dto.ImMsgBody;
import com.carl.live.app.common.constants.CacheConstants;
import com.carl.live.im.core.server.common.ChannelHandlerContextCache;
import com.carl.live.im.core.server.common.ImMsg;
import com.carl.live.im.core.server.handler.SimplyHandler;
import com.carl.live.im.core.server.util.ImContextUtils;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @description: 登出消息handler实现类
 * @author: 小琦
 * @createDate: 2024-04-01 20:39
 * @version: 1.0
 */
@Component
public class LoginOutHandlerImpl implements SimplyHandler {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void doHandler(ChannelHandlerContext ctx, ImMsg msg) {
        long userId = ImContextUtils.getUserId(ctx);
        if (ObjectUtils.isEmpty(userId)) {
            throw new IllegalArgumentException("userId is null");
        }
        // 将ip绑定信息从redis中删除
        ImMsgBody imMsgBody = JSON.parseObject(new String(msg.getBytes()), ImMsgBody.class);
        String bindIpKey = CacheConstants.IM_BIND_IP_KEY + imMsgBody.getAppId() + ":" + userId;
        stringRedisTemplate.delete(bindIpKey);
        ChannelHandlerContextCache.remove(userId);
        ctx.close();
    }
}
