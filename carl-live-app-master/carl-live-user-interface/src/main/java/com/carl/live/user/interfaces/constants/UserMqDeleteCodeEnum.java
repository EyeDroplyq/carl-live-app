package com.carl.live.user.interfaces.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 用户中台删除业务码枚举类
 * @author: 小琦
 * @createDate: 2024-03-23 21:30
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public enum UserMqDeleteCodeEnum {
    DELETE_USER_INFO(1, "用户信息删除业务码"),
    DELETE_USER_TAG(2, "用户标签删除业务码");
    /**
     * 业务码
     */
    private int code;
    /**
     * 描述
     */
    private String desc;
}
