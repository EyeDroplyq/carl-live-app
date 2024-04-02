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
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                System.out.println("client init");
                ch.pipeline().addLast(new ImMsgEncoder());
                ch.pipeline().addLast(new ImMsgDecoder());
                ch.pipeline().addLast(new ClientHandler());
            }
        });
        ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
        Channel channel = channelFuture.channel();
        for (int i = 0; i < 100; i++) {
            channel.writeAndFlush(ImMsg.makeImMsg(ImMsgCodeEnums.LOGIN_IN_MSG.getCode(), "login test".getBytes(StandardCharsets.UTF_8)));
            channel.writeAndFlush(ImMsg.makeImMsg(ImMsgCodeEnums.LOGIN_OUT_MSG.getCode(), "login out test".getBytes(StandardCharsets.UTF_8)));
            channel.writeAndFlush(ImMsg.makeImMsg(ImMsgCodeEnums.BIZ_MSG.getCode(), "biz test".getBytes(StandardCharsets.UTF_8)));
            channel.writeAndFlush(ImMsg.makeImMsg(ImMsgCodeEnums.HEALTH_MSG.getCode(), "health test".getBytes(StandardCharsets.UTF_8)));
            Thread.sleep(3000);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ClientApplication clientApplication = new ClientApplication();
        clientApplication.start("localhost", 9090);
    }
}
