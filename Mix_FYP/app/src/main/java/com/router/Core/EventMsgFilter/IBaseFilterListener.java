package com.router.Core.EventMsgFilter;

import android.app.Activity;

import com.router.Core.EventBus.EventBusMessage;

/************************************************************
 * Description:  基類過濾消息...
 ***********************************************************/
public interface IBaseFilterListener {

    /**
     * 過濾消息
     *
     * @param msg
     * @param context
     * @return true:已過濾   false:未過濾
     */
    boolean filterEventMsg(EventBusMessage msg, Activity context);

    /**
     * 當前視圖 可見狀態發送變化
     *
     * @param vis
     */
    void setVisibleChanged(boolean vis);
}
