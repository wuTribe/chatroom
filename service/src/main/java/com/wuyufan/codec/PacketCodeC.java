package com.wuyufan.codec;

import com.wuyufan.bean.Command;
import com.wuyufan.bean.packet.*;
import com.wuyufan.bean.packet.request.*;
import com.wuyufan.bean.packet.response.*;
import com.wuyufan.bean.serializer.JSONSerializer;
import com.wuyufan.bean.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

public class PacketCodeC {
    public static final int MAGIC_NUMBER = 0x12345678;

    public static final PacketCodeC INSTANCE = new PacketCodeC();


    /**
     * 指令集
     */
    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    /**
     * 序列化集
     */
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);

        serializerMap = new HashMap<>();
        serializerMap.put(Serializer.DEFAULT.getSerializerAlgorithm(), new JSONSerializer());
    }


    public void encode(ByteBuf byteBuf, Packet packet) {
        // 1. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }



    /**
     * 协议编码
     *
     * @param packet 协议
     * @return 二进制
     */
    public ByteBuf encode(Packet packet) {
        // String allocType = SystemPropertyUtil.get("io.netty.allocator.type", PlatformDependent.isAndroid() ? "unpooled" : "pooled");
        // 根据平台创建池化 or 非池化对象
        // ioBuffer()方法会返回适配IO读写相关的内存 写到IO缓冲区的效果更高
        ByteBuf buffer = ByteBufAllocator.DEFAULT.ioBuffer();

        buffer.writeByte(MAGIC_NUMBER);
        buffer.writeByte(packet.getVersion());
        buffer.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        buffer.writeByte(packet.getCommand());
        byte[] serialize = Serializer.DEFAULT.serialize(packet);
        buffer.writeByte(serialize.length);
        buffer.writeBytes(serialize);

        return buffer;
    }

    /**
     * 协议解码
     *
     * @param buffer 二进制
     * @return Java 对象
     */
    public Packet decode(ByteBuf buffer) {
        // 跳过魔术
        // int 四个字节
        buffer.skipBytes(4);
        // 跳过版本号
        buffer.skipBytes(1);
        byte serializeAlgorithm = buffer.readByte();
        // 指令
        byte cmd = buffer.readByte();
        // 数据长度，读取数据
        int length = buffer.readInt();
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(cmd);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
            return serializer.deserialize(bytes, requestType);
        }
        return null;
    }


    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
