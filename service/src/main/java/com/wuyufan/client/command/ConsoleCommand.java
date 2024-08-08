package com.wuyufan.client.command;

import io.netty.channel.Channel;

import java.util.Scanner;

public interface ConsoleCommand {
    void exe(Scanner scanner, Channel channel);
}
