package com.carl.live.bank.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-11 22:11
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTradeRespDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -7048459600373997357L;

    private long userId;

    private boolean isSuccess;
    private String errorMsg;


}
