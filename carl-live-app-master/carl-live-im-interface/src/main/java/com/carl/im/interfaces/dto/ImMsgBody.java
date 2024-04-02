package com.carl.im.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-02 21:23
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImMsgBody implements Serializable {

    @Serial
    private static final long serialVersionUID = -924297894876084837L;

    private int appId;

    private long userId;

    private String token;

    /**
     * 和业务服务进行消息传递
     */
    private String data;
}
