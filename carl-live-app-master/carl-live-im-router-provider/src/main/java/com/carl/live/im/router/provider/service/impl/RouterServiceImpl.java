package com.carl.live.im.router.provider.service.impl;

import com.carl.im.interfaces.dto.ImMsgBody;
import com.carl.live.app.common.constants.CacheConstants;
import com.carl.live.im.core.server.interfaces.rpc.IRouterHandlerRpc;
import com.carl.live.im.router.provider.service.RouterService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean sendMsg(Long objectId, ImMsgBody imMsgBody) {
        String bindIpKey = CacheConstants.IM_BIND_IP_KEY + imMsgBody.getAppId() + ":" + imMsgBody.getUserId();
        String bindIp = stringRedisTemplate.opsForValue().get(bindIpKey);
        if (!StringUtils.hasText(bindIp)){
            throw new IllegalArgumentException("ip bind info is null");
        }
        RpcContext.getContext().set("ip",bindIp);
        routerHandlerRpc.sendMsg(objectId, imMsgBody);
        return true;
    }
}
