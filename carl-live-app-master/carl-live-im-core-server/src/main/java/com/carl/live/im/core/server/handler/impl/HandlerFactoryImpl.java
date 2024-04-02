package com.carl.live.im.core.server.handler.impl;

import com.carl.im.interfaces.enums.ImMsgCodeEnums;
import com.carl.live.im.core.server.common.ImMsg;
import com.carl.live.im.core.server.handler.HandlerFactory;
import com.carl.live.im.core.server.handler.SimplyHandler;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-01 20:42
 * @version: 1.0
 */
public class HandlerFactoryImpl implements HandlerFactory {
    /**
     * 根据code来映射不同的handler实现类
     */
    private static Map<Integer, SimplyHandler> map = new HashMap<>();

    static {
        map.put(ImMsgCodeEnums.LOGIN_IN_MSG.getCode(), new LoginHandlerImpl());
        map.put(ImMsgCodeEnums.LOGIN_OUT_MSG.getCode(), new LoginOutHandlerImpl());
        map.put(ImMsgCodeEnums.BIZ_MSG.getCode(), new BizHandlerImpl());
        map.put(ImMsgCodeEnums.HEALTH_MSG.getCode(), new HealthHandlerImpl());
    }

    @Override
    public void doHandlerMsg(ChannelHandlerContext ctx, ImMsg imMsg) {
        int code = imMsg.getCode();
        if (ObjectUtils.isEmpty(code)){
            throw new IllegalArgumentException("imMsg’s code is null");
        }
        SimplyHandler simplyHandler = map.get(code);
        if (ObjectUtils.isEmpty(simplyHandler)){
            throw new IllegalArgumentException("simplyHandler is null");
        }
        simplyHandler.doHandler(ctx,imMsg);
    }
}
