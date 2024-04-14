package com.carl.live.bank.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carl.live.bank.provider.dao.po.CarlCurrencyAccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-11 21:02
 * @version: 1.0
 */
@Mapper
public interface CurrencyAccountMapper extends BaseMapper<CarlCurrencyAccountPO> {

    @Update("update t_carl_currency_account set current_balance=current_balance+#{num} where user_id=#{userId}")
    void incr(@Param("userId") long userId, @Param("num") int num);

    @Update("update t_carl_currency_account set current_balance=current_balance-#{num} where user_id=#{userId}")

    void decr(@Param("userId") long userId, @Param("num") int num);

    @Select("select current_balance from t_carl_currency_account where user_id=#{userId} and status=1")
    int queryBalance(@Param("userId") long userId);
}
