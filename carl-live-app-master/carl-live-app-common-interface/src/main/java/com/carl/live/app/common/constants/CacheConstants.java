package com.carl.live.app.common.constants;

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

    public static final String USER_LOGIN = "carl-live-user-provider-login";

    public static final String IM_LOGIN_TOKEN = "carl-live-im-login";

    public static final String IM_CORE_PREFIX = "carl-live-im-core";

    public static final String USER_BANK_PREFIX = "carl-live-bank-provider";


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
    /**
     * 用户登录信息的key
     */
    public static final String USER_LOGIN_KEY = USER_LOGIN + ":";
    /**
     * im服务登录token的key
     */
    public static final String IM_LOGIN_KEY = IM_LOGIN_TOKEN + ":";

    public static final String IM_CORE_HEART_BEAT_KEY = IM_CORE_PREFIX + ":"+"heart_beat:";

    public static final String IM_BIND_IP_KEY="carl-live-im-bind-ip:";
    /**
     * 用户账户余额 key
     */
    public static final String USER_ACCOUNT_BALANCE_KEY=USER_BANK_PREFIX+":"+"account:"+"balace:";

    /**
     * 支付产品列表 key
     */
    public static  final  String PAY_PRODUCT_KEY=USER_BANK_PREFIX+"pay:product:";


}
