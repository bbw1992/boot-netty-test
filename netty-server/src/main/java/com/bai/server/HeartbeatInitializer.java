package com.bai.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class HeartbeatInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                .addLast(new IdleStateHandler(10, 0, 0))
//                .addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4))
                //换行分割
//                .addLast(new LineBasedFrameDecoder(1024))
//                .addLast(new StringDecoder(CharsetUtil.UTF_8))
                .addLast(new StringEncoder(CharsetUtil.UTF_8))
//                .addLast(new ByteArrayToBinaryEncoder())
                .addLast(new HeartBeatSimpleHandle());
    }

}
