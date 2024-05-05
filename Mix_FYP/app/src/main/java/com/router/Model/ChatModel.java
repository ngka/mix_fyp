package com.router.Model;

import java.io.Serializable;

/**
 * 聊天介面model
 */
public class ChatModel implements Serializable {
    public static final int SEND = 1;     //發送消息
    public static final int RECEIVE = 2; //接收到的消息
    private int state;       //消息的狀態（是接收還是發送）
    private String message; //消息的內容

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
