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
 * @createDate: 2024-04-14 21:53
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayProductDTO implements Serializable {


    @Serial
    private static final long serialVersionUID = -5043850555004098429L;

    private Long id;
    private String name;
    private Integer price;
    private String extra;
    private Integer type;
    private Integer validStatus;
    private Date createTime;
    private Date updateTime;
}
