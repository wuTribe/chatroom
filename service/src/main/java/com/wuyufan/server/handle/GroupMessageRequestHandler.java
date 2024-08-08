package com.wuyufan.server.handle;

import com.wuyufan.bean.Session;
import com.wuyufan.bean.packet.request.GroupMessageRequestPacket;
import com.wuyufan.bean.packet.response.GroupMessageResponsePacket;
import com.wuyufan.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        //  1.拿到groupId  构造群聊消息的响应
        int groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            System.out.println("群组不存在" + groupId);
            return;
        }
        Session fromSession = SessionUtil.getSession(ctx.channel());
        GroupMessageResponsePacket packet = new GroupMessageResponsePacket();
        packet.setFromUserId(fromSession.getUserId());
        packet.setGroupId(groupId);
        packet.setMessage(msg.getMessage());
        channelGroup.writeAndFlush(packet);
    }
}
