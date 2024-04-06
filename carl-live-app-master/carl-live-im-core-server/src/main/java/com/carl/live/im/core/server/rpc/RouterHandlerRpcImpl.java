package com.carl.live.im.core.server.rpc;

import com.carl.live.im.core.server.interfaces.rpc.IRouterHandlerRpc;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 20:57
 * @version: 1.0
 */
@DubboService
public class RouterHandlerRpcImpl implements IRouterHandlerRpc {
    /**
     * 给用户发送消息
     *
     * @param userId
     * @param msgJson
     */
    @Override
    public void sendMsg(long userId, String msgJson) {
        System.out.println("用户" + userId + " 收到消息为：" + msgJson);
    }
}
