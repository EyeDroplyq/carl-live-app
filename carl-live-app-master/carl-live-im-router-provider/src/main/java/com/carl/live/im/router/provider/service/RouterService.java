package com.carl.live.im.router.provider.service;

import com.carl.im.interfaces.dto.ImMsgBody;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 20:52
 * @version: 1.0
 */
public interface RouterService {
    /**
     * 发送消息
     * @param objectId
     * @param imMsgBody
     */
    boolean sendMsg(Long objectId, ImMsgBody imMsgBody);
}
