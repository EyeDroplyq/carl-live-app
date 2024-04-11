package com.carl.live.im.core.server.service;

import com.carl.im.interfaces.dto.ImMsgBody;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-07 21:42
 * @version: 1.0
 */
public interface RouterHandlerService {


    /**
     * 当接收到业务服务发过来的消息后进行处理
     * @param imMsgBody
     */
    void onReceive(ImMsgBody imMsgBody);
}
