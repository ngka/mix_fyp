package com.router.Core.EventMsgFilter;

import android.app.Activity;

import com.router.Core.EventBus.EventBusMessage;

/************************************************************
 * Description:  全局消息過濾...
 ***********************************************************/
public class IBaseFilterAction {

    private static IBaseFilterListener mGlobalEventFilter = null;

    /**
     * 設置全局過濾器
     *
     * @param globalEventFilter
     */
    public static void setGlobalEventFilter(IBaseFilterListener globalEventFilter) {
        mGlobalEventFilter = globalEventFilter;
    }

    /**
     * 全局消息過濾，采用反射方式 -- 後面有好的方式，可以更換
     *
     * @param eventHandlerMsg
     * @param context
     * @return
     */
    public static boolean filterEventMsg(EventBusMessage eventHandlerMsg, Activity context) {
        boolean result = true;
        if (mGlobalEventFilter != null) {
            result = mGlobalEventFilter.filterEventMsg(eventHandlerMsg, context);
        }
        return result;
    }
}
