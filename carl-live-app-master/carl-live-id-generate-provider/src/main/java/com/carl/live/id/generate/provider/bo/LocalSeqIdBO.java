package com.carl.live.id.generate.provider.bo;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-19 20:58
 * @version: 1.0
 */
@Data
public class LocalSeqIdBO {
    /**
     * id
     */
    private Integer id;

    /**
     * 当前开始的起始序列号
     */
    private AtomicLong currentValue;

    /**
     * 最大id值
     */
    private Long nextThreshold;

    /**
     * 可以取的初始id值
     */
    private Long currentStart;
}
