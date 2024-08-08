package com.wuyufan.bean.packet.request;

import com.wuyufan.bean.Command;
import com.wuyufan.bean.packet.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GroupMessageRequestPacket extends Packet {
    private int groupId;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
