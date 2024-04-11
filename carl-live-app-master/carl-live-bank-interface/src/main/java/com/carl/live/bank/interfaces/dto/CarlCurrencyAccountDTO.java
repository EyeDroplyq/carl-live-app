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
 * @createDate: 2024-04-11 21:04
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarlCurrencyAccountDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3249023343157678803L;
    private Long userId;
    private int currentBalance;
    private int totalCharged;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
