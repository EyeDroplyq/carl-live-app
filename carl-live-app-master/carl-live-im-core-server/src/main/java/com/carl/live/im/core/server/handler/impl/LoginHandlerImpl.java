package com.carl.live.im.core.server.handler.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.carl.im.interfaces.dto.ImMsgBody;
import com.carl.im.interfaces.enums.ImAppIdEnums;
import com.carl.im.interfaces.enums.ImMsgCodeEnums;
import com.carl.im.interfaces.rpc.ImServerRpc;
import com.carl.live.im.core.server.common.ChannelHandlerContextCache;
import com.carl.live.im.core.server.common.ImMsg;
import com.carl.live.im.core.server.handler.SimplyHandler;
import com.carl.live.im.core.server.util.ImContextUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @description: 登录handler实现类
 * @author: 小琦
 * @createDate: 2024-04-01 20:38
 * @version: 1.0
 */
@Component
@Slf4j
public class LoginHandlerImpl implements SimplyHandler {
    @DubboReference
    private ImServerRpc imServerRpc;

    @Override
    public void doHandler(ChannelHandlerContext ctx, ImMsg msg) {
        // 防止重复请求
        if (!ObjectUtils.isEmpty(ImContextUtils.getUserId(ctx))) {
            return;
        }
        byte[] body = msg.getBytes();
        ImMsgBody imMsgBody = JSON.parseObject(new String(body), ImMsgBody.class);
        String token = imMsgBody.getToken();
        if (!StringUtils.hasText(token)) {
            ctx.close();
            log.error("token is null");
            throw new IllegalArgumentException("token is null");
        }
        Long userId = imServerRpc.getUserIdByToken(token);
        Assert.notNull(userId, "userId is null");
        if (userId.equals(imMsgBody.getUserId())) {
            //更新userId和channel的映射关系
            ChannelHandlerContextCache.put(userId, ctx);
            // 设置ctx上下文中的userId属性
            ImContextUtils.setUserId(ctx, userId);
            ImMsgBody respBody = new ImMsgBody();
            respBody.setToken(token);
            respBody.setUserId(userId);
            respBody.setAppId(ImAppIdEnums.CARL_LIVE_APPID.getAppId());
            respBody.setData("true");
            ImMsg imMsg = ImMsg.makeImMsg(ImMsgCodeEnums.LOGIN_IN_MSG.getCode(), JSON.toJSONString(respBody).getBytes(StandardCharsets.UTF_8));
            ctx.writeAndFlush(imMsg);
        } else {
            ctx.close();
        }
    }
}
