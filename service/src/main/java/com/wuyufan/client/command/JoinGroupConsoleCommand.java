package com.wuyufan.client.command;

import com.wuyufan.bean.packet.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exe(Scanner scanner, Channel channel) {
        System.out.println("输入 group id，加入群聊：");
        String groupId = scanner.next();
        JoinGroupRequestPacket packet = new JoinGroupRequestPacket(groupId);
        channel.writeAndFlush(packet);
    }
}
