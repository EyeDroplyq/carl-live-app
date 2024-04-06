package com.carl.live.im.router.provider.cluster;

import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Cluster;
import org.apache.dubbo.rpc.cluster.Directory;

/**
 * @description: 基于dubbo的spi去进行扩展，实现根据rpc上下文来选择具体的机器
 * @author: 小琦
 * @createDate: 2024-04-06 21:33
 * @version: 1.0
 */
public class ImRouterCluster implements Cluster {
    @Override
    public <T> Invoker<T> join(Directory<T> directory, boolean buildFilterChain) throws RpcException {
        return new ImRouterClusterInvoker<>(directory);
    }
}
