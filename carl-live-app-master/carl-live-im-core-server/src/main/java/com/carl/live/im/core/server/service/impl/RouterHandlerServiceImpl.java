package com.carl.live.im.core.server.service.impl;

import com.carl.im.interfaces.dto.ImMsgBody;
import com.carl.live.im.core.server.interfaces.rpc.IRouterHandlerRpc;
import com.carl.live.im.core.server.service.RouterHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-07 21:40
 * @version: 1.0
 */
@Slf4j
@Service
public class RouterHandlerServiceImpl implements RouterHandlerService {

    /**
     * 给用户发送消息
     * @param userId
     * @param imMsgBody
     */
    @Override
    public void sendMsg(long userId, ImMsgBody imMsgBody) {

    }

    /**
     * 当接收到业务服务发过来的消息后进行处理
     * @param imMsgBody
     */
    @Override
    public void onReceive(ImMsgBody imMsgBody) {

    }
}
