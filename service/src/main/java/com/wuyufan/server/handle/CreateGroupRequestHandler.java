package com.wuyufan.server.handle;

import cn.hutool.core.util.RandomUtil;
import com.wuyufan.bean.packet.request.CreateGroupRequestPacket;
import com.wuyufan.bean.packet.response.CreateGroupResponsePacket;
import com.wuyufan.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<Long> userIds = msg.getUserIds();
        List<String> userNameList = new ArrayList<>();
        // 1. 创建 channel 组
        DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        // 2. 筛选出待加入群聊的用户的 channel 和 username
        for (Long userId : userIds) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }
        CreateGroupResponsePacket packet = new CreateGroupResponsePacket();
        packet.setSuccess(true);
        packet.setGroupId(RandomUtil.randomInt());
        packet.setUserNames(userNameList);
        channelGroup.writeAndFlush(packet);
        System.out.print("群创建成功，id  为［"  +  packet.getGroupId()  + "］,  ");
        System.out.println("群里面有："  +  packet.getUserNames());
    }
}
