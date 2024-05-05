package com.router.Core.EventBus;

/************************************************************
 * Description: EventBus 發送的消息數據
 ***********************************************************/
public class EventBusMessage {

    /**
     * 消息組類型
     **/
    private String msgType;

    /**
     * 消息類型，數字類型重複概率較大，儘量別用
     **/
    public int what;

    /**
     * 消息具體類型
     **/
    public String whatAction;

    /**
     * 消息 內容
     **/
    public Object obj;

    /**
     * 消息 擴展內容
     */
    public Object extendObj;

    /**
     * 消息 是否是後台通知的消息
     **/
    private boolean isFromNotifcation = false;

    /**
     * 用於標識 本消息是否有效，即是否被處理了
     */
    private boolean avaliable = true;

    public EventBusMessage() {
    }

    public EventBusMessage(String _msgType) {
        msgType = _msgType;
    }

    public EventBusMessage(String _msgType, boolean _isNotifcation) {
        msgType = _msgType;
        isFromNotifcation = _isNotifcation;
    }

    /**
     * 消息組類型
     **/
    public String getMsgType() {
        if (msgType == null) msgType = "";
        return msgType;
    }

    /**
     * 消息 是否是後台通知的消息
     **/
    public boolean isNotifcation() {
        return isFromNotifcation;
    }

    /**
     * 銷毀事件
     */
    public void destroy() {
        msgType = null;
        what = 0;
        whatAction = null;
        obj = null;
        isFromNotifcation = false;
        avaliable = false;
    }

    /**
     * 用於標識本消息是否有效，即是否被處理了
     */
    public boolean isAvaliable() {
        return avaliable;
    }

    /**
     * 消息內容轉字元
     */
    public String ObjToString() {
        if (obj != null) {
            return obj.toString();
        }
        return "";
    }

    /**
     * 消息 擴展內容 轉字符
     */
    public String ExtObjToString() {
        if (extendObj != null) {
            return extendObj.toString();
        }
        return "";
    }
}