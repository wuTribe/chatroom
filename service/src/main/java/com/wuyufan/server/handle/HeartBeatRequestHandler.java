package com.wuyufan.server.handle;

import com.wuyufan.bean.packet.request.HeartBeatRequestPacket;
import com.wuyufan.bean.packet.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        System.out.println("接收到心跳....");
        ctx.channel().writeAndFlush(new HeartBeatResponsePacket());
    }
}
