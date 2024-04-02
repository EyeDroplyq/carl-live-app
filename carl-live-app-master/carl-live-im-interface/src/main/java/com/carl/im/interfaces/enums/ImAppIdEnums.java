package com.carl.im.interfaces.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-02 21:18
 * @version: 1.0
 */
@AllArgsConstructor
@Getter
public enum ImAppIdEnums {
    CARL_LIVE_APPID(1, "carl直播业务");
    private int appId;
    private String desc;
}
