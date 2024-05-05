package com.router.Core.EventBus;


import com.router.Core.EventBus.event.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Description:  EventBus 註冊接口...
 ***********************************************************/
public class IEventBusManager implements Serializable {

    private IEventBusListener mEventHandler;

    /**
     * 本頁面 所需處理的消息類型集合
     */
    private List<String> mEventMsgTypes;

    public IEventBusManager(IEventBusListener listener) {
        mEventHandler = listener;
        mEventMsgTypes = new ArrayList<>();
    }

    /**
     * 將事件總線消息處理器EventHandler註冊到當前界面的事件總線中
     */
    public void eventRegister() {
        if (mEventHandler != null) {
            getEventBus().register(mEventHandler);
            mEventHandler.registEventHandlerMsgType();
        }
    }

    /**
     * 界面銷毀時，解除EventBus事件總線中綁定的EventHandler
     */
    public void eventUnregister() {
        if (mEventHandler != null) {
            getEventBus().unregister(mEventHandler);
            mEventHandler = null;
        }
    }

    /**
     * 註冊 消息類型
     *
     * @param msgType
     */
    public void regisEventMsgType(String msgType) {
        if (isMsgTypeListValid() && !mEventMsgTypes.contains(msgType)) {
            mEventMsgTypes.add(msgType);
        }
    }

    /**
     * 是否註冊消息
     *
     * @param msgType
     * @return
     */
    public boolean isRegisMsg(String msgType) {
        return isRegisMsg(msgType, true);
    }

    /**
     * 是否註冊消息
     *
     * @param msgType      消息組類型
     * @param isEmpytValid 新模式應傳false，默認為true  兼容如果組類型為空，是否按註冊處理（起初沒有組類型模式）
     * @return
     */
    public boolean isRegisMsg(String msgType, boolean isEmpytValid) {
        return isMsgTypeListValid() && ((isEmpytValid && mEventMsgTypes.size() == 0) || mEventMsgTypes.contains(msgType));
    }

    /**
     * 消息類型集合是否為null
     *
     * @return
     */
    public boolean isMsgTypeListValid() {
        return mEventMsgTypes != null;
    }

    /**
     * 獲取事件總線EventBus
     *
     * @return 當前界面的事件總線。
     */
    public EventBus getEventBus() {
        return EventBus.getDefault();
    }
}
