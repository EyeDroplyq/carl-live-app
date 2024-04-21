package com.carl.live.gift.provider.service;

import com.carl.live.gift.provider.dao.po.RedPacketConfigPO;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-21 17:17
 * @version: 1.0
 */
public interface IRedPacketConfigService {
    /**
     * 通过主播id查询红包雨配置
     * @param anchorId
     * @return
     */
    RedPacketConfigPO queryByAnchorId(Integer anchorId);

    /**
     * 新增红包
     * @param redPacketConfigPO
     * @return
     */
    boolean addOne(RedPacketConfigPO redPacketConfigPO);

    /**
     * 更新红包配置
     * @param redPacketConfigPO
     * @return
     */
    boolean updateById(RedPacketConfigPO redPacketConfigPO);

    /**
     * 生成红包雨
     * @param anchorId
     * @return
     */
    boolean prepareRedPacket(Integer anchorId);
}
