package com.bai.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelUtil {

    final static ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

    public static String getIp(ChannelHandlerContext ctx){
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        return insocket.getAddress().getHostAddress();
    }

    public static int getPort(ChannelHandlerContext ctx){
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        return insocket.getPort();
    }

}
