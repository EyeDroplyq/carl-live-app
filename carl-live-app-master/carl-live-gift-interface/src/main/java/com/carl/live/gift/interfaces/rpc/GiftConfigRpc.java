package com.carl.live.gift.interfaces.rpc;

import com.carl.live.gift.interfaces.dto.GiftConfigDTO;
import com.carl.live.gift.interfaces.dto.GiftRecordDTO;

import java.util.List;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 15:18
 * @version: 1.0
 */
public interface GiftConfigRpc {
    /**
     * 查询礼物配置详情
     * @param giftId
     * @return
     */
    GiftConfigDTO getByGiftId(int giftId);

    /**
     * 查询礼物配置列表
     * @return
     */
    List<GiftConfigDTO> queryGiftConfigList();

    /**
     * 插入一个礼物篇配置
     * @param giftConfigDTO
     * @return
     */
    boolean insertOneGiftConfig(GiftConfigDTO giftConfigDTO);

    /**
     * 更新一个礼物配置
     * @param giftConfigDTO
     * @return
     */
    boolean updateOneGiftConfig(GiftConfigDTO giftConfigDTO);

    /**
     * 插入一条礼物流水记录
     * @param giftRecordDTO
     * @return
     */
    boolean insertOneGiftRecord(GiftRecordDTO giftRecordDTO);

    /**
     * 送礼
     * @param giftConfigDTO
     * @return
     */
    boolean send(GiftConfigDTO giftConfigDTO);
}
