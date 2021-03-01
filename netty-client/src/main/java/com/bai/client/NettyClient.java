package com.bai.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NettyClient {

    @Value("${netty.server.port}")
    private int port;

    @Value("${netty.server.host}")
    private String host;

    private SocketChannel socketChannel;

    public void run() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new CustomerHandleInitializer()).option(ChannelOption.TCP_NODELAY, true);
        ChannelFuture future = bootstrap.connect(host, port);
        future.sync().addListener((ChannelFutureListener) c -> {
            if (c.isDone()) {
                if (c.isSuccess()) {
                    log.info("client start success...");
                } else if (c.isCancelled()) {
                    log.info("client cancell...");
                } else if (c.cause() != null) {
                    log.error("client start error ：" + c.cause().getMessage());
                }
            }
        });
        socketChannel = (SocketChannel) future.channel();
    }
}
