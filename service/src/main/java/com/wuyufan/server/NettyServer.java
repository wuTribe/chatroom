package com.wuyufan.server;

import com.wuyufan.bean.Constants;
import com.wuyufan.codec.IMIdleStateHandler;
import com.wuyufan.codec.PacketCodecHandler;
import com.wuyufan.codec.Spliter;
import com.wuyufan.server.handle.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {


    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(IMRequestHandler.INSTANCE);
                    }
                });
        bind(serverBootstrap, Constants.SERVER_PORT);

    }

    private static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap
                .bind(port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("端口绑定成功..." + port);
                    } else {
                        bind(serverBootstrap, port + 1);
                    }
                });
    }
}
