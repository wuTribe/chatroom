package com.wuyufan.client.handle;

import com.wuyufan.bean.packet.request.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println("加入群［" + msg.getGroupId() + "］成功！");
        } else {
            System.err.println("加入群［" + msg.getGroupId() + "］失败");
        }
    }
}
