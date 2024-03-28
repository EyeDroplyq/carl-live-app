package com.carl.live.user.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-28 20:38
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPhoneDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3884539764731943666L;

    private Long id;
    private Long userId;
    private String phone;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
