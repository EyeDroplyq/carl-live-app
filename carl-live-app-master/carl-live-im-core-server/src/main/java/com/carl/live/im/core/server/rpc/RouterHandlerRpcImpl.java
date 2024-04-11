package com.carl.live.im.core.server.rpc;

import com.carl.im.interfaces.dto.ImMsgBody;
import com.carl.live.im.core.server.interfaces.rpc.IRouterHandlerRpc;
import com.carl.live.im.core.server.service.RouterHandlerService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 20:57
 * @version: 1.0
 */
@DubboService
public class RouterHandlerRpcImpl implements IRouterHandlerRpc {
    @Resource
    private RouterHandlerService routerHandlerService;

    /**
     * 给用户发送消息
     *
     * @param userId
     * @param imMsgBody
     */
    @Override
    public void sendMsg(long userId, ImMsgBody imMsgBody) {
        routerHandlerService.onReceive(imMsgBody);
    }
}
