package com.carl.sms.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-25 21:50
 * @version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MsgCheckDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8953394651182600213L;
    private boolean checkStatus;
    private String desc;
}
