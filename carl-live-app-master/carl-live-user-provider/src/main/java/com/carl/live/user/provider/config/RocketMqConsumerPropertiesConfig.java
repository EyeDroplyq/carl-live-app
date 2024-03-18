package com.carl.live.user.provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-18 21:07
 * @version: 1.0
 */
@Configuration
@ConfigurationProperties("rocketmq.consumer")
@Data
public class RocketMqConsumerPropertiesConfig {
    /**
     * 连接地址
     */
    private String nameSrv;

    /**
     * group名称
     */
    private String groupName;
}
