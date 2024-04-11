package com.carl.live.gift.provider.rpc;

import com.carl.live.gift.interfaces.dto.GiftConfigDTO;
import com.carl.live.gift.interfaces.dto.GiftRecordDTO;
import com.carl.live.gift.interfaces.rpc.GiftConfigRpc;
import com.carl.live.gift.provider.service.GiftConfigService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 15:19
 * @version: 1.0
 */
@DubboService
public class GiftConfigRpcImpl implements GiftConfigRpc {
    @Resource
    private GiftConfigService giftConfigService;

    @Override
    public GiftConfigDTO getByGiftId(int giftId) {
        return giftConfigService.getByGiftId(giftId);
    }

    @Override
    public List<GiftConfigDTO> queryGiftConfigList() {
        return giftConfigService.queryGiftConfigList();
    }

    @Override
    public boolean insertOneGiftConfig(GiftConfigDTO giftConfigDTO) {
        return giftConfigService.insertOneGiftConfig(giftConfigDTO);
    }

    @Override
    public boolean updateOneGiftConfig(GiftConfigDTO giftConfigDTO) {
        return giftConfigService.updateOneGiftConfig(giftConfigDTO);
    }

    /**
     * 插入一条礼物流水记录
     *
     * @param giftRecordDTO
     * @return
     */
    @Override
    public boolean insertOneGiftRecord(GiftRecordDTO giftRecordDTO) {
        return giftConfigService.insertOneGiftRecord(giftRecordDTO);
    }

    @Override
    public boolean send(GiftConfigDTO giftConfigDTO) {
        return giftConfigService.send(giftConfigDTO);
    }
}
