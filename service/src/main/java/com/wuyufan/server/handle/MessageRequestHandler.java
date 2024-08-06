package com.wuyufan.server.handle;

import com.wuyufan.bean.packet.MessageRequestPacket;
import com.wuyufan.bean.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        System.out.println(LocalDateTime.now() + "：收到客户端消息：" + msg.getMessage());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("服务端回复【" + msg.getMessage() + "】");

        // 使用 ctx.writeAndFlush(messageResponsePacket); 会直接从当前 pipline 往前找到第一个 out bound 进行处理，可能会跳过编解码的处理逻辑
        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
