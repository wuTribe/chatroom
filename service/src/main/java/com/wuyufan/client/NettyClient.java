package com.wuyufan.client;

import com.wuyufan.bean.Constants;
import com.wuyufan.bean.packet.MessageRequestPacket;
import com.wuyufan.handle.PacketCodeC;
import com.wuyufan.handle.client.ClientHandler;
import com.wuyufan.utils.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        connect(bootstrap, "127.0.0.1", Constants.SERVER_PORT, 3);
    }


    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap
                .connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        Channel channel = ((ChannelFuture) future).channel();
                        startConsoleThread(channel);
                    } else if (retry == 0){
                        System.out.println("重试次数已用完，放弃链接....");
                    } else {
                        // 退避算法，重试 n 次
                        int order = (Constants.MAX_RETRY - retry) + 1;
                        int delay = 1 << order;
                        System.err.println(LocalDateTime.now()  +  "：连接失败，第"  +  order  +  "次重连.....");
                        bootstrap.config().group().schedule(() -> {
                            connect(bootstrap, host, port, retry - 1);
                        }, delay, TimeUnit.SECONDS);
                    }
                });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("输入消息发送至服务端：");
                    Scanner scanner = new Scanner(System.in);
                    String line = scanner.nextLine();
                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);
                    ByteBuf byteBuf = channel.alloc().ioBuffer();
                    PacketCodeC.INSTANCE.encode(byteBuf, packet);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }
}
