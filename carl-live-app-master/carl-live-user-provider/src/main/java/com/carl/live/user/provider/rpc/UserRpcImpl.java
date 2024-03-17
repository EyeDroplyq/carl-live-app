package com.carl.live.user.provider.rpc;

import com.carl.live.user.interfaces.IUserRpc;
import com.carl.live.user.interfaces.dto.UserDTO;
import com.carl.live.user.provider.service.IUserService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-14 21:38
 * @version: 1.0
 */
@DubboService
public class UserRpcImpl implements IUserRpc {
    @Resource
    private IUserService userService;
    @Override
    public UserDTO getUserById(Long userId) {
        return userService.getUserById(userId);
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @Override
    public boolean insertUser(UserDTO userDTO) {
        return userService.insertUser(userDTO);
    }

    @Override
    public Map<Long, UserDTO> batchQueryUserInfoByList(List<Long> userIdList) {
        return userService.batchQueryUserInfoByList(userIdList);
    }
}
