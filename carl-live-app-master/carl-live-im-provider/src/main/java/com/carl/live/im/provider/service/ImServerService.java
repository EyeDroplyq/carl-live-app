package com.carl.live.im.provider.service;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-02 20:56
 * @version: 1.0
 */
public interface ImServerService {
    /**
     * 创建Im服务的登录token
     *
     * @param userId
     * @param appId
     * @return
     */
    String createImLoginToken(long userId, int appId);

    /**
     * 通过token获得用户的id
     *
     * @param token
     * @return
     */
    Long getUserIdByToken(String token);
}
