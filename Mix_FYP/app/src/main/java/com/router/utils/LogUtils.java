package com.router.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Description: 日誌工具類(默認開啟日誌)
 */
public class LogUtils {

    private static final String TAG = "Log";
    /**
     * 是否打印日誌信息
     */
    public static boolean isDebug = true;

    // 下面四個是默認tag的函數
    public static void i(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.v(TAG, msg);
    }

    //下面是傳入類名打印log
    public static void i(Class<?> _class, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.i(_class.getName(), msg);
    }

    public static void d(Class<?> _class, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.d(_class.getName(), msg);
    }

    public static void e(Class<?> _class, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.e(_class.getName(), msg);
    }

    public static void v(Class<?> _class, String msg) {
        if (isDebug && !TextUtils.isEmpty(msg))
            Log.v(_class.getName(), msg);
    }

    // 下面是傳入自定義tag的函數
    public static void i(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg))
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg))
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg))
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg))
            Log.v(tag, msg);
    }

    public static void throwException(String TAG, String fromMethod) {
        if (!isDebug) return;
        if (TextUtils.isEmpty(TAG)) TAG = "Custom Exception TAG > ";
        if (fromMethod == null) fromMethod = "";
        Log.e(TAG, "############ 警告 此處有異常 開始 ############" + fromMethod);
        Exception exception = new Exception();
        StackTraceElement[] stackTraceElements;
        if ((stackTraceElements = exception.getStackTrace()) != null) {
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                if (stackTraceElement != null) {
                    Log.e(TAG, "### " + stackTraceElement.toString() + " ###");
                }
            }
        }
        Log.e(TAG, "############ 警告 此處有異常 結束 ############" + fromMethod);
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        LogUtils.isDebug = isDebug;
    }
}
