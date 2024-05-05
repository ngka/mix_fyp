package com.router.utils;


import android.graphics.Bitmap;
import android.os.Looper;

/**
 * Created by Administrator on 2016/7/26.
 */
public class Utils {

    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static boolean checkBitmapNotRecycle(Bitmap bitmap) {
        return bitmap != null && !bitmap.isRecycled();
    }
}
