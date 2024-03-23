package com.carl.live.user.provider.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.carl.live.app.common.interfaces.ConvertBeanUtils;
import com.carl.live.user.interfaces.constants.MqConstants;
import com.carl.live.user.interfaces.constants.UserMqDeleteCodeEnum;
import com.carl.live.user.interfaces.dto.UserCacheDeleteAsyncDTO;
import com.carl.live.user.interfaces.dto.UserDTO;
import com.carl.live.user.provider.config.RocketMqProducerConfig;
import com.carl.live.user.provider.dao.mapper.IUserMapper;
import com.carl.live.user.provider.dao.po.UserPO;
import com.carl.live.user.provider.service.IUserService;
import com.carl.live.user.provider.service.IUserTagService;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.carl.live.app.common.Constants.CacheConstants.USER_PROVIDER_KET;
import static com.carl.live.app.common.Constants.ComConstants.ONE_INT;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-17 13:21
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private IUserMapper userMapper;

    @Resource
    private RedisTemplate<String, UserDTO> redisTemplate;

    @Resource
    private RocketMqProducerConfig producerConfig;

    @Resource
    private IUserTagService userTagService;

    @Override
    public UserDTO getUserById(Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }
        if (Objects.nonNull(redisTemplate.opsForValue().get(String.valueOf(userId)))) {
            return redisTemplate.opsForValue().get(String.valueOf(userId));
        }
        UserDTO userDTO = ConvertBeanUtils.convert(userMapper.selectById(userId), UserDTO.class);
        redisTemplate.opsForValue().set(USER_PROVIDER_KET + String.valueOf(userId), userDTO, createRandomExpiredTime(), TimeUnit.SECONDS);
        return userDTO;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        if (Objects.isNull(userDTO) || Objects.isNull(userDTO.getUserId())) {
            return false;
        }
        int res = userMapper.updateById(ConvertBeanUtils.convert(userDTO, UserPO.class));
        if (res!=ONE_INT){
            return false;
        }
        redisTemplate.delete(String.valueOf(userDTO.getUserId()));
        try {
            MQProducer mqProducer = producerConfig.mqProducer();
            Message message = new Message();
            message.setTopic(MqConstants.CacheDeleteAsyncTopic);
            UserCacheDeleteAsyncDTO userCacheDeleteAsyncDTO = new UserCacheDeleteAsyncDTO();
            userCacheDeleteAsyncDTO.setCode(UserMqDeleteCodeEnum.DELETE_USER_INFO.getCode());
            Map<String, String> jsonMap = new HashMap<>();
            jsonMap.put("userId", String.valueOf(userDTO.getUserId()));
            String json = JSON.toJSONString(jsonMap);
            userCacheDeleteAsyncDTO.setJson(json);
            message.setBody(JSON.toJSONString(userCacheDeleteAsyncDTO).getBytes(StandardCharsets.UTF_8));
            // 延迟一秒发送 level-1对应1s左右，level-2对应10s左右
            message.setDelayTimeLevel(1);
            mqProducer.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 插入用户，同时需要事务插入用户标签记录
     *
     * @param userDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertUser(UserDTO userDTO) {
        if (Objects.isNull(userDTO) || Objects.isNull(userDTO.getUserId())) {
            return false;
        }
        int res = userMapper.insert(ConvertBeanUtils.convert(userDTO, UserPO.class));
        if (ONE_INT == res) {
            return userTagService.insertUserTag(userDTO.getUserId()) == 1;
        }
        return false;
    }

    /**
     * 批量查询用户信息
     * 1.查缓存
     * 2、查表
     * 3、回写缓存
     *
     * @param userIdList
     * @return
     */
    @Override
    public Map<Long, UserDTO> batchQueryUserInfoByList(List<Long> userIdList) {
        if (CollUtil.isEmpty(userIdList)) {
            return Maps.newHashMap();
        }
        List<UserDTO> resUserList = new ArrayList<>();
        // 查缓存
        List<String> redisKeyList = new ArrayList<>();
        userIdList.forEach(item -> redisKeyList.add(USER_PROVIDER_KET + String.valueOf(item)));
        List<UserDTO> multiRes = Objects.requireNonNull(redisTemplate.opsForValue().multiGet(redisKeyList))
                .stream()
                .filter(Objects::nonNull)
                .toList();
        resUserList.addAll(multiRes);
        Set<Long> inCacheUserIdSet = multiRes.stream().map(UserDTO::getUserId).collect(Collectors.toSet());
        List<Long> noInCacheUserIdList = userIdList.stream().filter(item -> !inCacheUserIdSet.contains(item)).toList();
        Map<Long, List<Long>> queryUserInfoMap = noInCacheUserIdList.stream().collect(Collectors.groupingBy(item -> item % 100));
        // 先取模分组，然后多线程查表，防止分表导致的union操作
        List<UserDTO> queryRes = new CopyOnWriteArrayList<>();
        queryUserInfoMap.values().parallelStream().forEach(idList -> {
            queryRes.addAll(ConvertBeanUtils.convertList(userMapper.selectBatchIds(idList), UserDTO.class));
        });
        if (CollUtil.isNotEmpty(queryRes)) {
            resUserList.addAll(queryRes);
            // 回写缓存
            Map<String, UserDTO> insertCacheUserMap = queryRes
                    .stream()
                    .collect(Collectors.toMap(item -> USER_PROVIDER_KET + String.valueOf(item.getUserId()), x -> x));
            redisTemplate.opsForValue().multiSet(insertCacheUserMap);
            redisTemplate.executePipelined(new SessionCallback<Object>() {
                @Override
                public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                    for (String key : insertCacheUserMap.keySet()) {
                        redisTemplate.expire(key, createRandomExpiredTime(), TimeUnit.SECONDS);
                    }
                    return null;
                }
            });
        }
        return resUserList.stream().collect(Collectors.toMap(UserDTO::getUserId, x -> x));
    }


    private int createRandomExpiredTime() {
        int time = ThreadLocalRandom.current().nextInt(1000);
        return time + 60 * 30;
    }
}
