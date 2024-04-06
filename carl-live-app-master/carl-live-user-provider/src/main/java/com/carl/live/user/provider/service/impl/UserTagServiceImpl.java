package com.carl.live.user.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.carl.live.app.common.constants.CacheConstants;
import com.carl.live.app.common.interfaces.ConvertBeanUtils;
import com.carl.live.user.interfaces.constants.MqConstants;
import com.carl.live.user.interfaces.constants.UserMqDeleteCodeEnum;
import com.carl.live.user.interfaces.constants.UserTagFileNameConstants;
import com.carl.live.user.interfaces.constants.UserTagsEnum;
import com.carl.live.user.interfaces.dto.UserCacheDeleteAsyncDTO;
import com.carl.live.user.interfaces.dto.UserTagDTO;
import com.carl.live.user.interfaces.utils.UserTagUtils;
import com.carl.live.user.provider.config.RocketMqProducerConfig;
import com.carl.live.user.provider.dao.mapper.IUserTagMapper;
import com.carl.live.user.provider.dao.po.UserTagPO;
import com.carl.live.user.provider.service.IUserTagService;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.carl.live.app.common.constants.ComConstants.ONE_INT;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-23 18:07
 * @version: 1.0
 */
@Service
public class UserTagServiceImpl implements IUserTagService {
    @Resource
    private IUserTagMapper userTagMapper;

    @Resource
    private RedisTemplate<String, UserTagDTO> redisTemplate;

    @Resource
    private RocketMqProducerConfig producerConfig;

    /**
     * 更新用户标签
     * 1、更新用户标签
     * 2、删除缓存
     * 3、发送rocketMq，延迟删除
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    @Override
    public boolean setTag(Long userId, UserTagsEnum userTagsEnum) {
        int res = userTagMapper.setTag(userId, userTagsEnum.getTag(), userTagsEnum.getFileName());
        if (ONE_INT != res) {
            return false;
        }
        //删除缓存
        redisTemplate.delete(CacheConstants.USER_TAG_PROVIDER_KET + userId);

        //发送消息给mq,延迟删除
        try {
            MQProducer mqProducer = producerConfig.mqProducer();
            Message message = new Message();
            message.setTopic(MqConstants.CacheDeleteAsyncTopic);
            UserCacheDeleteAsyncDTO userCacheDeleteAsyncDTO = new UserCacheDeleteAsyncDTO();
            userCacheDeleteAsyncDTO.setCode(UserMqDeleteCodeEnum.DELETE_USER_TAG.getCode());
            Map<String, String> jsonMap = new HashMap<>();
            jsonMap.put("userId", String.valueOf(userId));
            userCacheDeleteAsyncDTO.setJson(JSON.toJSONString(jsonMap));
            message.setBody(JSON.toJSONString(userCacheDeleteAsyncDTO).getBytes(StandardCharsets.UTF_8));
            message.setDelayTimeLevel(1);
            mqProducer.send(message);
        } catch (Exception e) {
            throw new RuntimeException("发送消息给mq失败");
        }
        return true;
    }

    /**
     * 取消用户标签
     * 1.取消标签
     * 2、删除缓存
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    @Override
    public boolean cancelTag(Long userId, UserTagsEnum userTagsEnum) {
        int res = userTagMapper.cancelTag(userId, userTagsEnum.getTag(), userTagsEnum.getFileName());
        if (ONE_INT != res) {
            return false;
        }
        redisTemplate.delete(CacheConstants.USER_TAG_PROVIDER_KET + userId);
        return true;
    }

    /**
     * 用户是否包含指定的标签
     * 1、查缓存，有则直接返回
     * 2、缓存没有则查数据库。然后写回缓存中
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    @Override
    public boolean containTag(Long userId, UserTagsEnum userTagsEnum) {
        UserTagDTO userTagDTO = queryUserTagInRedis(userId, userTagsEnum);
        if (Objects.isNull(userTagDTO)) {
            return false;
        }
        if (UserTagFileNameConstants.TAG_INFO_01.equals(userTagsEnum.getFileName())) {
            return UserTagUtils.isContainUserTag(userTagDTO.getTagInfo01(), userTagsEnum.getTag());
        } else if (UserTagFileNameConstants.TAG_INFO_02.equals(userTagsEnum.getFileName())) {
            return UserTagUtils.isContainUserTag(userTagDTO.getTagInfo02(), userTagsEnum.getTag());
        } else if (UserTagFileNameConstants.TAG_INFO_03.equals(userTagsEnum.getFileName())) {
            return UserTagUtils.isContainUserTag(userTagDTO.getTagInfo03(), userTagsEnum.getTag());
        }
        return false;
    }


    @Override
    public int insertUserTag(Long userId) {
        UserTagPO userTagPO = new UserTagPO();
        userTagPO.setUserId(userId);
        return userTagMapper.insert(userTagPO);
    }


    //***************************************private域************************************

    /**
     * 从缓存中查询用户标签信息
     *
     * @param userId
     * @param userTagsEnum
     * @return
     */
    private UserTagDTO queryUserTagInRedis(Long userId, UserTagsEnum userTagsEnum) {
        UserTagDTO userTagDTO = redisTemplate.opsForValue().get(CacheConstants.USER_TAG_PROVIDER_KET + userId);
        if (Objects.nonNull(userTagDTO)) {
            return userTagDTO;
        }
        UserTagPO userTagPO = userTagMapper.selectById(userId);
        if (Objects.isNull(userTagPO)) {
            return null;
        }
        userTagDTO = ConvertBeanUtils.convert(userTagPO, UserTagDTO.class);
        redisTemplate.opsForValue().set(CacheConstants.USER_TAG_PROVIDER_KET + userTagPO.getUserId(),
                userTagDTO,
                createRandomExpiredTime(), TimeUnit.SECONDS);
        return userTagDTO;
    }

    /**
     * 随机生成key过期时间
     *
     * @return
     */
    private int createRandomExpiredTime() {
        int time = ThreadLocalRandom.current().nextInt(1000);
        return time + 60 * 30;
    }
}
