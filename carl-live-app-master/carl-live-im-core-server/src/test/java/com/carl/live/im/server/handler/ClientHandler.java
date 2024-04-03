package com.carl.live.im.server.handler;

import com.alibaba.fastjson.JSON;
import com.carl.im.interfaces.dto.ImMsgBody;
import com.carl.im.interfaces.enums.ImAppIdEnums;
import com.carl.im.interfaces.enums.ImMsgCodeEnums;
import com.carl.im.interfaces.rpc.ImServerRpc;
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
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: 小琦
 * @createDate: 2024-04-03 21:06
 * @version: 1.0
 */
@Service
@Slf4j
public class ClientHandler implements InitializingBean {
    @Resource
    private ImServerRpc imServerRpc;

    @Override
    public void afterPropertiesSet() throws Exception {
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
                ch.pipeline().addLast(new com.carl.live.im.server.ClientHandler());
            }
        });
        ChannelFuture channelFuture = bootstrap.connect("localhost", 8085).sync();
        Channel channel = channelFuture.channel();
        Long userId = 123456L;
        for (int i = 0; i < 100; i++) {
            String token = imServerRpc.createImLoginToken(userId, ImAppIdEnums.CARL_LIVE_APPID.getAppId());
            ImMsgBody imMsgBody = new ImMsgBody();
            imMsgBody.setUserId(userId);
            imMsgBody.setAppId(ImAppIdEnums.CARL_LIVE_APPID.getAppId());
            imMsgBody.setToken(token);
            ImMsg imMsg = ImMsg.makeImMsg(ImMsgCodeEnums.LOGIN_IN_MSG.getCode(), JSON.toJSONString(imMsgBody).getBytes(StandardCharsets.UTF_8));
            channel.writeAndFlush(imMsg);
            log.info("发送的消息为: {}", JSON.toJSONString(imMsg));
            Thread.sleep(3000);
        }
    }
}
