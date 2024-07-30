package com.wuyufan.bean;

import lombok.Data;

/**
 * 服务器发送给浏览器的websocket数据
 */
@Data
public class ResultMessage {

    private boolean isSystem;
    private String fromName;
    private Object message;//如果是系统消息是数组


}
