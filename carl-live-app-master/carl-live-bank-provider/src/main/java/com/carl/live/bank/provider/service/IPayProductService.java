package com.carl.live.bank.provider.service;

import com.carl.live.bank.interfaces.dto.PayProductDTO;

import java.util.List;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-14 21:57
 * @version: 1.0
 */
public interface IPayProductService {
    /**
     * 通过支付产品类型查询平台所有的支付产品类型
     * @param type
     * @return
     */
    List<PayProductDTO> products(int type);
}
