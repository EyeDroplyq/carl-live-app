package com.carl.live.bank.interfaces.rpc;

import com.carl.live.bank.interfaces.dto.PayProductDTO;

import java.util.List;

/**
 * @description: 平台支付产品rpc接口
 * @author: 小琦
 * @createDate: 2024-04-14 21:53
 * @version: 1.0
 */
public interface IPayProductRpc {
    /**
     * 通过支付产品类型查询平台支付产品列表
     * @param type
     * @return
     */
    List<PayProductDTO> products(int type);
}
