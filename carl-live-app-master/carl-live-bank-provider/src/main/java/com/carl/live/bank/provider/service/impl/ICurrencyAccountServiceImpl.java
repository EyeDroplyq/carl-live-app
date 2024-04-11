package com.carl.live.bank.provider.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carl.live.app.common.interfaces.ConvertBeanUtils;
import com.carl.live.bank.interfaces.dto.AccountTradeReqDTO;
import com.carl.live.bank.interfaces.dto.AccountTradeRespDTO;
import com.carl.live.bank.interfaces.dto.CarlCurrencyAccountDTO;
import com.carl.live.bank.interfaces.enums.AccountStatusEnum;
import com.carl.live.bank.provider.dao.mapper.CurrencyAccountMapper;
import com.carl.live.bank.provider.dao.po.CarlCurrencyAccountPO;
import com.carl.live.bank.provider.service.ICurrencyAccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-11 21:03
 * @version: 1.0
 */
@Service
@Slf4j
public class ICurrencyAccountServiceImpl implements ICurrencyAccountService {
    @Resource
    private CurrencyAccountMapper currencyAccountMapper;

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
        QueryWrapper<CarlCurrencyAccountPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("status", AccountStatusEnum.VALID.getAccountCode());
        queryWrapper.last("limit 1");
        CarlCurrencyAccountPO carlCurrencyAccountPO = currencyAccountMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(carlCurrencyAccountPO)) {
            throw new RuntimeException("账户信息异常");
        }
        return carlCurrencyAccountPO.getCurrentBalance();
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
     * 1、查询账户信息
     * 2、判断余额是否充足，如果充足则扣减余额
     *
     * @param accountTradeReqDTO
     * @return
     */
    @Override
    public AccountTradeRespDTO consume(AccountTradeReqDTO accountTradeReqDTO) {
        long userId = accountTradeReqDTO.getUserId();
        int num = accountTradeReqDTO.getNum();
        Assert.notNull(userId, "用户id不能为空");
        Integer balance = getBalance(userId);
        if (ObjectUtils.isEmpty(balance) || balance < num) {
            throw new RuntimeException("扣减失败，余额不足");
        }
        // todo 保存消费流水
        // todo 高并发性能优化
        this.decr(userId, num);
        AccountTradeRespDTO accountTradeRespDTO = new AccountTradeRespDTO();
        accountTradeRespDTO.setSuccess(true);
        accountTradeRespDTO.setUserId(userId);
        accountTradeRespDTO.setErrorMsg("");
        return accountTradeRespDTO;
    }
}
