package com.carl.live.gift.provider.rpc;

import com.carl.live.app.common.interfaces.ConvertBeanUtils;
import com.carl.live.gift.interfaces.dto.RedPacketConfigDTO;
import com.carl.live.gift.interfaces.dto.RedPacketReceiveDTO;
import com.carl.live.gift.interfaces.rpc.IRedPacketConfigRpc;
import com.carl.live.gift.provider.dao.po.RedPacketConfigPO;
import com.carl.live.gift.provider.service.IRedPacketConfigService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-21 20:30
 * @version: 1.0
 */
@DubboService
public class RedPacketConfigRpcImpl implements IRedPacketConfigRpc {
    @Resource
    private IRedPacketConfigService redPacketConfigService;

    @Override
    public RedPacketConfigDTO queryByAnchorId(Integer anchorId) {
        return ConvertBeanUtils.convert(redPacketConfigService.queryByAnchorId(anchorId), RedPacketConfigDTO.class);
    }

    @Override
    public boolean addOne(RedPacketConfigDTO redPacketConfigDTO) {
        return redPacketConfigService.addOne(ConvertBeanUtils.convert(redPacketConfigDTO, RedPacketConfigPO.class));
    }

    @Override
    public boolean updateById(RedPacketConfigDTO redPacketConfigDTO) {
        return redPacketConfigService.updateById(ConvertBeanUtils.convert(redPacketConfigDTO, RedPacketConfigPO.class));
    }

    /**
     * 抢红包
     * @param code
     * @return
     */
    @Override
    public RedPacketReceiveDTO receiveRedPacket(String code) {
        return redPacketConfigService.receiveRedPacket(code);
    }
}
