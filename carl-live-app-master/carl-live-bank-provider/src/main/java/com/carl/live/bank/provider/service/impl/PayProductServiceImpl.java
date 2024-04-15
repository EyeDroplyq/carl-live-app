package com.carl.live.bank.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carl.live.app.common.constants.CacheConstants;
import com.carl.live.app.common.enums.StatusEnum;
import com.carl.live.app.common.interfaces.ConvertBeanUtils;
import com.carl.live.bank.interfaces.dto.PayProductDTO;
import com.carl.live.bank.provider.dao.mapper.IPayProductMapper;
import com.carl.live.bank.provider.dao.po.PayProductPO;
import com.carl.live.bank.provider.service.IPayProductService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-14 21:57
 * @version: 1.0
 */
@Service
@Slf4j
public class PayProductServiceImpl implements IPayProductService {
    @Resource
    private IPayProductMapper payProductMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 通过产品类型查询平台所有的支付产品列表
     * 使用缓存来提高查询性能
     *
     * @param type
     * @return
     */
    @Override
    public List<PayProductDTO> products(int type) {
        String payProductKey = CacheConstants.PAY_PRODUCT_KEY + type;
        List<PayProductDTO> productDTOs = redisTemplate.opsForList().range(payProductKey, 0, 30).stream().map(item -> (PayProductDTO) item).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(productDTOs)) {
            // 防止缓存击穿缓存的空值的情况
            if (productDTOs.get(0).getId() == null) {
                return Collections.EMPTY_LIST;
            }
            return productDTOs;
        }
        QueryWrapper<PayProductPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", 0);
        queryWrapper.eq("valid_status", StatusEnum.VALID_STATUS.getStatus());
        List<PayProductPO> payProductPOS = payProductMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(payProductPOS)) {
            // 如果db中也没有的话，为了防止缓存击穿，缓存进去空值
            redisTemplate.opsForList().leftPushAll(payProductKey, Collections.EMPTY_LIST);
            redisTemplate.expire(payProductKey, 5, TimeUnit.MINUTES);
            return Collections.EMPTY_LIST;
        }
        List<PayProductDTO> payProductDTOS = ConvertBeanUtils.convertList(payProductPOS, PayProductDTO.class);
        redisTemplate.opsForList().leftPushAll(payProductKey, payProductDTOS);
        redisTemplate.expire(payProductKey, 30, TimeUnit.MINUTES);
        return payProductDTOS;
    }

    /**
     * 通过产品id查询支付产品
     *
     * @param productId
     * @return
     */
    @Override
    public PayProductDTO getByProductId(Integer productId) {
        String productKey = CacheConstants.PAY_PRODUCT_KEY + productId;
        PayProductDTO product = (PayProductDTO) redisTemplate.opsForValue().get(productKey);
        if (product != null) {
            if (ObjectUtils.isEmpty(product.getId())) {
                return null;
            }
            return product;
        }
        PayProductPO payProductPO = payProductMapper.selectById(productId);
        if (payProductPO != null) {
            PayProductDTO payProductDTO = ConvertBeanUtils.convert(payProductPO, PayProductDTO.class);
            redisTemplate.opsForValue().set(productKey, payProductDTO, 30, TimeUnit.MINUTES);
            return payProductDTO;
        }
        redisTemplate.opsForValue().set(productKey, new PayProductDTO(), 5, TimeUnit.MINUTES);
        return null;
    }
}
