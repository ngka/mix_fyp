package com.router.Model;

import java.io.Serializable;

public class MessageTypeModel implements Serializable {
    /**
     * {
     * "id":1,
     * "content":""
     * }
     */
    int id;
    String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
