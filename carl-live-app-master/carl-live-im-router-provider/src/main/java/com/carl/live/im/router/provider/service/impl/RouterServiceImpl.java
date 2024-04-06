package com.carl.live.im.router.provider.service.impl;

import com.carl.live.im.core.server.interfaces.rpc.IRouterHandlerRpc;
import com.carl.live.im.router.provider.service.RouterService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 20:52
 * @version: 1.0
 */
@Service
public class RouterServiceImpl implements RouterService {
    @DubboReference
    private IRouterHandlerRpc routerHandlerRpc;

    @Override
    public boolean sendMsg(Long objectId, String msgJson) {
        String objectImServerIp="127.0.0.1:9099";
        RpcContext.getContext().set("ip",objectId);
        routerHandlerRpc.sendMsg(objectId, msgJson);
        return true;
    }
}
