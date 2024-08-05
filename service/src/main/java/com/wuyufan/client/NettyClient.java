package com.wuyufan.client;

import com.wuyufan.Constants;
import com.wuyufan.handle.client.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.time.LocalDateTime;
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
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });
        connect(bootstrap, "127.0.0.1", Constants.SERVER_PORT, 3);
    }


    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap
                .connect(host, port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println("客户端链接成功");
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
}
