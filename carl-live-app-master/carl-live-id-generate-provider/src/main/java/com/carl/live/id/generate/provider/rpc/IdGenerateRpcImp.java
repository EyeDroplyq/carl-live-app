package com.carl.live.id.generate.provider.rpc;

import com.carl.live.app.common.Constants.ComConstants;
import com.carl.live.id.generate.interfaces.IdGenerate;
import com.carl.live.id.generate.provider.bo.LocalSeqIdBO;
import com.carl.live.id.generate.provider.bo.LocalUnSeqIdBO;
import com.carl.live.id.generate.provider.dao.IdBuilderMapper;
import com.carl.live.id.generate.provider.po.IdBuilderPO;
import com.carl.live.id.generate.provider.service.IdGenerateService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.InitializingBean;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

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
