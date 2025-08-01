package com.ShiXi.feishu.template;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FeishuMessage {
    private String msg_type;
    public FeishuMessage(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }
}
