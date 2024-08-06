package com.wuyufan.client.handle;

import cn.hutool.core.util.IdUtil;
import com.wuyufan.bean.packet.LoginRequestPacket;
import com.wuyufan.bean.packet.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LocalDateTime.now() + "：客户端开始登录");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(IdUtil.getSnowflake().nextId());
        loginRequestPacket.setUserName("flush");
        loginRequestPacket.setPassword("pwd");
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println("客户端登录成功");
        } else {
            System.out.println("客户端登录失败：原因" + msg.getReason());
        }
    }

}
