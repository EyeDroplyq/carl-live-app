package com.carl.live.im.core.server.interfaces.rpc;

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
     * @param msgJson
     */
    void sendMsg(long userId, String msgJson);

}
