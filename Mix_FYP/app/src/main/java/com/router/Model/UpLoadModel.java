package com.router.Model;

import android.net.Uri;

import java.io.Serializable;

/************************************************************
 * Description:  图片上传返回数据...
 ***********************************************************/
public class UpLoadModel implements Serializable {

    private String savePath;

    private String imgUrl;

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
