package com.router.Model;

import android.net.Uri;

import java.io.Serializable;
import java.util.List;

/************************************************************
 * Description:  圖片上傳返回數據...
 ***********************************************************/
public class UpLoadResultModel implements Serializable {

    private List<UpLoadModel> items;

    public List<UpLoadModel> getItems() {
        return items;
    }

    public void setItems(List<UpLoadModel> items) {
        this.items = items;
    }
}
