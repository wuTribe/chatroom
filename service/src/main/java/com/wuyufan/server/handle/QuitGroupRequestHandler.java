package com.wuyufan.server.handle;

import com.wuyufan.bean.packet.request.QuitGroupRequestPacket;
import com.wuyufan.bean.packet.response.QuitGroupResponsePacket;
import com.wuyufan.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        // 1.  获取群对应的ChannelGroup，然后将当前用户的Channel移除
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(msg.getGroupId());
        Channel channel = ctx.channel();
        channelGroup.remove(channel);
        QuitGroupResponsePacket packet = new QuitGroupResponsePacket();
        channel.writeAndFlush(packet);
    }
}
