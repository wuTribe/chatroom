package com.wuyufan.server.handle;

import com.wuyufan.bean.packet.request.LogoutRequestPacket;
import com.wuyufan.bean.packet.response.LogoutResponsePacket;
import com.wuyufan.utils.SessionUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登出请求
 */
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) {
        SessionUtil.unBindSession(ctx.channel());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        ctx.channel().writeAndFlush(logoutResponsePacket).addListener(ChannelFutureListener.CLOSE);
    }
}
