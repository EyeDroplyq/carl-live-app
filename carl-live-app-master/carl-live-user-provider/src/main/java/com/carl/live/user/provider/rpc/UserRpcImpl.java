package com.carl.live.user.provider.rpc;

import com.carl.live.user.interfaces.IUserRpc;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-14 21:38
 * @version: 1.0
 */
@DubboService
public class UserRpcImpl implements IUserRpc {
    @Override
    public void test() {
        System.out.println("this is dubbo");
    }
}
