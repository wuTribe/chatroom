package com.wuyufan.client.command;

import com.wuyufan.bean.packet.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exe(Scanner scanner, Channel channel) {
        long toUserId = Long.parseLong(scanner.next());
        String message = scanner.next();
        MessageRequestPacket packet = new MessageRequestPacket(toUserId, message);
        channel.writeAndFlush(packet);
    }
}
