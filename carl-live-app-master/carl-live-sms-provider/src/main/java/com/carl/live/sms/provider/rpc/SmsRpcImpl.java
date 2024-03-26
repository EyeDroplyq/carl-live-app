package com.carl.live.sms.provider.rpc;

import com.carl.live.sms.provider.service.ISmsRpcService;
import com.carl.sms.interfaces.dto.MsgCheckDTO;
import com.carl.sms.interfaces.enums.MsgSendResultEnum;
import com.carl.sms.interfaces.rpc.SmsRpc;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-25 21:52
 * @version: 1.0
 */
@DubboService
public class SmsRpcImpl implements SmsRpc {
    @Resource
    private ISmsRpcService smsRpcService;
    /**
     * 发送短信
     * @param phone
     * @return
     */
    @Override
    public MsgSendResultEnum sendMessage(String phone) {
        return smsRpcService.sendMessage(phone);
    }

    /**
     * 检查验证码是否正确
     * @param phone
     * @param code
     * @return
     */
    @Override
    public MsgCheckDTO checkLoginCode(String phone, String code) {
        return smsRpcService.checkLoginCode(phone,code);
    }

    /**
     * 保存验证码记录
     * @param phone
     * @param code
     */

    @Override
    public void insertOne(String phone, String code) {
        smsRpcService.insertOne(phone, code);
    }
}
