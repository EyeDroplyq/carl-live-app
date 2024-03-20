package com.carl.live.id.generate.provider.rpc;

import com.carl.live.id.generate.interfaces.IdGenerate;
import com.carl.live.id.generate.provider.bo.LocalSeqIdBO;
import com.carl.live.id.generate.provider.dao.IdBuilderMapper;
import com.carl.live.id.generate.provider.po.IdBuilderPO;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-03-19 20:54
 * @version: 1.0
 */
@DubboService
public class IdGenerateRpcImp implements IdGenerate, InitializingBean {
    @Resource
    private IdBuilderMapper builderMapper;

    private Map<Integer, LocalSeqIdBO> map = new ConcurrentHashMap<>();

    private Map<Integer, Semaphore> semaphoreMap = new ConcurrentHashMap<>();

    // 异步重新获取id段的使用阈值，最好做成可以配置 todo
    private static final float ID_THRESHOLD = 0.8F;

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000, 1500, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("id-generate:" + ThreadLocalRandom.current().nextInt(100));
            return thread;
        }
    });

    /**
     * 获取有序id
     * 1.从缓存中获取id值
     * 2.如果使用的id值达到了id段的一个阈值，则 异步 更新并且获取新的id段
     * 3、id值+1，并且判断是不是使用完了，使用完了直接return null fail fast机制
     *
     * @param id
     * @return
     */
    @Override
    public Long getSeqId(Integer id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id为空");
        }
        LocalSeqIdBO localSeqIdBO = map.get(id);
        asyncGetNewId(localSeqIdBO);
        if (localSeqIdBO.getCurrentValue().get() > localSeqIdBO.getNextThreshold()) {
            return null;
        }
        return localSeqIdBO.getCurrentValue().getAndIncrement();
    }

    /**
     * 异步重新获取id段
     *
     * @param localSeqIdBO
     */
    private void asyncGetNewId(LocalSeqIdBO localSeqIdBO) {
        IdBuilderPO idBuilderPO = builderMapper.selectById(localSeqIdBO.getId());
        long distance = idBuilderPO.getNextThreshold() - idBuilderPO.getCurrentStart();
        long usedDistance = map.get(localSeqIdBO.getId()).getCurrentValue().get() - idBuilderPO.getCurrentStart();
        if (usedDistance >= distance * ID_THRESHOLD) {
            //使用信号量，控制只有一个线程能去取新的id段
            Semaphore semaphore = semaphoreMap.get(idBuilderPO.getId());
            if (Objects.isNull(semaphore)) {
                throw new RuntimeException("semaphore is null");
            }
            boolean tryRes = semaphore.tryAcquire();
            if (tryRes) {
                CompletableFuture.runAsync(() -> updateAndInitLocalCache(idBuilderPO), threadPoolExecutor).join();
                semaphore.release();
            }
        }
    }

    /**
     * 获取无序id
     *
     * @param id
     * @return
     */
    @Override
    public Long getUnseqId(Integer id) {
        return null;
    }

    /**
     * 初始化 Map<Integer, LocalSeqIdBO> map
     * 1.当前机器获取它可以分配的id段
     * 2、更新id段的参数，给别的机器用，防止多个机器抢到了同一个id段，这里使用基于version的乐观锁
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<IdBuilderPO> idBuilderPOS = builderMapper.selectAll();
        for (IdBuilderPO idBuilderPO : idBuilderPOS) {
            updateAndInitLocalCache(idBuilderPO);
        }
    }

    /**
     * 更新以及初始化本地缓存,必须更新成功之后才会进行本地缓存的初始化
     *
     * @param idBuilderPO
     */
    private void updateAndInitLocalCache(IdBuilderPO idBuilderPO) {
        int index = builderMapper.updateGenerateConfig(idBuilderPO.getId(), idBuilderPO.getVersion());
        if (index == 1) {
            // 更新成功，可以进行初始化
            LocalSeqIdBO localSeqIdBO = new LocalSeqIdBO();
            localSeqIdBO.setId(idBuilderPO.getId());
            AtomicLong atomicLong = new AtomicLong(idBuilderPO.getCurrentStart());
            localSeqIdBO.setCurrentValue(atomicLong);
            localSeqIdBO.setCurrentStart(idBuilderPO.getCurrentStart());
            localSeqIdBO.setNextThreshold(idBuilderPO.getNextThreshold());
            map.put(idBuilderPO.getId(), localSeqIdBO);
            semaphoreMap.put(idBuilderPO.getId(),new Semaphore(1));
            return;
        } else {
            //更新失败，重试更新，重试时间递增,重试3次，最好做成配置 todo
            for (int i = 1; i <= 3; i++) {
                try {
                    Thread.sleep(100 * i);
                    int tryIndex = builderMapper.updateGenerateConfig(idBuilderPO.getId(), idBuilderPO.getVersion());
                    if (tryIndex == 1) {
                        // 更新成功，可以进行初始化
                        LocalSeqIdBO localSeqIdBO = new LocalSeqIdBO();
                        localSeqIdBO.setId(idBuilderPO.getId());
                        AtomicLong atomicLong = new AtomicLong(idBuilderPO.getCurrentStart());
                        localSeqIdBO.setCurrentValue(atomicLong);
                        localSeqIdBO.setCurrentStart(idBuilderPO.getCurrentStart());
                        localSeqIdBO.setNextThreshold(idBuilderPO.getNextThreshold());
                        map.put(idBuilderPO.getId(), localSeqIdBO);
                        semaphoreMap.put(idBuilderPO.getId(),new Semaphore(1));
                        return;
                    }
                } catch (Exception e) {
                    throw new RuntimeException("重试失败");
                }
            }
        }
    }
}
