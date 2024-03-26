package com.carl.live.app.common.Constants;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-17 18:10
 * @version: 1.0
 */
public class CacheConstants {

    public static final String USER_PROVIDER = "carl-live-user-provider-user-info";
    public static final String USER_TAG_PROVIDER = "carl-live-user-provider-user-tag";

    public static final String SMS_PROVIDER = "carl-live-sms-provider";
    public static final String SMS_PROVIDER_SEND_TIME = "carl-live-sms-provider-send-time";

    /**
     * 用户信息缓存key的前缀
     */
    public static final String USER_PROVIDER_KET = USER_PROVIDER + ":";

    /**
     * 用户标签缓存key的前缀
     */
    public static final String USER_TAG_PROVIDER_KET = USER_TAG_PROVIDER + ":";

    /**
     * 短信缓存key前缀
     */
    public static final String SMS_PROVIDER_KEY = SMS_PROVIDER + ":";

    /**
     * 短信发送时间
     */
    public static final String SMS_PROVIDER_SEND_TIME_KEY = SMS_PROVIDER_SEND_TIME + ":";


}
