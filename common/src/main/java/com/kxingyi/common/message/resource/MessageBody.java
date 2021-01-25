package com.kxingyi.common.message.resource;

/**
 * @author: xin ru-yi
 * @date: 2021-01-15
 * @desc: message
 */
public class MessageBody {
    String action;
    String id;

    public MessageBody() {
    }

    public MessageBody(String action, String id) {
        this.action = action;
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
