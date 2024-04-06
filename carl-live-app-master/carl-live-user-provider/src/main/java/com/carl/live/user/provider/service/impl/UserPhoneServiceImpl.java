package com.carl.live.user.provider.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carl.live.app.common.constants.CacheConstants;
import com.carl.live.app.common.constants.ComConstants;
import com.carl.live.app.common.enums.UserPhoneEnum;
import com.carl.live.app.common.interfaces.ConvertBeanUtils;
import com.carl.live.id.generate.interfaces.IdGenerate;
import com.carl.live.user.interfaces.dto.UserLoginDTO;
import com.carl.live.user.interfaces.dto.UserPhoneDTO;
import com.carl.live.user.provider.dao.mapper.IUserMapper;
import com.carl.live.user.provider.dao.mapper.IUserPhoneMapper;
import com.carl.live.user.provider.dao.po.UserPO;
import com.carl.live.user.provider.dao.po.UserPhonePO;
import com.carl.live.user.provider.service.IUserPhoneService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-28 20:41
 * @version: 1.0
 */
@Service
public class UserPhoneServiceImpl implements IUserPhoneService {
    @Resource
    private IUserPhoneMapper userPhoneMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @DubboReference
    private IdGenerate idGenerate;

    @Resource
    private IUserMapper userMapper;

    //为了防止缓存击穿
    private volatile Object lock = new Object();


    /**
     * 登录或注册功能
     * 1、通过手机号从redis查询实体，有直接返回，没有查表
     * 2、表中没有则插入，然后写回缓存
     *
     * @param phone
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserLoginDTO login(String phone) {
        if (!StringUtils.hasText(phone)) {
            throw new RuntimeException("手机号不能为空");
        }
        //1、通过手机号从redis查询实体，有直接返回，没有查表
        String token = RandomUtil.randomString(18);
        String userLoginKey = CacheConstants.USER_LOGIN_KEY + token;
        UserLoginDTO userLoginDTO = (UserLoginDTO) redisTemplate.opsForValue().get(userLoginKey);
        if (!ObjectUtils.isEmpty(userLoginDTO)) {
            return UserLoginDTO.loginSuccess(userLoginDTO.getUserId());
        }
        UserPhonePO userPhonePO = new UserPhonePO();
        //双重检查锁机制
        synchronized (lock) {
            userLoginDTO = (UserLoginDTO) redisTemplate.opsForValue().get(userLoginKey);
            if (!ObjectUtils.isEmpty(userLoginDTO)) {
                return UserLoginDTO.loginSuccess(userLoginDTO.getUserId());
            }
            //查表
            QueryWrapper<UserPhonePO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", phone);
            queryWrapper.eq("status", UserPhoneEnum.VALID.getCode());
            queryWrapper.last("limit 1");
            userPhonePO = userPhoneMapper.selectOne(queryWrapper);
            if (!ObjectUtils.isEmpty(userPhonePO)) {
                redisTemplate.opsForValue().set(userLoginKey, ConvertBeanUtils.convert(userPhonePO, UserLoginDTO.class), 10, TimeUnit.MINUTES);
                return UserLoginDTO.loginSuccess(userPhonePO.getUserId());
            }
        }
        //没注册过，防止这个phone大量请求过来造成缓存穿透，所以缓存null值
        redisTemplate.opsForValue().set(userLoginKey, null, 10, TimeUnit.MINUTES);
        // 2、表中没有则插入，然后写回缓存
        userPhonePO = register(phone);
        //注册完后删掉原来的缓存, 使用读时缓存方案来保证数据库和缓存的一致性
        redisTemplate.delete(userLoginKey);
        return UserLoginDTO.loginSuccess(userPhonePO.getUserId());
    }


    /**
     * 通过userId获取查询手机信息列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserPhoneDTO> queryByUserId(Long userId) {
        if (Objects.isNull(userId)) {
            throw new RuntimeException("用户id不能为空");
        }
        QueryWrapper<UserPhonePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("status", UserPhoneEnum.VALID.getCode());
        List<UserPhonePO> userPhoneList = userPhoneMapper.selectList(queryWrapper);
        return ConvertBeanUtils.convertList(userPhoneList, UserPhoneDTO.class);
    }

    /**
     * 通过手机号查询用户的手机信息
     *
     * @param phone
     * @return
     */
    @Override
    public UserPhoneDTO queryByPhone(String phone) {
        if (!StringUtils.hasLength(phone)) {
            throw new RuntimeException("手机号不能为空");
        }
        QueryWrapper<UserPhonePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        queryWrapper.eq("status", UserPhoneEnum.VALID.getCode());
        UserPhonePO userPhone = userPhoneMapper.selectOne(queryWrapper);
        return ConvertBeanUtils.convert(userPhone, UserPhoneDTO.class);
    }


    //*******************************private域********************************

    /**
     * 注册
     *
     * @param phone
     */
    private UserPhonePO register(String phone) {
        UserPO userPO = new UserPO();
        Long userId = idGenerate.getUnseqId(ComConstants.ONE_INT);
        userPO.setUserId(userId);
        userPO.setNickName("carl");
        userMapper.insert(userPO);
        UserPhonePO userPhone = new UserPhonePO();
        userPhone.setUserId(userId);
        userPhone.setPhone(phone);
        userPhone.setStatus(UserPhoneEnum.VALID.getCode());
        userPhoneMapper.insert(userPhone);
        return userPhone;
    }
}
