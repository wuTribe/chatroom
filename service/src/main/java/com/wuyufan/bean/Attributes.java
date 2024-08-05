package com.wuyufan.bean;

import io.netty.util.AttributeKey;

public interface Attributes {
    /** 是否登录标记位*/
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
