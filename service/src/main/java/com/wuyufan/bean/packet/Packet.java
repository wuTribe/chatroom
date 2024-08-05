package com.wuyufan.bean.packet;

import lombok.Data;

/**
 * 协议
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 协议指令
     *
     * @return 指令
     */
    public abstract Byte getCommand();
}
