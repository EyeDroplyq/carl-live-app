package com.carl.sms.interfaces.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 短信发送结果枚举类
 * @author: 小琦
 * @createDate: 2024-03-25 21:49
 * @version: 1.0
 */
@AllArgsConstructor
@Getter
public enum MsgSendResultEnum {
    SEND_SUCCESS(0, "发送成功"),
    SEND_FAIL(1, "发送失败"),
    REPEAT_SEND(2,"重复发送");
    private int code;
    private String desc;
}
