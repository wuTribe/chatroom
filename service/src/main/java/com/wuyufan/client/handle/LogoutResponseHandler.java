package com.wuyufan.client.handle;

import com.wuyufan.bean.packet.response.LogoutResponsePacket;
import com.wuyufan.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    public static final LogoutResponseHandler INSTANCE = new LogoutResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) {
        SessionUtil.unBindSession(ctx.channel());
        System.out.println("登出成功");
    }
}
