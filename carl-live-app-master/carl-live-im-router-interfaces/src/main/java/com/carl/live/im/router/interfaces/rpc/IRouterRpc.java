package com.carl.live.im.router.interfaces.rpc;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 20:51
 * @version: 1.0
 */
public interface IRouterRpc {
    /**
     * 发送消息
     *
     * @param objectId: 接收方id
     * @param msgJson:  消息json格式
     */
    boolean sendMsg(Long objectId, String msgJson);
}
