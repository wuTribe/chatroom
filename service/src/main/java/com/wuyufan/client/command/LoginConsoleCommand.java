package com.wuyufan.client.command;

import com.wuyufan.bean.packet.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand{
    @Override
    public void exe(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        System.out.print("输入用户名登录：");
        String  username  =  scanner.nextLine();
        loginRequestPacket.setUserName(username);
        loginRequestPacket.setPassword("pwd");
        channel.writeAndFlush(loginRequestPacket);
    }
}
