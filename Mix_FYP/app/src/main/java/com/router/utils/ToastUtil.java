package com.router.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

/************************************************************
 * Description: Toast消息提醒
 ***********************************************************/
public class ToastUtil {

    private volatile Toast toast;
    private static volatile ToastUtil instance = null;

    public static ToastUtil getInstance() {
        ToastUtil inst = instance;
        if (inst == null) {
            synchronized (ToastUtil.class) {
                inst = instance;
                if (inst == null) {
                    inst = new ToastUtil();
                    instance = inst;
                }
            }
        }
        return inst;
    }

    private ToastUtil() {
        toast = Toast.makeText(ActivityManager.getInstance().getAppContext(), "", Toast.LENGTH_LONG);
    }

    /**
     * 顯示Toast,使用唯壹靜態Toast對象(短時間)
     *
     * @param msg
     */
    public void show(String msg) {
        show(msg, 0);
    }

    /**
     * 顯示Toast,使用唯壹靜態Toast對象
     *
     * @param msg  要顯示的內容
     * @param type 顯示時間類型(0/短、1/長)
     */
    public void show(String msg, int type) {
        try {
            if (TextUtils.isEmpty(msg)) return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//9.0以上toast直接用原生的方法即可，並不用setText防止重復的顯示的問題
                Toast.makeText(ActivityManager.getInstance().getAppContext(), msg, (type == 0) ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
            } else {
                // 設置內容
                toast.setText(msg);
                // 設置時間
                if (type == 0) {// 短
                    toast.setDuration(Toast.LENGTH_SHORT);
                } else {// 長
                    toast.setDuration(Toast.LENGTH_LONG);
                }
                // 顯示Toast
                toast.show();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public synchronized void recycle() {
        if (instance != null) {
            instance = null;
        }
        if (toast != null) {
            toast = null;
        }
    }

    public void showThread(final String msg) {
        showThread(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 線程中顯示Toast
     *
     * @param msg
     * @param type
     */
    public void showThread(final String msg, final int type) {
        if (Utils.isOnMainThread()) {
            show(msg, type);
        } else {
            ThreadUtils.postMainThread(new Runnable() {
                @Override
                public void run() {
                    show(msg, type);
                }
            });
        }
    }

    // Toast顯示
    public void show(Context context, final String toast_info) {
        if (TextUtils.isEmpty(toast_info)) return;
        if (Utils.isOnMainThread()) {
            show(toast_info);
        } else if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    show(toast_info);
                }
            });
        }
    }
}
