package com.carl.live.user.interfaces.utils;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-23 18:18
 * @version: 1.0
 */
public class UserTagUtils {
    public static boolean isContainUserTag(Long userTag, Long matchTag) {
        return userTag != null && matchTag != null && (userTag & matchTag) == matchTag && matchTag != 0L;
    }
}
