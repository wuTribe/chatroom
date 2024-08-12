package com.wuyufan.client.handle;

import com.wuyufan.bean.packet.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class HeartResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> {

    public static final HeartResponseHandler INSTANCE = new HeartResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatResponsePacket msg) throws Exception {
        System.out.println("接受到服务器的心跳回包");
    }
}
