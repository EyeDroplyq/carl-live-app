package com.carl.live.gift.interfaces.rpc;

import com.carl.live.gift.interfaces.dto.RedPacketConfigDTO;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-21 20:30
 * @version: 1.0
 */
public interface IRedPacketConfigRpc {
    /**
     * 通过主播id查询红包雨配置
     * @param anchorId
     * @return
     */
    RedPacketConfigDTO queryByAnchorId(Integer anchorId);

    /**
     * 新增红包
     * @param redPacketConfigDTO
     * @return
     */
    boolean addOne(RedPacketConfigDTO redPacketConfigDTO);

    /**
     * 更新红包配置
     * @param redPacketConfigDTO
     * @return
     */
    boolean updateById(RedPacketConfigDTO redPacketConfigDTO);
}
