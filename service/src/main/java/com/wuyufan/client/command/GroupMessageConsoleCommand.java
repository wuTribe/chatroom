package com.wuyufan.client.command;

import cn.hutool.core.util.StrUtil;
import com.wuyufan.bean.packet.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class GroupMessageConsoleCommand implements ConsoleCommand{
    @Override
    public void exe(Scanner scanner, Channel channel) {
        System.out.println("输入发送的群组");
        int groupId = Integer.parseInt(scanner.next());
        String message = "";

        // 循环读取
        while (StrUtil.isBlank(message)) {
            System.out.println("输入发送的消息");
            message = scanner.nextLine();
        }

        GroupMessageRequestPacket packet = new GroupMessageRequestPacket();
        packet.setGroupId(groupId);
        packet.setMessage(message);
        channel.writeAndFlush(packet);
    }
}
