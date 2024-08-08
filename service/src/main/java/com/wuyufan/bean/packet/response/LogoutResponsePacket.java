package com.wuyufan.bean.packet.response;

import com.wuyufan.bean.Command;
import com.wuyufan.bean.packet.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LogoutResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
