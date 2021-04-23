package com.bai.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class HeartBeatSimpleHandle extends SimpleChannelInboundHandler<ByteBuf> {

    AtomicLong count = new AtomicLong(0);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive");
        super.channelRegistered(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("失联了！");
                log.info(ctx.channel().remoteAddress().toString());
                //向客户端发送消息
                ctx.writeAndFlush("Hello, are you still alive?").addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }
        super.userEventTriggered(ctx, evt);
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelRegistered");
        String ip= ChannelUtil.getIp(ctx);
        ChannelUtil.channelMap.put(ip, ctx.channel());
        for (Map.Entry<String, Channel> m : ChannelUtil.channelMap.entrySet()) {
            log.info("key:" + m.getKey());
            log.info("value:" + m.getValue());
            log.info("port:" + ChannelUtil.getPort(ctx));
        }
        log.info("当前用户数 ： {}", ChannelUtil.channelMap.size());
        ChannelFuture future = ChannelUtil.channelMap.get(ip).writeAndFlush("register successed");
        if(future.isDone()){
            log.info(String.format("发送结果 ： %s", future.isSuccess()));
        }
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelUnregistered");
        ChannelUtil.channelMap.remove(ChannelUtil.getIp(ctx));
        log.info("移除后用户数 ： {}", ChannelUtil.channelMap.size());
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerAdded");
        log.info("当前用户id ： {}", ctx.channel().id().asLongText());
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved");
        log.info("移除当前用户id ： {}", ctx.channel().id().asLongText());
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.info("channelWritabilityChanged");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        log.info("ByteBuf={}", byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("msg={}", msg);
        log.info("count : " + count.addAndGet(1));
        super.channelRead(ctx, msg);
    }
}
