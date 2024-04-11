package com.carl.live.web.starter.limit;

import java.lang.annotation.*;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-10 20:52
 * @version: 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    /**
     * 限制数量
     * @return
     */
    int limit();

    /**
     * 限制时间
     * @return
     */
    int limitSecond();

    /**
     * 限流提示语
     */
    String limitMsg() default "请求次数太多，请稍后再试";
}
