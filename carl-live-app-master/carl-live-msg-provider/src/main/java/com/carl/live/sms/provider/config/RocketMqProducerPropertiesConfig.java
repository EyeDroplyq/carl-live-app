package com.carl.live.sms.provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-18 20:57
 * @version: 1.0
 */
@Configuration
@ConfigurationProperties("rocketmq.producer")
@Data
public class RocketMqProducerPropertiesConfig {
    /**
     * 连接地址
     */
    private String nameSrv;

    /**
     * group名称
     */
    private String groupName;

    /**
     * 发送失败后重试次数
     */
    private Integer retryCount;

    /**
     * 超时时间
     */
    private Integer sendTimeOut;
}
