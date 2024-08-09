package com.wuyufan.client.handle;

import com.wuyufan.bean.packet.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        System.out.print("群创建成功，id  为［"  +  msg.getGroupId()  + "］,  ");
        System.out.println("群里面有："  +  msg.getUserNames());
    }
}
