package com.carl.live.im.router.provider.cluster;

import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-06 21:34
 * @version: 1.0
 */
public class ImRouterClusterInvoker<T> extends AbstractClusterInvoker<T> {
    public ImRouterClusterInvoker(Directory<T> directory) {
        super(directory);
    }

    @Override
    protected Result doInvoke(Invocation invocation, List<Invoker<T>> invokers, LoadBalance loadbalance) throws RpcException {
        // 调用父类方法进行参数校验
        checkWhetherDestroyed();
        // 获取接收对象的ip地址信息
        String ip = (String) RpcContext.getContext().get("ip");
        if (!StringUtils.hasText(ip)) {
            throw new IllegalArgumentException("reciver ip is null");
        }
        // 从注册中心中得到所有的ip地址信息
        List<Invoker<T>> invokerList = list(invocation);
        Invoker<T> invoker = invokerList.stream()
                .filter(item -> {
                    String ipAddr = item.getUrl().getHost() + ":" + item.getUrl().getPort();
                    return ip.equals(ipAddr);
                }).findFirst().orElse(null);
        if (ObjectUtils.isEmpty(invoker)) {
            throw new RuntimeException("result ip is null");
        }
        return invoker.invoke(invocation);
    }
}
