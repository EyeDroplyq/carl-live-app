package com.carl.live.im.core.server.common;

import com.carl.im.interfaces.constants.ImConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description: 消息体
 * @author: 小琦
 * @createDate: 2024-03-31 17:19
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImMsg implements Serializable {
    @Serial
    private static final long serialVersionUID = -5464866171729828384L;

    /**
     * 用于校验的 magic
     */
    private short magic;


    /**
     * 字节数组的长度
     */
    private int len;

    /**
     * 消息类型的code
     */
    private int code;

    /**
     * 存放消息的字节数组
     */
    private byte[] bytes;

    public static ImMsg makeImMsg(int code, byte[] bytes) {
        ImMsg imMsg = new ImMsg();
        imMsg.setMagic(ImConstants.BASE_MAGIC);
        imMsg.setBytes(bytes);
        imMsg.setLen(imMsg.getBytes().length);
        imMsg.setCode(code);
        return imMsg;
    }
}

