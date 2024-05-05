package com.router.utils;

import android.os.Handler;
import android.os.Looper;

import com.router.Core.Exector.ThreadExector;

/************************************************************
 * Description:  線程切換的工具類...
 ***********************************************************/
public class ThreadUtils {

    // 主線程的Handler
    private final static Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 切換至主線程操作
     *
     * @param runnable
     */
    public static void postMainThread(Runnable runnable) {
        if (runnable == null) return;
        if (Utils.isOnMainThread()) {
            runnable.run();
            return;
        }
        MAIN_HANDLER.post(runnable);
    }

    /**
     * 切換至子線程操作
     *
     * @param runnable
     */
    public static void postThread(Runnable runnable) {
        if (runnable == null) return;
        if (!Utils.isOnMainThread()) {
            runnable.run();
            return;
        }
        ThreadExector.Taskexecutor.execute(runnable);
    }

    /**
     * 延遲執行, 主線程當中執行.
     *
     * @param runnable
     * @param delayMillis The delay (in milliseconds) until the Runnable will be executed.
     */
    public static void postDelayed(Runnable runnable, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        MAIN_HANDLER.postDelayed(runnable, delayMillis);
    }
}
