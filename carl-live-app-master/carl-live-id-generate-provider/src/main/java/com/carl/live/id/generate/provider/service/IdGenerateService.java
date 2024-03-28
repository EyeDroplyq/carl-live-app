package com.carl.live.id.generate.provider.service;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-19 21:01
 * @version: 1.0
 */
public interface IdGenerateService {
    /**
     * 获取有序id
     *
     * @param id
     * @return
     */
    Long getSeqId(Integer id);

    /**
     * 获取无序id
     *
     * @param id
     * @return
     */
    Long getUnseqId(Integer id);
}
