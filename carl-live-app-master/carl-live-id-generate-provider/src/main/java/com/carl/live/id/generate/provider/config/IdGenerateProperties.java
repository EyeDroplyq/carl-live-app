package com.carl.live.id.generate.provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-28 21:19
 * @version: 1.0
 */
@ConfigurationProperties("carl.live.id.generate")
@Component
@RefreshScope
@Data
public class IdGenerateProperties {
    /**
     * 提前预热的阈值
     */
    private float idThreshold;

    /**
     * 重试次数
     */
    private int reTryConut;

    /**
     * 有序id生成器中递增的步长
     */
    private Long increaseNum;
}
