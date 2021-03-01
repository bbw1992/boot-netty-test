package com.bai.server;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelUtil {

    final static ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<>();

}
