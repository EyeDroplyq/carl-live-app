package com.carl.live.im.server;

import com.carl.live.im.core.server.common.ImMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-01 21:25
 * @version: 1.0
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ImMsg imMsg = (ImMsg) msg;
        System.out.println("【服务端的响应为】:" + imMsg);
    }
}
