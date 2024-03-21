package com.carl.live.id.generate.provider.bo;

import lombok.Data;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: 无序id本地内存BO
 * @author: 小琦
 * @createDate: 2024-03-19 20:58
 * @version: 1.0
 */
@Data
public class LocalUnSeqIdBO {
    /**
     * id
     */
    private Integer id;

    /**
     * 并发安全队列，用来存放打乱顺序的id
     */
    private ConcurrentLinkedQueue<Long> idQueue;

    /**
     * 最大id值
     */
    private Long nextThreshold;

    /**
     * 可以取的初始id值
     */
    private Long currentStart;
}
