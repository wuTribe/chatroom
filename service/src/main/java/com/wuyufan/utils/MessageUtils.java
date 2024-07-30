package com.wuyufan.utils;

import cn.hutool.json.JSONUtil;
import com.wuyufan.bean.ResultMessage;

public class MessageUtils {

    public static String getMessage(boolean isSystemMessage,String fromName, Object message) {
        ResultMessage result = new ResultMessage();
        result.setSystem(isSystemMessage);
        result.setMessage(message);
        if(fromName != null) {
            result.setFromName(fromName);
        }

        return JSONUtil.toJsonStr(result);
    }
}
