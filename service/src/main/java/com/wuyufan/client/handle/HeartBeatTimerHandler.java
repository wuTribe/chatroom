package com.wuyufan.client.handle;

import com.wuyufan.bean.packet.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;

import java.util.concurrent.TimeUnit;

public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
    // 服务端心跳的三分之一时间
    private static final int HEARTBEAT_INTERVAL = 5;
    Future<?> future;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleHeartBeat(ctx);
        System.out.println("心跳定时器启动....");
        super.channelActive(ctx);
    }

    private void scheduleHeartBeat(ChannelHandlerContext ctx) {
        future = ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                System.out.println("心跳发送.....");
                ctx.channel().writeAndFlush(new HeartBeatRequestPacket());
                scheduleHeartBeat(ctx);
            }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }



    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 如果通道断开则停止定时任务
        System.out.println("关掉定时任务");
        future.cancel(true);
        super.channelInactive(ctx);
    }
}
