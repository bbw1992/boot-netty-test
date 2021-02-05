package com.bai.client;


import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ClientHandle extends ChannelInboundHandlerAdapter {

    @Autowired
    NettyClient nettyClient;


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //重连
        log.info("重新连接");
        EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> {
            try {
                nettyClient.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("msg = {}", msg);
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                log.info("已经15秒没有发送消息了");
                ctx.writeAndFlush("Hello, I'm still alive. \n ").addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }

        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.info("channelWritabilityChanged");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }


    //    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
//            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
//                log.info("已经15秒没有发送消息了");
//                ctx.writeAndFlush("Hello, I'm still alive. \n ").addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
////                ctx.writeAndFlush("Hello, I'm still alive. \\r\\n ");
//            }
//
//        }
//        super.userEventTriggered(ctx, evt);
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        //重连
//        log.info("重新连接");
//        EventLoop eventLoop = ctx.channel().eventLoop();
//        eventLoop.schedule(() -> {
//            try {
//                nettyClient.run();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }, 1L, TimeUnit.SECONDS);
//        super.channelInactive(ctx);
//    }
//
//
//
//    @Override
//    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
//        log.info("channelRegistered");
//        super.channelRegistered(ctx);
//    }
//
//    @Override
//    public void channelUnregistered(ChannelHandlerContext ctx)  {
//        log.info("channelUnregistered");
//        try {
//            super.channelUnregistered(ctx);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        log.info("channelActive");
//        super.channelActive(ctx);
//    }
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        log.info("channelReadComplete");
//        super.channelReadComplete(ctx);
//    }
//
//    @Override
//    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        log.info("handlerAdded");
//        super.handlerAdded(ctx);
//    }
//
//    @Override
//    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        log.info("handlerRemoved");
//        super.handlerRemoved(ctx);
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        log.info("exceptionCaught");
//        super.exceptionCaught(ctx, cause);
//    }
//
//    @Override
//    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
//        log.info("channelWritabilityChanged");
//        super.channelWritabilityChanged(ctx);
//    }
}
