package com.router.Model;

import android.net.Uri;

import java.io.Serializable;

/************************************************************
 * Description:  圖片...
 ***********************************************************/
public class PhotoModel implements Serializable {

    // 圖片id
    private long id;

    // 圖片路徑
    private String path;
    private Uri uri;
    // 圖片名稱
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
