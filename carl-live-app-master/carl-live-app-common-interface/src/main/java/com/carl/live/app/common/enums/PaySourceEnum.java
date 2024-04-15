package com.carl.live.app.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-15 21:20
 * @version: 1.0
 */
@AllArgsConstructor
@Getter
public enum PaySourceEnum {
    CARL_LIVING_ROOM(1, "直播间内支付"),
    CARL_USER_CENTER(2, "用户中心支付");

    public static PaySourceEnum find(int code) {
        for (PaySourceEnum value : PaySourceEnum.values()) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }

    private Integer code;
    private String desc;
}
