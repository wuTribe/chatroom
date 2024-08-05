package com.wuyufan.handle.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(LocalDateTime.now() + "：往客户端写出数据");
        ctx.writeAndFlush(getByteBuf(ctx));
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 服务端接受数据
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(LocalDateTime.now() + "：读取服务端数据 ->" + byteBuf.toString(StandardCharsets.UTF_8));
    }



    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 获取二进制抽象 buf
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes("你好 wyf".getBytes(StandardCharsets.UTF_8));
        return buffer;
    }
}
