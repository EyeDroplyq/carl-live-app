package com.carl.live.bank.provider.rpc;

import com.carl.live.bank.interfaces.dto.PayProductDTO;
import com.carl.live.bank.interfaces.rpc.IPayProductRpc;
import com.carl.live.bank.provider.service.IPayProductService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-14 21:56
 * @version: 1.0
 */
@DubboService
public class PayProductRpcImpl implements IPayProductRpc {
    @Resource
    private IPayProductService payProductService;

    /**
     * 通过产品类型查询平台的所有支付产品列表
     * @param type
     * @return
     */
    @Override
    public List<PayProductDTO> products(int type) {
        return payProductService.products(type);
    }

    /**
     * 通过产品id查询支付产品
     * @param productId
     * @return
     */
    @Override
    public PayProductDTO getByProductId(Integer productId) {
        return payProductService.getByProductId(productId);
    }
}
