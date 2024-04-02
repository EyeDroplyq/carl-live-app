package com.carl.live.im.core.server.common;

import com.carl.im.interfaces.constants.ImConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @description: 消息解码器
 * @author: 小琦
 * @createDate: 2024-03-31 17:32
 * @version: 1.0
 */
public class ImMsgDecoder extends ByteToMessageDecoder {
    private final int BASE_LEN = 2 + 4 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        //校验长度
        if (byteBuf.readableBytes() < BASE_LEN) {
            ctx.close();
            return;
        }
        // 校验magic
        if (ImConstants.BASE_MAGIC != byteBuf.readShort()) {
            ctx.close();
            return;
        }
        int len = byteBuf.readInt();
        int code = byteBuf.readInt();
        if (byteBuf.readableBytes() < len) {
            ctx.close();
            return;
        }
        byte[] body = new byte[len];
        byteBuf.readBytes(body);
        ImMsg imMsg = new ImMsg();
        imMsg.setLen(len);
        imMsg.setCode(code);
        imMsg.setMagic(ImConstants.BASE_MAGIC);
        imMsg.setBytes(body);
        out.add(imMsg);
    }
}
