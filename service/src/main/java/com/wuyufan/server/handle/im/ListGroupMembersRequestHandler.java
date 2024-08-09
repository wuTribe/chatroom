package com.wuyufan.server.handle.im;

import com.wuyufan.bean.Session;
import com.wuyufan.bean.packet.request.ListGroupMembersRequestPacket;
import com.wuyufan.bean.packet.response.ListGroupMembersResponsePacket;
import com.wuyufan.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.stream.Collectors;

@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket msg) throws Exception {
        //  1.  获取群的ChannelGroup
        int groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        if (channelGroup == null) {
            System.err.println("群组不存在..." + groupId);
            return;
        }
        // 2. 加入所有群组的用户信息
        ListGroupMembersResponsePacket packet = new ListGroupMembersResponsePacket();
        packet.setGroupId(groupId);
        packet.setUserNames(channelGroup.stream().map(e -> {
            Session session = SessionUtil.getSession(e);
            return session.getUserName();
        }).collect(Collectors.toList()));
        ctx.channel().writeAndFlush(packet);
    }
}
