package com.carl.live.app.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 15:50
 * @version: 1.0
 */
@AllArgsConstructor
@Getter
public enum StatusEnum {
    VALID_STATUS(1, "有效"),
    NO_VALID_STATUS(0, "无效");
    private int status;
    private String desc;
}
