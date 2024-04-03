package com.carl.live.im.core.server.common;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 将userId和ChannelHandlerContext进行绑定
 * @author: 小琦
 * @createDate: 2024-04-02 22:02
 * @version: 1.0
 */
public class ChannelHandlerContextCache {
    private static final Map<Long, ChannelHandlerContext> userIdAndChannelHandlerContextMap = new HashMap<>();

    public static ChannelHandlerContext get(long userId) {
        return userIdAndChannelHandlerContextMap.get(userId);
    }

    public static void put(long userId, ChannelHandlerContext channelHandlerContext) {
        userIdAndChannelHandlerContextMap.put(userId, channelHandlerContext);
    }

    public static boolean remove(long userId) {
        return userIdAndChannelHandlerContextMap.remove(userId) != null;
    }
}
