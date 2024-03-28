package com.carl.live.user.provider.service;

import com.carl.live.user.interfaces.dto.UserLoginDTO;
import com.carl.live.user.interfaces.dto.UserPhoneDTO;

import java.util.List;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-28 20:41
 * @version: 1.0
 */
public interface IUserPhoneService {
    /**
     * 手机号登录或注册
     * @param phone
     * @return
     */
    UserLoginDTO login(String phone);
    /**
     * 根据用户 id 查询手机信息列表
     *
     * @param userId
     * @return
     */
    List<UserPhoneDTO> queryByUserId(Long userId);
    /**
     * 根据手机号查询用户信息
     *
     * @param phone
     * @return
     */
    UserPhoneDTO queryByPhone(String phone);
}
