package com.wuyufan.client.command;

import com.wuyufan.bean.packet.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exe(Scanner scanner, Channel channel) {
        System.out.println("请输入退出的群组id：");
        QuitGroupRequestPacket packet = new QuitGroupRequestPacket();
        String groupId = scanner.next();
        packet.setGroupId(Integer.parseInt(groupId));
        channel.writeAndFlush(packet);
    }
}
