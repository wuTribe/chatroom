package com.wuyufan.client;

import com.wuyufan.bean.Constants;
import com.wuyufan.bean.packet.request.LoginRequestPacket;
import com.wuyufan.bean.packet.request.MessageRequestPacket;
import com.wuyufan.client.handle.LoginResponseHandler;
import com.wuyufan.client.handle.MessageResponseHandler;
import com.wuyufan.codec.PacketDecoder;
import com.wuyufan.codec.PacketEncoder;
import com.wuyufan.codec.Spliter;
import com.wuyufan.utils.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    public static volatile boolean LOGIN_FINISH = false;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
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
        Scanner sc = new Scanner(System.in);
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    if (!SessionUtil.hasLogin(channel)) {
                        System.out.print("输入用户名登录：");
                        String  username  =  sc.nextLine();
                        loginRequestPacket.setUserName(username);
                        loginRequestPacket.setPassword("pwd");
                        channel.writeAndFlush(loginRequestPacket);
                        // 等待客户端响应，登录状态变更
                        if (!LOGIN_FINISH) {
                            Thread.sleep(500);
                            System.out.println("等待服务器回复登录状态...");
                        }
                    } else {
                        System.out.println("输入消息发送至服务端：");
                        long toUserId = Long.parseLong(sc.next());
                        String message = sc.next();
                        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
                    }
                } catch (Exception ignored) {
                }
            }
        }).start();
    }
}
