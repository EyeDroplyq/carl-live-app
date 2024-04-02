package com.carl.im.interfaces.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description: 不同业务消息包枚举类
 * @author: 小琦
 * @createDate: 2024-04-01 20:33
 * @version: 1.0
 */
@AllArgsConstructor
@Getter
public enum ImMsgCodeEnums {

    LOGIN_IN_MSG(1001,"登录消息"),

    LOGIN_OUT_MSG(1002,"登出消息"),

    BIZ_MSG(1003,"业务消息"),

    HEALTH_MSG(1004,"心跳消息");

    private int code;
    private String desc;
}
