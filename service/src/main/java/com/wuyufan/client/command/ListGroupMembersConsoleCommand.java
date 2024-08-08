package com.wuyufan.client.command;

import com.wuyufan.bean.packet.request.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    @Override
    public void exe(Scanner scanner, Channel channel) {
        System.out.print("输入groupId，获取群成员列表：");
        String groupId = scanner.next();
        ListGroupMembersRequestPacket packet = new ListGroupMembersRequestPacket();
        packet.setGroupId(Integer.parseInt(groupId));
        channel.writeAndFlush(packet);
    }
}
