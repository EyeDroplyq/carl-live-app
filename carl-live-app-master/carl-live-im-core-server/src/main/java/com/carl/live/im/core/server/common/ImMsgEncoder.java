package com.carl.live.im.core.server.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @description: 消息编码器，上游发送的消息都封装成了ImMsg，然后通过这个编码器发送出去
 * @author: 小琦
 * @createDate: 2024-03-31 17:23
 * @version: 1.0
 */
public class ImMsgEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        ImMsg imMsg = (ImMsg) msg;
        out.writeShort(imMsg.getMagic());
        out.writeInt(imMsg.getLen());
        out.writeInt(imMsg.getCode());
        out.writeBytes(imMsg.getBytes());
    }
}
