package com.carl.live.bank.provider.rpc;

import com.carl.live.bank.interfaces.dto.PayOrderDTO;
import com.carl.live.bank.interfaces.rpc.IPayOrderRpc;
import com.carl.live.bank.provider.service.IPayOrderService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-16 20:38
 * @version: 1.0
 */
@DubboService
public class PayOrderRpcImpl implements IPayOrderRpc {
    @Resource
    private IPayOrderService payOrderService;

    @Override
    public boolean insertOne(PayOrderDTO payOrderDTO) {
        return payOrderService.insertOne(payOrderDTO);
    }

    @Override
    public boolean updateOrderStatus(String orderId, Integer status) {
        return payOrderService.updateOrderStatus(orderId, status);
    }

    @Override
    public boolean updateOrderStatusById(String id, Integer status) {
        return payOrderService.updateOrderStatusById(id, status);
    }
}
