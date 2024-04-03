package com.carl.live.im.core.server.handler.impl;

import com.carl.live.im.core.server.common.ChannelHandlerContextCache;
import com.carl.live.im.core.server.common.ImMsg;
import com.carl.live.im.core.server.handler.SimplyHandler;
import com.carl.live.im.core.server.util.ImContextUtils;
import io.netty.channel.ChannelHandlerContext;
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
    @Override
    public void doHandler(ChannelHandlerContext ctx, ImMsg msg) {
        long userId = ImContextUtils.getUserId(ctx);
        if (ObjectUtils.isEmpty(userId)) {
            throw new IllegalArgumentException("userId is null");
        }
        ChannelHandlerContextCache.remove(userId);
        ctx.close();
    }
}
