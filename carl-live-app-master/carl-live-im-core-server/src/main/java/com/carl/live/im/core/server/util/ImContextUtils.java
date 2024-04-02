package com.carl.live.im.core.server.util;

import com.carl.live.im.core.server.common.ImContextAttr;
import io.netty.channel.ChannelHandlerContext;

/**
 * @description: 设置Im上下文属性的工具类
 * @author: 小琦
 * @createDate: 2024-04-02 22:09
 * @version: 1.0
 */
public class ImContextUtils {
    public static void setUserId(ChannelHandlerContext ctx, long userId) {
        ctx.attr(ImContextAttr.USER_ID).set(userId);
    }

    public static long getUserId(ChannelHandlerContext ctx) {
        return ctx.attr(ImContextAttr.USER_ID).get();
    }

    public static void setAppId(ChannelHandlerContext ctx, int appId) {
        ctx.attr(ImContextAttr.APP_ID).set(appId);
    }

    public static int getAppId(ChannelHandlerContext ctx) {
        return ctx.attr(ImContextAttr.APP_ID).get();
    }
}
