package com.carl.live.bank.provider.service.impl;

import com.carl.live.app.common.interfaces.ConvertBeanUtils;
import com.carl.live.bank.interfaces.dto.PayOrderDTO;
import com.carl.live.bank.provider.dao.mapper.IPayOrderMapper;
import com.carl.live.bank.provider.dao.po.PayOrderPO;
import com.carl.live.bank.provider.service.IPayOrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-15 21:58
 * @version: 1.0
 */
@Service
@Slf4j
public class PayOrderServiceImpl implements IPayOrderService {
    @Resource
    private IPayOrderMapper payOrderMapper;

    /**
     * 保存订单信息
     *
     * @param payOrderDTO
     * @return
     */
    @Override
    public boolean insertOne(PayOrderDTO payOrderDTO) {
        PayOrderPO payOrderPO = ConvertBeanUtils.convert(payOrderDTO, PayOrderPO.class);
        return payOrderMapper.insert(payOrderPO) == 1;
    }

    @Override
    public boolean updateOrderStatus(String orderId, Integer status) {
        return payOrderMapper.updateOrderStatus(orderId, status) == 1;
    }

    @Override
    public boolean updateOrderStatusById(String id, Integer status) {
        return payOrderMapper.updateOrderStatusById(id, status) == 1;
    }
}
