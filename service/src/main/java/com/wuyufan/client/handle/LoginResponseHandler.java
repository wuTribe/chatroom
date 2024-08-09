package com.wuyufan.client.handle;

import com.wuyufan.bean.Attributes;
import com.wuyufan.bean.Session;
import com.wuyufan.bean.packet.response.LoginResponsePacket;
import com.wuyufan.client.NettyClient;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println("客户端登录成功 " + msg.getUserId() + ":" + msg.getUserName());
            //channel设置登录标记
            ctx.channel().attr(Attributes.SESSION).set(new Session(msg.getUserId(), msg.getUserName()));
        } else {
            System.out.println("客户端登录失败：原因" + msg.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接关闭....");
    }
}
