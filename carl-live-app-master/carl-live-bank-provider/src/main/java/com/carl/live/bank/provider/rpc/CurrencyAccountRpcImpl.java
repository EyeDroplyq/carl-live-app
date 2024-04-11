package com.carl.live.bank.provider.rpc;

import com.carl.live.bank.interfaces.dto.AccountTradeReqDTO;
import com.carl.live.bank.interfaces.dto.AccountTradeRespDTO;
import com.carl.live.bank.interfaces.dto.CarlCurrencyAccountDTO;
import com.carl.live.bank.interfaces.rpc.ICurrencyAccountRpc;
import com.carl.live.bank.provider.service.ICurrencyAccountService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-11 21:03
 * @version: 1.0
 */
@DubboService
public class CurrencyAccountRpcImpl implements ICurrencyAccountRpc {
    @Resource
    private ICurrencyAccountService currencyAccountService;

    @Override
    public void incr(long userId, int num) {
        currencyAccountService.incr(userId,num);
    }

    @Override
    public void decr(long userId, int num) {
        currencyAccountService.decr(userId,num);
    }

    @Override
    public Integer getBalance(long userId) {
        return currencyAccountService.getBalance(userId);
    }

    @Override
    public CarlCurrencyAccountDTO getByUserId(long userId) {
        return currencyAccountService.getByUserId(userId);
    }

    @Override
    public boolean insertOne(long userId) {
        return currencyAccountService.insertOne(userId);
    }

    /**
     * 送礼物，消费账户
     * @param accountTradeReqDTO
     * @return
     */
    @Override
    public AccountTradeRespDTO consume(AccountTradeReqDTO accountTradeReqDTO) {
        return currencyAccountService.consume(accountTradeReqDTO);
    }
}
