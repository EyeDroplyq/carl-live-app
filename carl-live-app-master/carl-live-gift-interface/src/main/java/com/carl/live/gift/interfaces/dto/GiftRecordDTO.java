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
 * @createDate: 2024-04-06 16:10
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftRecordDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4009762963146861794L;
    private Long id;
    private Long userId;
    private Long objectId;
    private Integer source;
    private Integer price;
    private Integer priceUnit;
    private Integer giftId;
    private Date sendTime;
}
