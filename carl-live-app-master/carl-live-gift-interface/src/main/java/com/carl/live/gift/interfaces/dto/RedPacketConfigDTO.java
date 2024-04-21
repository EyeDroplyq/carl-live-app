package com.carl.live.gift.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-21 20:29
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedPacketConfigDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 993389858679007049L;

    private Integer id;
    private Integer anchorId;
    private Date startTime;
    private Integer totalGet;
    private Integer totalGetPrice;
    private Integer maxGetPrice;
    private Integer status;
    private Integer totalPrice;
    private Integer totalCount;
    private String remark;

    private String configCode;

    private Date createTime;
    private Date updateTime;
}
