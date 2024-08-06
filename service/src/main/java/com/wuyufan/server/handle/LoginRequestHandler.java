package com.wuyufan.server.handle;

import com.wuyufan.bean.packet.LoginRequestPacket;
import com.wuyufan.bean.packet.LoginResponsePacket;
import com.wuyufan.utils.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (valid(msg)) {
            loginResponsePacket.setSuccess(true);
            // 给客户端设置成功标记
            // 原本作者直接在客户端 handle 设置 应该在服务端处理
            LoginUtil.markAsLogin(ctx.channel());
            System.out.println("登录成功");
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号校验失败");
            System.out.println("登录失败");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket msg) {
        return true;
    }
}
