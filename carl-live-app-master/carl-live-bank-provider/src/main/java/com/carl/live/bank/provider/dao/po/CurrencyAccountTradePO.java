package com.carl.live.bank.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-11 21:00
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_carl_currency_trade")
public class CurrencyAccountTradePO implements Serializable {


    @Serial
    private static final long serialVersionUID = 7607492034250560309L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Integer num;
    private Integer type;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
