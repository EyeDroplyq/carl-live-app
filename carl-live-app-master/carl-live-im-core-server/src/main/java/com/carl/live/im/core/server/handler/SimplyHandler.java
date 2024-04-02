package com.carl.live.im.core.server.handler;

import com.carl.live.im.core.server.common.ImMsg;
import io.netty.channel.ChannelHandlerContext;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-01 20:37
 * @version: 1.0
 */
public interface SimplyHandler {
    public void doHandler(ChannelHandlerContext ctx, ImMsg msg);
}
