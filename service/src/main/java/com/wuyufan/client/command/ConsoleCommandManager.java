package com.wuyufan.client.command;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 管理所有指令
 */
public class ConsoleCommandManager implements ConsoleCommand {
    private static final Map<String, ConsoleCommand> commandMap = new HashMap<>();

    public ConsoleCommandManager() {
        commandMap.put("sendToUser", new SendToUserConsoleCommand());
        commandMap.put("logout", new LogoutConsoleCommand());
        commandMap.put("createGroup", new CreateGroupConsoleCommand());
        commandMap.put("joinGroup", new JoinGroupConsoleCommand());
        commandMap.put("quitGroup", new QuitGroupConsoleCommand());
        commandMap.put("listGroup", new ListGroupMembersConsoleCommand());
    }

    @Override
    public void exe(Scanner scanner, Channel channel) {
        String command = scanner.next();
        ConsoleCommand consoleCommand = commandMap.get(command);
        if (consoleCommand == null) {
            System.err.println("无法识别［"  +  command  +  "］指令，请重新输入！");
            return;
        }
        consoleCommand.exe(scanner, channel);
    }
}
