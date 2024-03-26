package com.carl.sms.interfaces.rpc;

import com.carl.sms.interfaces.dto.MsgCheckDTO;
import com.carl.sms.interfaces.enums.MsgSendResultEnum;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-25 21:48
 * @version: 1.0
 */
public interface SmsRpc {
    /**
     * 发送短信接口
     *
     * @param phone
     * @return
     */
    MsgSendResultEnum sendMessage(String phone);

    /**
     * 校验登录验证码
     *
     * @param phone
     * @param code
     * @return
     */
    MsgCheckDTO checkLoginCode(String phone, String code);

    /**
     * 插入一条短信记录
     *
     * @param phone
     * @param code
     */
    void insertOne(String phone, String code);
}
