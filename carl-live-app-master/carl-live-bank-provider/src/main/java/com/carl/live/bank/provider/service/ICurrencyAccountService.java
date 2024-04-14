package com.carl.live.bank.provider.service;

import com.carl.live.bank.interfaces.dto.AccountTradeReqDTO;
import com.carl.live.bank.interfaces.dto.AccountTradeRespDTO;
import com.carl.live.bank.interfaces.dto.CarlCurrencyAccountDTO;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-11 21:03
 * @version: 1.0
 */
public interface ICurrencyAccountService {

    /**
     * 增加虚拟币
     *
     * @param userId
     * @param num
     */
    void incr(long userId,int num);

    /**
     * 扣减虚拟币
     *
     * @param userId
     * @param num
     */
    void decr(long userId,int num);

    /**
     * 查询余额
     *
     * @param userId
     * @return
     */
    Integer getBalance(long userId);

    /**
     * 通过用户id查询用户账户信息
     */
    CarlCurrencyAccountDTO getByUserId(long userId);
    /**
     * 创建账户信息
     * @param userId
     * @return
     */
    boolean insertOne(long userId);

    /**
     * 送礼物消费账户余额
     * @param accountTradeReqDTO
     * @return
     */
    AccountTradeRespDTO consumeSendGift(AccountTradeReqDTO accountTradeReqDTO);
}
