package com.carl.live.im.core.server.handler.impl;

import com.carl.live.im.core.server.common.ImMsg;
import com.carl.live.im.core.server.handler.SimplyHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @description: 业务消息handler实现类
 * @author: 小琦
 * @createDate: 2024-04-01 20:40
 * @version: 1.0
 */
public class BizHandlerImpl implements SimplyHandler {
    @Override
    public void doHandler(ChannelHandlerContext ctx, ImMsg msg) {
        System.out.println("【Biz】接收到的消息为:"+msg);
        ctx.writeAndFlush(msg);
    }
}
