package com.carl.live.bank.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carl.live.bank.provider.dao.po.PayOrderPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-15 21:57
 * @version: 1.0
 */
@Mapper
public interface IPayOrderMapper extends BaseMapper<PayOrderPO> {
    @Update("update t_pay_order set status=#{status} where order_id=#{orderId}")
    int updateOrderStatus(@Param("orderId") String orderId, @Param("status") Integer status);

    @Update("update t_pay_order set status=#{status} where id=#{id}")
    int updateOrderStatusById(@Param("id") String id, @Param("status") Integer status);
}
