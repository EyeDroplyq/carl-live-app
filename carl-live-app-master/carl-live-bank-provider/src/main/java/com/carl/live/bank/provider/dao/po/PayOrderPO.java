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
 * @createDate: 2024-04-15 21:56
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_pay_order")
public class PayOrderPO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8754961466807875180L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderId;
    private Integer productId;
    private Long userId;
    private Integer source;
    private Integer payChannel;
    private Integer status;
    private Date payTime;
    private Date createTime;
    private Date updateTime;

}
