package com.carl.live.user.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description: 用户中台删除数据的时候发送给mq的DTO实体
 * @author: 小琦
 * @createDate: 2024-03-23 21:29
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCacheDeleteAsyncDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8864211197779632319L;

    /**
     * 业务标识
     */
    private int code;

    /**
     * json串
     */
    private String json;
}
