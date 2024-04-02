package com.carl.live.im.core.server.common;

import io.netty.util.AttributeKey;

/**
 * @description: Im上下文属性
 * @author: 小琦
 * @createDate: 2024-04-02 22:07
 * @version: 1.0
 */
public class ImContextAttr {
    public static AttributeKey<Long> USER_ID=AttributeKey.valueOf("userId");

    public static AttributeKey<Integer> APP_ID=AttributeKey.valueOf("appId");
}
