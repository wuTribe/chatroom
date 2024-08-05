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
}
