package com.wuyufan.server.handle;

import com.wuyufan.bean.Session;
import com.wuyufan.bean.packet.request.MessageRequestPacket;
import com.wuyufan.bean.packet.response.MessageResponsePacket;
import com.wuyufan.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        // 1.获得消息发送方的会话信息
        Channel channel = ctx.channel();
        Session session = SessionUtil.getSession(channel);
        System.out.println(LocalDateTime.now() + "：收到客户端消息：" + msg.getMessage());
        // 2.通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        // 3.获得消息接收方的Channel
        Channel toChannel = SessionUtil.getChannel(msg.getToUserId());
        if (toChannel != null && SessionUtil.hasLogin(channel)) {
            messageResponsePacket.setMessage("【" + msg.getMessage() + "】");
            // 使用 ctx.writeAndFlush(messageResponsePacket); 会直接从当前 pipline 往前找到第一个 out bound 进行处理，可能会跳过编解码的处理逻辑
            toChannel.writeAndFlush(messageResponsePacket);
        } else {
            messageResponsePacket.setMessage("［"  +  msg.getToUserId()  +  "］不在线，发送失败！");
            ctx.channel().writeAndFlush(messageResponsePacket);
        }

    }
}
