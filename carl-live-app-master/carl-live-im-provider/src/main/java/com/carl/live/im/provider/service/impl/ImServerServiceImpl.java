package com.carl.live.im.provider.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.carl.live.app.common.constants.CacheConstants;
import com.carl.live.im.provider.service.ImServerService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-02 20:56
 * @version: 1.0
 */
@Service
public class ImServerServiceImpl implements ImServerService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String createImLoginToken(long userId, int appId) {
        String token = RandomUtil.randomString(6) + "%" + appId;
        redisTemplate.opsForValue().set(CacheConstants.IM_LOGIN_KEY + token, userId, 5, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public Long getUserIdByToken(String token) {
        Object userId = redisTemplate.opsForValue().get(CacheConstants.IM_LOGIN_KEY + token);
        return Long.valueOf((Integer) userId);
    }
}
