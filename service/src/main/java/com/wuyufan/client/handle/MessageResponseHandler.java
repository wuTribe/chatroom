package com.wuyufan.client.handle;

import com.wuyufan.bean.packet.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        long fromUserId = msg.getFromUserId();
        String fromUserName = msg.getFromUserName();
        System.out.println(LocalDateTime.now() + "" + fromUserId + "：" + fromUserName + "  ->  " + msg.getMessage());
    }
}
