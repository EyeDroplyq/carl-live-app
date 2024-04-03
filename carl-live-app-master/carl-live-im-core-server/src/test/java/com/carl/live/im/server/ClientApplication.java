package com.carl.live.im.server;

import com.carl.im.interfaces.enums.ImMsgCodeEnums;
import com.carl.live.im.core.server.common.ImMsg;
import com.carl.live.im.core.server.common.ImMsgDecoder;
import com.carl.live.im.core.server.common.ImMsgEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-01 21:22
 * @version: 1.0
 */
public class ClientApplication {
    private void start(String host, Integer port) throws InterruptedException {

    }

    public static void main(String[] args) throws InterruptedException {
        ClientApplication clientApplication = new ClientApplication();
        clientApplication.start("localhost", 9090);
    }
}
