package com.carl.live.im.core.server.handler.impl;

import com.alibaba.fastjson.JSON;
import com.carl.im.interfaces.dto.ImMsgBody;
import com.carl.im.interfaces.enums.ImMsgCodeEnums;
import com.carl.live.app.common.constants.CacheConstants;
import com.carl.live.im.core.server.common.ChannelHandlerContextCache;
import com.carl.live.im.core.server.common.ImMsg;
import com.carl.live.im.core.server.config.HeartBeatConfigProperties;
import com.carl.live.im.core.server.handler.SimplyHandler;
import com.carl.live.im.core.server.util.ImContextUtils;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;

/**
 * @description: 心跳包消息handler实现类
 * @author: 小琦
 * @createDate: 2024-04-01 20:39
 * @version: 1.0
 */
@Component
public class HealthHandlerImpl implements SimplyHandler {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private HeartBeatConfigProperties heartBeatConfigProperties;

    @Override
    public void doHandler(ChannelHandlerContext ctx, ImMsg msg) {
        long userId = ImContextUtils.getUserId(ctx);
        int appId = ImContextUtils.getAppId(ctx);
        if (ObjectUtils.isEmpty(userId)) {
            ctx.close();
            throw new IllegalArgumentException("用户id为空");
        }
        if (ObjectUtils.isEmpty(appId)) {
            ctx.close();
            ChannelHandlerContextCache.remove(userId);
            throw new IllegalArgumentException("appId为空");
        }
        String heartBeatKey = CacheConstants.IM_CORE_HEART_BEAT_KEY + appId + ":" + (userId % 10000);
        // 将用户的心跳数据存在zset数据结构中，如果用大量的数据过来为了防止产生大key,所以对userId进行取余，然后作为zset的key
        saveHeartBeatToZset(userId, heartBeatKey);
        //将过期的心跳数据进行删除
        removeExpiredHeartBeat(heartBeatKey);
        //继续向下传递ImMsg
        ImMsgBody imMsgBody = new ImMsgBody();
        imMsgBody.setAppId(appId);
        imMsgBody.setUserId(userId);
        imMsgBody.setData("true");
        ImMsg imMsg = ImMsg.makeImMsg(ImMsgCodeEnums.HEALTH_MSG.getCode(), JSON.toJSONString(imMsgBody).getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(imMsg);
    }


    //******************************private域**************************************

    /**
     * 将用户的心跳数据存在zset数据结构中，如果用大量的数据过来为了防止产生大key,所以对userId进行取余，然后作为zset的key
     *
     * @param userId
     * @param heartBeatKey
     */
    private void saveHeartBeatToZset(long userId, String heartBeatKey) {
        redisTemplate.opsForZSet().add(heartBeatKey, userId, System.currentTimeMillis());
    }

    /**
     * 将过期的心跳数据进行删除
     *
     * @param heartBeatKey
     */
    private void removeExpiredHeartBeat(String heartBeatKey) {
        redisTemplate.opsForZSet().removeRangeByScore(heartBeatKey, 0, System.currentTimeMillis() - heartBeatConfigProperties.getExpiredHeartBeat() * 2);
    }
}
