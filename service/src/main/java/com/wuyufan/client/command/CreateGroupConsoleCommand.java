package com.wuyufan.client.command;

import com.wuyufan.bean.packet.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exe(Scanner scanner, Channel channel) {
        System.out.print("【拉人群聊】输入userId列表，userId之间英文逗号隔开：");
        String userIds = scanner.next();
        List<Long> collect = Arrays.stream(userIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        createGroupRequestPacket.setUserIds(collect);
        createGroupRequestPacket.setVersion((byte) 1);
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
