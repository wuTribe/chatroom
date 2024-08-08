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
    Byte LOGOUT_REQUEST = 5;
    /** 登出 响应*/
    Byte LOGOUT_RESPONSE = 6;
}
