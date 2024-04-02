package com.carl.live.im.core.server.handler;

import com.carl.live.im.core.server.common.ImMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * @description: 使用策略模式。针对不同的消息类型执行使用不同的handler
 * @author: 小琦
 * @createDate: 2024-04-01 20:41
 * @version: 1.0
 */
public interface HandlerFactory {
    public void doHandlerMsg(ChannelHandlerContext ctx, ImMsg msg);
}
