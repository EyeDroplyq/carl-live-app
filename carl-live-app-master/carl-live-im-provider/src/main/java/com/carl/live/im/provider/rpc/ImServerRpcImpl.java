package com.carl.live.im.provider.rpc;

import com.carl.im.interfaces.rpc.ImServerRpc;
import com.carl.live.im.provider.service.ImServerService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-02 20:57
 * @version: 1.0
 */
@DubboService
public class ImServerRpcImpl implements ImServerRpc {
    @Resource
    private ImServerService imServerService;

    @Override
    public String createImLoginToken(long userId, int appId) {
        return imServerService.createImLoginToken(userId, appId);
    }

    @Override
    public Long getUserIdByToken(String token) {
        return imServerService.getUserIdByToken(token);
    }
}
