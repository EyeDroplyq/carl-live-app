package com.carl.live.web.starter.config;

import com.carl.live.web.starter.limit.RateLimit;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * @description: 限流注解拦截器
 * @author: 小琦
 * @createDate: 2024-04-10 20:55
 * @version: 1.0
 */
public class LimitInterceptor implements HandlerInterceptor {
    @Value(("${spring.application.name}"))
    private String applicationName;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String redisKey = applicationName + ":" + request.getRequestURL().toString();
        boolean annotationPresent = handlerMethod.getMethod().isAnnotationPresent(RateLimit.class);
        RateLimit methodAnnotation = handlerMethod.getMethodAnnotation(RateLimit.class);
        int limit = methodAnnotation.limit();
        int limitSecond = methodAnnotation.limitSecond();
        String limitMsg = methodAnnotation.limitMsg();
        if (annotationPresent) {
            Integer requestTimes = (Integer) redisTemplate.opsForValue().get(redisKey);
            if (ObjectUtils.isEmpty(requestTimes)) {
                redisTemplate.opsForValue().setIfAbsent(redisKey, 1, limitSecond, TimeUnit.SECONDS);
                return true;
            }
            if (requestTimes < limit) {
                redisTemplate.opsForValue().increment(redisKey, 1);
                return true;
            }
            throw new RuntimeException(limitMsg);
        }
        return true;
    }
}
