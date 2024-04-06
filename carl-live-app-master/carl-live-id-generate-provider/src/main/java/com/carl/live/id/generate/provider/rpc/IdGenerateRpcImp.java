package com.carl.live.id.generate.provider.rpc;

import com.carl.live.id.generate.interfaces.IdGenerate;
import com.carl.live.id.generate.provider.service.IdGenerateService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-19 20:54
 * @version: 1.0
 */
@DubboService
public class IdGenerateRpcImp implements IdGenerate {

    @Resource
    private IdGenerateService idGenerateService;
    @Override
    public Long getSeqId(Integer id) {
        return idGenerateService.getSeqId(id);
    }

    @Override
    public Long getUnseqId(Integer id) {
        return idGenerateService.getUnseqId(id);
    }
}
