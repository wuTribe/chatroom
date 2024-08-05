package com.wuyufan.handle.server;

import com.wuyufan.bean.packet.*;
import com.wuyufan.handle.PacketCodeC;
import com.wuyufan.utils.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(buf);
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                // 给客户端设置成功标记
                // 原本作者直接在客户端 handle 设置 应该在服务端处理
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("账号校验失败");
            }

            ByteBuf buffer = ctx.alloc().ioBuffer();
            PacketCodeC.INSTANCE.encode(buffer, loginResponsePacket);
            ctx.writeAndFlush(buffer);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            System.out.println(LocalDateTime.now() + "：收到客户端消息：" + messageRequestPacket.getMessage());
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");

            ByteBuf buffer = ctx.alloc().ioBuffer();
            PacketCodeC.INSTANCE.encode(buffer, messageResponsePacket);
            ctx.writeAndFlush(buffer);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
