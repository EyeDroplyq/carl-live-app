package com.carl.live.user.provider.service;

import com.carl.live.user.interfaces.dto.UserDTO;
import com.carl.live.user.provider.dao.po.UserPO;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-17 13:21
 * @version: 1.0
 */
public interface IUserService {
    /**
     * 根据userId查询用户
     * @param userId
     * @return
     */
    UserDTO getUserById(Long userId);

    /**
     * 更新用户信息
     * @param userDTO
     * @return
     */
    boolean updateUser(UserDTO userDTO);

    /**
     * 插入用户信息
     * @param userDTO
     * @return
     */
    boolean insertUser(UserDTO userDTO);

    /**
     * 批量查询
     * @param userIdList
     * @return
     */
    Map<Long,UserDTO> batchQueryUserInfoByList(List<Long> userIdList);
}
