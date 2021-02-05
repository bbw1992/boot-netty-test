package com.bai.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class CustomerHandleInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                .addLast(new IdleStateHandler(0, 15, 0))
                .addLast("encoder", new StringEncoder())
                .addLast("decoder", new StringDecoder())
                .addLast(new ClientHandle());
    }
}
