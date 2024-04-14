package com.carl.live.bank.provider.service;

import com.carl.live.bank.interfaces.dto.AccountTradeReqDTO;
import com.carl.live.bank.interfaces.dto.AccountTradeRespDTO;
import com.carl.live.bank.interfaces.dto.CarlCurrencyAccountDTO;
import com.carl.live.bank.provider.dao.po.CurrencyAccountTradePO;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-11 21:03
 * @version: 1.0
 */
public interface ICurrencyAccountTradeService {

    /**
     * 保存流水
     * @param userId
     * @param num
     * @param type
     * @return
     */
    boolean insertOne(long userId,int num,int type);
}
