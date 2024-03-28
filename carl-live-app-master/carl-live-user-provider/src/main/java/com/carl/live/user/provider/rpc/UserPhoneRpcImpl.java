package com.carl.live.user.provider.rpc;

import com.carl.live.user.interfaces.IUserPhoneRpc;
import com.carl.live.user.interfaces.dto.UserLoginDTO;
import com.carl.live.user.interfaces.dto.UserPhoneDTO;
import com.carl.live.user.provider.service.IUserPhoneService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-28 20:42
 * @version: 1.0
 */
@DubboService
public class UserPhoneRpcImpl implements IUserPhoneRpc {
    @Resource
    private IUserPhoneService userPhoneService;
    @Override
    public UserLoginDTO login(String phone) {
        return userPhoneService.login(phone);
    }

    @Override
    public List<UserPhoneDTO> queryByUserId(Long userId) {
        return userPhoneService.queryByUserId(userId);
    }

    @Override
    public UserPhoneDTO queryByPhone(String phone) {
        return userPhoneService.queryByPhone(phone);
    }
}
