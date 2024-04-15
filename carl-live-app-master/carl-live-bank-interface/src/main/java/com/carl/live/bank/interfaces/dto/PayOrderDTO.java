package com.carl.live.bank.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-15 21:57
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6823738754791144667L;
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
