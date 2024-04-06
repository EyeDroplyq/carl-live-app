package com.carl.live.gift.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carl.live.app.common.Constants.ComConstants;
import com.carl.live.app.common.enums.StatusEnum;
import com.carl.live.app.common.interfaces.ConvertBeanUtils;
import com.carl.live.gift.interfaces.dto.GiftConfigDTO;
import com.carl.live.gift.interfaces.dto.GiftRecordDTO;
import com.carl.live.gift.provider.dao.mapper.GiftConfigMapper;
import com.carl.live.gift.provider.dao.mapper.GiftRecordMapper;
import com.carl.live.gift.provider.dao.po.GiftConfigPO;
import com.carl.live.gift.provider.dao.po.GitRecordPO;
import com.carl.live.gift.provider.service.GiftConfigService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 15:19
 * @version: 1.0
 */
@Service
public class GiftConfigServiceImpl implements GiftConfigService {
    @Resource
    private GiftConfigMapper giftConfigMapper;

    @Resource
    private GiftRecordMapper giftRecordMapper;

    @Resource
    private RedisTemplate<String, GiftConfigDTO> redisTemplate;

    /**
     * 查询礼物配置详情
     *
     * @param giftId
     * @return
     */
    @Override
    public GiftConfigDTO getByGiftId(int giftId) {
        QueryWrapper<GiftConfigPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gift_id", giftId);
        queryWrapper.eq("status", StatusEnum.VALID_STATUS.getStatus());
        queryWrapper.last("limit 1");
        return ConvertBeanUtils.convert(giftConfigMapper.selectOne(queryWrapper), GiftConfigDTO.class);
    }

    /**
     * 查询礼物配置列表
     *
     * @return
     */
    @Override
    public List<GiftConfigDTO> queryGiftConfigList() {
        QueryWrapper<GiftConfigPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", StatusEnum.VALID_STATUS.getStatus());
        return ConvertBeanUtils.convertList(giftConfigMapper.selectList(queryWrapper), GiftConfigDTO.class);
    }

    /**
     * 插入一个礼物篇配置
     *
     * @param giftConfigDTO
     * @return
     */
    @Override
    public boolean insertOneGiftConfig(GiftConfigDTO giftConfigDTO) {
        GiftConfigPO giftConfigPO = ConvertBeanUtils.convert(giftConfigDTO, GiftConfigPO.class);
        return giftConfigMapper.insert(giftConfigPO) == ComConstants.ONE_INT;
    }

    /**
     * 更新一个礼物配置
     *
     * @param giftConfigDTO
     * @return
     */
    @Override
    public boolean updateOneGiftConfig(GiftConfigDTO giftConfigDTO) {
        GiftConfigPO giftConfigPO = ConvertBeanUtils.convert(giftConfigDTO, GiftConfigPO.class);
        return giftConfigMapper.updateById(giftConfigPO) == ComConstants.ONE_INT;
    }

    /**
     * 插入一条礼物流水记录
     *
     * @param giftRecordDTO
     * @return
     */
    @Override
    public boolean insertOneGiftRecord(GiftRecordDTO giftRecordDTO) {
        GitRecordPO gitRecordPO = ConvertBeanUtils.convert(giftRecordDTO, GitRecordPO.class);
        return giftRecordMapper.insert(gitRecordPO) == ComConstants.ONE_INT;
    }
}
