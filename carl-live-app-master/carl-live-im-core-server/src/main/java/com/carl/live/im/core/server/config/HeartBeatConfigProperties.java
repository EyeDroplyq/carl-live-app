package com.carl.live.im.core.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 17:54
 * @version: 1.0
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties("im.core.heart.beat")
public class HeartBeatConfigProperties {
    /**
     * 需要清除的过期的心跳 过期时间
     */
    private int expiredHeartBeat;
}
