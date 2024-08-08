package com.wuyufan.client.handle;

import com.wuyufan.bean.packet.response.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) throws Exception {
        System.out.println(msg.getGroupId() + " 的群组消息：" + msg.getFromUserId() + " 用户，" + msg.getMessage());
    }
}
