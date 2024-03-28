package com.carl.live.app.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-28 20:53
 * @version: 1.0
 */
@AllArgsConstructor
@Getter
public enum UserPhoneEnum {
    VALID(1, "有效"),
    UN_VALID(0, "无效");
    private int code;
    private String desc;
}
