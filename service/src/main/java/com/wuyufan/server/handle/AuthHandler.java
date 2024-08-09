package com.wuyufan.server.handle;

import com.wuyufan.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
    public static final AuthHandler INSTANCE = new AuthHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
            return;
        }
        // 认证成功后，只要连接不断开，登录状态都不会改变
        ctx.pipeline().remove(this);
        super.channelRead(ctx, msg);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登录验证完毕,无须再次验证,  AuthHandler  被移除");
        } else {
            System.out.println("无登录验证，强制关闭连接！");
        }
    }
}
