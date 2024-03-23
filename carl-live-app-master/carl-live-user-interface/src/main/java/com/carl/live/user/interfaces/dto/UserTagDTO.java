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
 * @createDate: 2024-03-23 18:09
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8884077480194362145L;
    private Long userId;
    private Long tagInfo01;
    private Long tagInfo02;
    private Long tagInfo03;
    private Date createTime;
    private Date updateTime;
}
