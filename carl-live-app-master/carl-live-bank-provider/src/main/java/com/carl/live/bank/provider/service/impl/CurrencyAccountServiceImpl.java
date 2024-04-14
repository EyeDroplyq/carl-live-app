package com.carl.live.bank.provider.service.impl;

import cn.hutool.core.lang.Assert;
import com.carl.live.app.common.constants.CacheConstants;
import com.carl.live.app.common.interfaces.ConvertBeanUtils;
import com.carl.live.bank.interfaces.dto.AccountTradeReqDTO;
import com.carl.live.bank.interfaces.dto.AccountTradeRespDTO;
import com.carl.live.bank.interfaces.dto.CarlCurrencyAccountDTO;
import com.carl.live.bank.interfaces.enums.AccountStatusEnum;
import com.carl.live.bank.provider.config.BankThreadPoolConfig;
import com.carl.live.bank.provider.dao.mapper.CurrencyAccountMapper;
import com.carl.live.bank.provider.dao.po.CarlCurrencyAccountPO;
import com.carl.live.bank.provider.service.ICurrencyAccountService;
import com.carl.live.bank.provider.service.ICurrencyAccountTradeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-11 21:03
 * @version: 1.0
 */
@Service
@Slf4j
public class CurrencyAccountServiceImpl implements ICurrencyAccountService {
    @Resource
    private CurrencyAccountMapper currencyAccountMapper;

    @Resource
    private ICurrencyAccountTradeService currencyAccountTradeService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private BankThreadPoolConfig threadPoolConfig;

    /**
     * 增加余额
     *
     * @param userId
     * @param num
     */
    @Override
    public void incr(long userId, int num) {
        currencyAccountMapper.incr(userId, num);
    }

    /**
     * 扣减余额
     *
     * @param userId
     * @param num
     */
    @Override
    public void decr(long userId, int num) {
        currencyAccountMapper.decr(userId, num);
    }

    /**
     * 得到余额
     *
     * @param userId
     * @return
     */
    @Override
    public Integer getBalance(long userId) {
        String balanceKey = CacheConstants.USER_ACCOUNT_BALANCE_KEY + userId;
        Object balance = redisTemplate.opsForValue().get(balanceKey);
        if (!ObjectUtils.isEmpty(balance)) {
            if ((Integer) balance == -1) {
                return null;
            }
            return (Integer) balance;
        }
        int curBalance = currencyAccountMapper.queryBalance(userId);
        if (!ObjectUtils.isEmpty(curBalance)) {
            redisTemplate.opsForValue().set(balanceKey, curBalance, 30, TimeUnit.MINUTES);
        } else {
            redisTemplate.opsForValue().set(balanceKey, null, 5, TimeUnit.MINUTES);
        }
        return curBalance;
    }

    /**
     * 得到账户信息
     *
     * @param userId
     * @return
     */
    @Override
    public CarlCurrencyAccountDTO getByUserId(long userId) {
        return ConvertBeanUtils.convert(currencyAccountMapper.selectById(userId), CarlCurrencyAccountDTO.class);
    }

    /**
     * 创建账户信息
     *
     * @param userId
     * @return
     */
    @Override
    public boolean insertOne(long userId) {
        CarlCurrencyAccountPO carlCurrencyAccountPO = new CarlCurrencyAccountPO();
        carlCurrencyAccountPO.setUserId(userId);
        carlCurrencyAccountPO.setCurrentBalance(0);
        carlCurrencyAccountPO.setTotalCharged(0);
        carlCurrencyAccountPO.setStatus(AccountStatusEnum.VALID.getAccountCode());
        return currencyAccountMapper.insert(carlCurrencyAccountPO) == 1 ? true : false;
    }

    /**
     * 送礼物消费账户余额
     * 查询当前用户的余额
     * 判断余额是不是充足
     * 扣减余额
     * 保存流水
     *
     * @param accountTradeReqDTO
     * @return
     */
    @Override
    public AccountTradeRespDTO consumeSendGift(AccountTradeReqDTO accountTradeReqDTO) {
        long userId = accountTradeReqDTO.getUserId();
        Assert.notNull(userId, "用户id不能为空");
        int num = accountTradeReqDTO.getNum();
        Assert.notNull(num, "扣减余额不能为空");
        Integer currentBalance = this.getBalance(userId);
        if (ObjectUtils.isEmpty(currentBalance)) {
            throw new RuntimeException("余额信息异常");
        }
        if (num > currentBalance) {
            throw new RuntimeException("账户余额不足");
        }
        String balanceKey = CacheConstants.USER_ACCOUNT_BALANCE_KEY + userId;
        redisTemplate.opsForValue().decrement(balanceKey, -num);
        ThreadPoolExecutor threadPoolExecutor = threadPoolConfig.threadPoolExecutor();
        // 异步操作db
        threadPoolExecutor.execute(() -> {
            consumeDbHandler(userId, num);
        });
        return new AccountTradeRespDTO(userId, true, "");
    }

    /**
     * 扣减余额，保存流水的db操作
     *
     * @param userId
     * @param num
     */
    @Transactional(rollbackFor = Exception.class)
    private void consumeDbHandler(long userId, int num) {
        this.decr(userId, num);
        currencyAccountTradeService.insertOne(userId, num, 0);
    }
}
