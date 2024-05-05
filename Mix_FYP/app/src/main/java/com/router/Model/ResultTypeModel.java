package com.router.Model;

import java.io.Serializable;
import java.util.List;

public class ResultTypeModel implements Serializable {
    /**
     * {
     * "id":1,"title":"影片標題名稱影片標題名稱",
     * "link":"www.baidu.com"
     * }
     */
    private int id;
    private String title;
    private String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

