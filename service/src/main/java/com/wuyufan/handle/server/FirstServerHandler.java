package com.wuyufan.handle.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 服务端接受数据
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(LocalDateTime.now() + "：服务器读取客户端数据 ->" + byteBuf.toString(StandardCharsets.UTF_8));

        // 响应客户端
        ctx.writeAndFlush(getByteBuf(ctx));
    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 获取二进制抽象 buf
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes("你好 我已经收到消息了".getBytes(StandardCharsets.UTF_8));
        return buffer;
    }
}
