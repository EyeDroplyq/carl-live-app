package com.carl.live.bank.interfaces.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-11 21:36
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public enum AccountStatusEnum {
    UN_VALID(0, "无效"),
    VALID(1, "有效"),
    FROZEN(2, "冻结");
    private int accountCode;
    private String desc;
}
