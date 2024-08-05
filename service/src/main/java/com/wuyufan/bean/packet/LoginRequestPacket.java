package com.wuyufan.bean.packet;

import com.wuyufan.bean.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录请求包
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequestPacket extends Packet {
    private Integer userId;
    private String userName;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
