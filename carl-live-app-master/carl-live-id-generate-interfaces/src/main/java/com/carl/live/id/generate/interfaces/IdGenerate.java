package com.carl.live.id.generate.interfaces;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-19 20:55
 * @version: 1.0
 */
public interface IdGenerate {
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
