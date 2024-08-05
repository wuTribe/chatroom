package com.wuyufan.handle.client;

import cn.hutool.core.util.IdUtil;
import com.wuyufan.bean.packet.LoginRequestPacket;
import com.wuyufan.bean.packet.LoginResponsePacket;
import com.wuyufan.bean.packet.MessageResponsePacket;
import com.wuyufan.bean.packet.Packet;
import com.wuyufan.handle.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LocalDateTime.now() + "：客户端开始登录");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(IdUtil.getSnowflake().nextId());
        loginRequestPacket.setUserName("flush");
        loginRequestPacket.setPassword("pwd");
        ByteBuf buffer = ctx.alloc().ioBuffer();
        PacketCodeC.INSTANCE.encode(buffer, loginRequestPacket);
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(buf);
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if (loginResponsePacket.isSuccess()) {
                System.out.println("客户端登录成功");
            } else {
                System.out.println("客户端登录失败：原因" + loginResponsePacket.getReason());
            }
            ByteBuf buffer = ctx.alloc().buffer();
            PacketCodeC.INSTANCE.encode(buffer, packet);
            ctx.writeAndFlush(buffer);
        }

        if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(LocalDateTime.now() + "：收到服务端的消息：" + messageResponsePacket.getMessage());
        }

    }
}
