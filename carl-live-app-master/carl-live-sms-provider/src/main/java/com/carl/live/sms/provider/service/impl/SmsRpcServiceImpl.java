package com.carl.live.sms.provider.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.carl.live.app.common.Constants.CacheConstants;
import com.carl.live.app.common.config.AsyncThreadPoolConfig;
import com.carl.live.sms.provider.dao.SmsMapper;
import com.carl.live.sms.provider.dao.SmsPO;
import com.carl.live.sms.provider.service.ISmsRpcService;
import com.carl.sms.interfaces.dto.MsgCheckDTO;
import com.carl.sms.interfaces.enums.MsgSendResultEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-25 21:53
 * @version: 1.0
 */
@Service
@Slf4j
public class SmsRpcServiceImpl implements ISmsRpcService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private SmsMapper smsMapper;


    /**
     * 发送验证码
     * 1、校验手机号合法性
     * 2、将手机号和发送验证码的时间记录在redis中
     * 3、生成随机验证码，并保存在redis中
     * 4、异步发送验证码，并保存验证码流水
     *
     * @param phone
     * @return
     */
    @Override
    public MsgSendResultEnum sendMessage(String phone) {
        // 1、校验手机号合法性
        checkPhoneValid(phone);
        // 2、将手机号和发送验证码的时间记录在redis中
        String sendTimeRedisKey = CacheConstants.SMS_PROVIDER_SEND_TIME_KEY + phone;
        if (Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(sendTimeRedisKey, String.valueOf(System.currentTimeMillis()), 60, TimeUnit.SECONDS))) {
            return MsgSendResultEnum.REPEAT_SEND;
        }
        // 3、生成随机验证码，并保存在redis中
        String randomNumbers = RandomUtil.randomNumbers(6);
        String randomNumberRedisKey = CacheConstants.SMS_PROVIDER_KEY + phone;
        redisTemplate.opsForValue().set(randomNumberRedisKey, randomNumbers, 3, TimeUnit.MINUTES);
        //4、异步发送验证码，并保存验证码流水
        try {
            CompletableFuture.runAsync(() -> {
                // 模拟发送短信验证码
                mockSendMsm(phone, randomNumbers);
                insertOne(phone, randomNumbers);
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return MsgSendResultEnum.SEND_SUCCESS;
    }


    /**
     * 校验验证码是否正确
     * 1、校验手机号合法性
     * 2、从redis中取出验证码进行对比
     * 3、删除缓存
     *
     * @param phone
     * @param code
     * @return
     */
    @Override
    public MsgCheckDTO checkLoginCode(String phone, String code) {
        checkPhoneValid(phone);
        if (!StringUtils.hasLength(code)) {
            return new MsgCheckDTO(false, "验证码不正确");
        }
        String cacheCode = redisTemplate.opsForValue().get(CacheConstants.SMS_PROVIDER_KEY + phone);
        if (!StringUtils.hasLength(cacheCode)) {
            return new MsgCheckDTO(false, "验证码已过期");
        }
        if (!code.equals(cacheCode)) {
            return new MsgCheckDTO(false, "验证码或手机号不正确");
        }
        redisTemplate.delete(CacheConstants.SMS_PROVIDER_KEY + phone);
        return new MsgCheckDTO(true, "验证成功");
    }

    @Override
    public void insertOne(String phone, String code) {
        SmsPO smsPO = new SmsPO();
        smsPO.setPhone(phone);
        smsPO.setCode(code);
        smsPO.setSendTime(new Date());
        smsMapper.insert(smsPO);
    }


    //**********************************************private域***********************************

    /**
     * 校验手机号合法性
     *
     * @param phone
     */
    private void checkPhoneValid(String phone) {
        if (StringUtils.hasLength(phone)) {
            if (!Pattern.matches("^1[3-9]\\d{9}$", phone)) {
                throw new RuntimeException("手机号格式不正确");
            }
        }
    }

    /**
     * 模拟发送验证码
     *
     * @param phone
     * @param code
     */
    private void mockSendMsm(String phone, String code) {
        try {
            log.info("短信验证码正在发送....");
            Thread.sleep(2000);
            log.info("短信验证码为：{}", code);
            log.info("短信验证码发送完成....");
        } catch (Exception e) {
            throw new RuntimeException("发送验证码失败");
        }
    }

}
