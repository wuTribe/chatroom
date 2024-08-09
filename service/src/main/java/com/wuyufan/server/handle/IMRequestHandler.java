package com.wuyufan.server.handle;

import com.wuyufan.bean.Command;
import com.wuyufan.bean.packet.Packet;
import com.wuyufan.server.handle.im.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class IMRequestHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMRequestHandler INSTANCE = new IMRequestHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap = new HashMap<>();

    private IMRequestHandler() {
        handlerMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
        handlerMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(Command.MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
