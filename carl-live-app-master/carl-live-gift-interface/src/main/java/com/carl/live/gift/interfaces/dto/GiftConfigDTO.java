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
 * @createDate: 2024-04-06 15:45
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftConfigDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2891319642303498534L;
    private Integer giftId;
    private Integer price;
    private String giftName;
    private Integer status;
    private String coverImgUrl;
    private String svgaUrl;
    private Date createTime;
    private Date updateTime;
}
