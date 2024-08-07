package com.wuyufan.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Session {
    private long userId;
    private String userName;
}
