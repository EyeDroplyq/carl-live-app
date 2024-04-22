package com.carl.live.gift.provider.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carl.live.app.common.constants.CacheConstants;
import com.carl.live.app.common.enums.StatusEnum;
import com.carl.live.gift.interfaces.dto.RedPacketReceiveDTO;
import com.carl.live.gift.provider.dao.mapper.IRedPacketConfigMapper;
import com.carl.live.gift.provider.dao.po.RedPacketConfigPO;
import com.carl.live.gift.provider.service.IRedPacketConfigService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-21 17:17
 * @version: 1.0
 */
@Service
@Slf4j
public class RedPacketConfigServiceImpl implements IRedPacketConfigService {
    @Resource
    private IRedPacketConfigMapper redPacketConfigMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 通过主播id查询红包雨配置
     *
     * @param anchorId
     * @return
     */
    @Override
    public RedPacketConfigPO queryByAnchorId(Integer anchorId) {
        QueryWrapper<RedPacketConfigPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("anchor_id", anchorId);
        queryWrapper.eq("status", StatusEnum.VALID_STATUS.getStatus());
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 1");
        return redPacketConfigMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean addOne(RedPacketConfigPO redPacketConfigPO) {
        String configCode = IdUtil.simpleUUID();
        redPacketConfigPO.setConfigCode(configCode);
        return redPacketConfigMapper.insert(redPacketConfigPO) == 1;
    }

    @Override
    public boolean updateById(RedPacketConfigPO redPacketConfigPO) {
        return redPacketConfigMapper.updateById(redPacketConfigPO) == 1;
    }

    /**
     * 生成红包雨
     *
     * @param anchorId
     * @return
     */
    @Override
    public boolean prepareRedPacket(Integer anchorId) {
        RedPacketConfigPO redPacketConfigPO = queryByAnchorId(anchorId);
        if (ObjectUtils.isEmpty(redPacketConfigPO)) {
            return false;
        }
        String configCode = redPacketConfigPO.getConfigCode();
        //接口幂等性
        String key = CacheConstants.REDPACKET_KEY + configCode;
        Boolean isLock = redisTemplate.opsForValue().setIfAbsent(key, "", 3, TimeUnit.SECONDS);
        if (!isLock) {
            return false;
        }
        Integer totalCount = redPacketConfigPO.getTotalCount();
        Integer totalPrice = redPacketConfigPO.getTotalPrice();
        List<Integer> redPacktList = createRedPacketList(totalPrice, totalCount);
        //防止生成大量的红包，所以分批发送给redis 100一批
        List<List<Integer>> listSplit = CollUtil.split(redPacktList, 100);
        for (List<Integer> redList : listSplit) {
            redisTemplate.opsForList().leftPushAll(key, redList);
        }
        redPacketConfigPO.setStatus(StatusEnum.NO_VALID_STATUS.getStatus());
        return updateById(redPacketConfigPO);
    }

    /**
     * 抢红包
     *
     * @param code
     * @return
     */
    @Override
    public RedPacketReceiveDTO receiveRedPacket(String code) {
        String key = CacheConstants.REDPACKET_KEY + code;
        Object price = redisTemplate.opsForList().rightPop(key);
        if (price == null) {
            return null;
        }
        redisTemplate.opsForValue().increment(CacheConstants.REDPACKET_TOTAL_GET_KEY + code);
        redisTemplate.expire(CacheConstants.REDPACKET_TOTAL_GET_KEY + code, 1, TimeUnit.DAYS);
        redisTemplate.opsForValue().increment(CacheConstants.REDPACKET_TOTAL_GET_PRICE_KEY + code, (int) price);
        redisTemplate.expire(CacheConstants.REDPACKET_TOTAL_GET_PRICE_KEY + code, 1, TimeUnit.DAYS);
        //todo 设置红包雨活动中抢到的最大金额

        return new RedPacketReceiveDTO((Integer) price);
    }

    /**
     * 使用二倍均值算法生成红包列表
     *
     * @param totalPrice
     * @param totalCount
     * @return
     */
    private List<Integer> createRedPacketList(Integer totalPrice, Integer totalCount) {
        List<Integer> res = new ArrayList<>();
        Integer useMoney = 0;
        for (int i = 0; i < totalPrice; i++) {
            if (i == totalCount - 1) {
                res.add(totalPrice - useMoney);
                break;
            } else {
                int avgMoney = ((totalPrice - useMoney) / (totalCount - i)) * 2;
                int curMoney = 1 + new Random().nextInt(avgMoney - 1);
                res.add(curMoney);
                useMoney += curMoney;
            }
        }
        return res;
    }
}
