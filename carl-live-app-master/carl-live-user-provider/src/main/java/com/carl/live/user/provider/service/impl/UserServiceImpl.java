package com.carl.live.user.provider.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.carl.live.app.common.interfaces.ConvertBeanUtils;
import com.carl.live.user.interfaces.dto.UserDTO;
import com.carl.live.user.provider.dao.mapper.IUserMapper;
import com.carl.live.user.provider.dao.po.UserPO;
import com.carl.live.user.provider.service.IUserService;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.carl.live.app.common.Constants.CacheConstants.USER_PROVIDER_KET;

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
        return res == 1;
    }

    @Override
    public boolean insertUser(UserDTO userDTO) {
        if (Objects.isNull(userDTO) || Objects.isNull(userDTO.getUserId())) {
            return false;
        }
        int res = userMapper.insert(ConvertBeanUtils.convert(userDTO, UserPO.class));
        return res == 1;
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
