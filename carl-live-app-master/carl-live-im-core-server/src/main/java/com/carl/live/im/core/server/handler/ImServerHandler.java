package com.carl.live.im.core.server.handler;

import com.carl.live.im.core.server.common.ImMsg;
import com.carl.live.im.core.server.handler.impl.HandlerFactoryImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @description: 消息接收handler
 * @author: 小琦
 * @createDate: 2024-04-01 20:37
 * @version: 1.0
 */
public class ImServerHandler extends SimpleChannelInboundHandler {
    private HandlerFactory handlerFactory = new HandlerFactoryImpl();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof ImMsg)) {
            throw new IllegalAccessException("param is error");
        }
        ImMsg imMsg = (ImMsg) msg;
        handlerFactory.doHandlerMsg(ctx, imMsg);
    }


    /**
     * 处理channel下线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
