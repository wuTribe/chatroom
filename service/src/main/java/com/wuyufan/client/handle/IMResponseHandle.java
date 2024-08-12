package com.wuyufan.client.handle;

import com.wuyufan.bean.Command;
import com.wuyufan.bean.packet.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class IMResponseHandle extends SimpleChannelInboundHandler<Packet> {

    public static final IMResponseHandle INSTANCE = new IMResponseHandle();

    private final Map<Byte, ChannelInboundHandlerAdapter> handlerMap = new HashMap<>();

    private IMResponseHandle() {
        handlerMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponseHandler.INSTANCE);
        handlerMap.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponseHandler.INSTANCE);
        handlerMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponseHandler.INSTANCE);
        handlerMap.put(Command.LOGOUT_RESPONSE, LogoutResponseHandler.INSTANCE);
        handlerMap.put(Command.MESSAGE_RESPONSE, MessageResponseHandler.INSTANCE);
        handlerMap.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponseHandle.INSTANCE);
        handlerMap.put(Command.HEART_BEAT_RESPONSE, HeartResponseHandler.INSTANCE);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
