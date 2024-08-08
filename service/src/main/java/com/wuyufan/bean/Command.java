package com.wuyufan.bean;

/**
 * Netty 指令
 */
public interface Command {
    /** 登录请求*/
    Byte LOGIN_REQUEST = 1;
    /** 登录响应*/
    Byte LOGIN_RESPONSE = 2;

    /** 消息 请求*/
    Byte MESSAGE_REQUEST = 3;
    /** 消息 响应*/
    Byte MESSAGE_RESPONSE = 4;

    /** 创建群组 请求*/
    Byte CREATE_GROUP_REQUEST = 5;
    /** 创建群组 响应*/
    Byte CREATE_GROUP_RESPONSE = 6;

    /** 登出 请求*/
    Byte LOGOUT_REQUEST = 7;
    /** 登出 响应*/
    Byte LOGOUT_RESPONSE = 8;

    /** 登出 响应*/
    Byte JOIN_GROUP_REQUEST = 9;
    /** 登出 响应*/
    Byte JOIN_GROUP_RESPONSE = 10;

    /** 退出群聊 请求*/
    Byte QUIT_GROUP_REQUEST = 11;
    /** 退出群聊 响应*/
    Byte QUIT_GROUP_RESPONSE = 12;

    /** 获取群里成员 请求*/
    Byte LIST_GROUP_MEMBERS_REQUEST = 13;
    /** 获取群里成员 响应*/
    Byte LIST_GROUP_MEMBERS_RESPONSE = 14;
}
