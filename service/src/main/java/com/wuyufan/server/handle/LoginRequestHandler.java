package com.wuyufan.server.handle;

import cn.hutool.core.util.IdUtil;
import com.wuyufan.bean.Session;
import com.wuyufan.bean.packet.request.LoginRequestPacket;
import com.wuyufan.bean.packet.response.LoginResponsePacket;
import com.wuyufan.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        Channel channel = ctx.channel();
        if (valid(msg)) {
            long userId = IdUtil.getSnowflake().nextId();
            String userName = msg.getUserName();
            loginResponsePacket.setUserId(userId);
            loginResponsePacket.setUserName(userName);
            loginResponsePacket.setSuccess(true);
            // 给客户端设置成功标记
            // 原本作者直接在客户端 handle 设置 应该在服务端处理
            SessionUtil.bindSession(new Session(userId, userName), channel);
            System.out.println("登录成功");
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号校验失败");
            System.out.println("登录失败");
        }
        channel.writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket msg) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
