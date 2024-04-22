package com.carl.live.gift.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-22 20:56
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedPacketReceiveDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3875450383363538880L;

    /**
     * 抢到的钱
     */
    private Integer price;
}
