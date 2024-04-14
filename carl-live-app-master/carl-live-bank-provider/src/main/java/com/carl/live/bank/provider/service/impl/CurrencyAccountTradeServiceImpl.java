package com.carl.live.bank.provider.service.impl;

import com.carl.live.bank.provider.dao.mapper.CurrencyAccountTradeMapper;
import com.carl.live.bank.provider.dao.po.CurrencyAccountTradePO;
import com.carl.live.bank.provider.service.ICurrencyAccountTradeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-14 15:29
 * @version: 1.0
 */
@Service
@Slf4j
public class CurrencyAccountTradeServiceImpl implements ICurrencyAccountTradeService {
    @Resource
    private CurrencyAccountTradeMapper currencyAccountTradeMapper;

    /**
     * 保存流水记录
     *
     * @param userId
     * @param num
     * @param type
     * @return
     */
    @Override
    public boolean insertOne(long userId, int num, int type) {
        CurrencyAccountTradePO currencyAccountTradePO = new CurrencyAccountTradePO();
        currencyAccountTradePO.setUserId(userId);
        currencyAccountTradePO.setNum(num);
        currencyAccountTradePO.setType(type);
        return currencyAccountTradeMapper.insert(currencyAccountTradePO) == 1;
    }
}
