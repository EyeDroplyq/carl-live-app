package com.carl.live.bank.provider.service;

import com.carl.live.bank.interfaces.dto.PayOrderDTO;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-15 21:58
 * @version: 1.0
 */
public interface IPayOrderService {

    /**
     * 保存订单信息
     * @param payOrderDTO
     * @return
     */
    boolean insertOne(PayOrderDTO payOrderDTO);

    /**
     * 更新订单状态
     * @param orderId
     * @param status
     * @return
     */
    boolean updateOrderStatus(String orderId,Integer status);

    /**
     * 更新订单状态
     * @param id
     * @param status
     * @return
     */
    boolean updateOrderStatusById(String id,Integer status);
}
