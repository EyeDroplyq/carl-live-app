package com.carl.live.api.controller;

import com.carl.live.user.interfaces.IUserRpc;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-14 21:39
 * @version: 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @DubboReference
    private IUserRpc userRpc;

    @GetMapping("/dubbo")
    public String test() {
        userRpc.test();
        return "success";
    }
}
