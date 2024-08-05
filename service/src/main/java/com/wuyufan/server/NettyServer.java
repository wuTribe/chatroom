package com.wuyufan.server;

import com.wuyufan.Constants;
import com.wuyufan.handle.server.FirstServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
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
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new FirstServerHandler());
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
