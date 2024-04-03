package com.carl.live.im.core.server.config;

import com.carl.live.im.core.server.common.ImMsgDecoder;
import com.carl.live.im.core.server.common.ImMsgEncoder;
import com.carl.live.im.core.server.handler.ImServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-01 22:24
 * @version: 1.0
 */
@Configuration
public class NettyServerStart implements InitializingBean {
    @Value("${im.server.port}")
    private int port;

    @Resource
    private ImServerHandler imServerHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(() -> {
            try {
                this.startApplication(port);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void startApplication(int port) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //添加消息编解码器
                ch.pipeline().addLast(new ImMsgDecoder());
                ch.pipeline().addLast(new ImMsgEncoder());
                // 设置handler
                ch.pipeline().addLast(imServerHandler);
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }));
        ChannelFuture channelFuture = bootstrap.bind(port).sync();
        channelFuture.channel().closeFuture().sync();
    }
}
