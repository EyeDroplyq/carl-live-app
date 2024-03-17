package com.carl.live.api.controller;

import com.carl.live.user.interfaces.IUserRpc;
import com.carl.live.user.interfaces.dto.UserDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-14 21:39
 * @version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @DubboReference
    private IUserRpc userRpc;

    @GetMapping("/getById")
    public UserDTO getUserById(@RequestParam("userId") Long userId) {
        return userRpc.getUserById(userId);
    }

    @GetMapping("/updateUserInfo")
    public boolean updateUserInfo(Long userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setNickName("idea-update");
        return userRpc.updateUser(userDTO);
    }

    @GetMapping("/insertUserInfo")
    public boolean insertUserInfo(Long userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setNickName("idea-insert");
        return userRpc.insertUser(userDTO);
    }

    @GetMapping("/batchQueryUser")
    public Map<Long, UserDTO> batchQueryUser(String userId) {
        List<Long> userIdList = Arrays.stream(userId.split(",")).map(Long::valueOf).toList();
        return userRpc.batchQueryUserInfoByList(userIdList);
    }

}
