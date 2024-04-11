package com.carl.live.im.router.provider.rpc;

import com.carl.im.interfaces.dto.ImMsgBody;
import com.carl.live.im.router.interfaces.rpc.IRouterRpc;
import com.carl.live.im.router.provider.service.RouterService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 20:52
 * @version: 1.0
 */
@DubboService
public class RouterRpcImpl implements IRouterRpc {
    @Resource
    private RouterService routerService;
    @Override
    public boolean sendMsg(Long objectId, ImMsgBody imMsgBody) {
        return routerService.sendMsg(objectId,imMsgBody);
    }
}
