package com.wuyufan.bean.packet.response;

import com.wuyufan.bean.Command;
import com.wuyufan.bean.packet.Packet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_RESPONSE;
    }
}
