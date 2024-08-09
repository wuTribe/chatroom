package com.wuyufan.client.handle;

import com.wuyufan.bean.packet.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class QuitGroupResponseHandle extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    public static final QuitGroupResponseHandle INSTANCE = new QuitGroupResponseHandle();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
        System.out.println("退出群聊成功....");
    }
}
