package com.router.Core.EventBus;


import com.router.Core.EventBus.event.EventBus;

/************************************************************
 * Description:  EventBus 事件接收回調...
 ***********************************************************/
public interface IEventBusListener {

    /**
     * 用於接收 EventBus 發送的事件，UI線程觸發
     *
     * @param msg
     */
    void onEventMainThread(EventBusMessage msg);

    /**
     * 註冊消息組類型
     */
    abstract void registEventHandlerMsgType();

    /**
     * 增加消息組類型
     *
     * @param msgType
     */
    void addHandlerMsgType(String msgType);

    /**
     * 普通的消息事件
     *
     * @param msg
     */
    abstract void onEventHandlerMsgUIThread(EventBusMessage msg);


    /**
     * 獲取事件總線EventBus
     *
     * @return 當前界面的事件總線。
     */
    EventBus getEventBus();
}