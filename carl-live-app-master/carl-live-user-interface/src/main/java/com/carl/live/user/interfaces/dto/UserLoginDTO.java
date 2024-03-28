package com.carl.live.user.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-28 20:39
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4594111084197405521L;


    private boolean isLoginSuccess;

    private String desc;

    private Long userId;

    public static UserLoginDTO loginSuccess(Long userId){
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUserId(userId);
        userLoginDTO.setLoginSuccess(true);
        userLoginDTO.setDesc("登录成功");
        return userLoginDTO;
    }
}
