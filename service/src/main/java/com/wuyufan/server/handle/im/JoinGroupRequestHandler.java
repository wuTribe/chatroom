package com.wuyufan.server.handle.im;

import com.wuyufan.bean.packet.request.JoinGroupRequestPacket;
import com.wuyufan.bean.packet.request.JoinGroupResponsePacket;
import com.wuyufan.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        // 获取群组 group，加入到群组
        int groupId = Integer.parseInt(msg.getGroupId());
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());
        ctx.channel().writeAndFlush(new JoinGroupResponsePacket(true, groupId));
    }
}
