package com.wuyufan.utils;

import com.wuyufan.bean.Attributes;
import io.netty.channel.Channel;

public class LoginUtil {
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.LOGIN).get() != null;
    }
}
