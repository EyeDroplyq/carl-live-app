package com.carl.live.im.core.server.interfaces.rpc;

import com.carl.im.interfaces.dto.ImMsgBody;

/**
 * @description: 专门给router层调用的rpc接口
 * @author: 小琦
 * @createDate: 2024-04-06 20:56
 * @version: 1.0
 */
public interface IRouterHandlerRpc {
    /**
     * 给用户发送消息
     *
     * @param userId
     * @param imMsgBody
     */
    void sendMsg(long userId, ImMsgBody imMsgBody);

}
