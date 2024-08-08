package com.wuyufan.server;

import com.wuyufan.bean.Constants;
import com.wuyufan.codec.PacketDecoder;
import com.wuyufan.codec.PacketEncoder;
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
                        ch.pipeline().addLast(new LifeCyCleTestHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(new CreateGroupRequestHandler());
                        ch.pipeline().addLast(new LogoutRequestHandler());
                        ch.pipeline().addLast(new JoinGroupRequestHandler());
                        ch.pipeline().addLast(new QuitGroupRequestHandler());
                        ch.pipeline().addLast(new ListGroupMembersRequestHandler());
                        ch.pipeline().addLast(new GroupMessageRequestHandler());
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
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
