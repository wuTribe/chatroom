package com.wuyufan.utils;

import com.wuyufan.bean.Attributes;
import com.wuyufan.bean.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    // userId  ->  channel  的映射
    private static final Map<Long, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    // group id -> channel group 的映射
    private static final Map<Integer, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.SESSION).get() != null;
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(long userId) {
        return userIdChannelMap.get(userId);
    }

    public static void addGroup(Integer groupId, ChannelGroup channelGroup) {
        channelGroupMap.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(int groupId) {
        return channelGroupMap.get(groupId);
    }


    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }
}
