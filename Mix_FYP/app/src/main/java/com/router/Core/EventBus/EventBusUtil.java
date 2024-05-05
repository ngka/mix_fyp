package com.router.Core.EventBus;


import com.router.Core.EventBus.event.EventBus;

/************************************************************
 * Description: 發送EventBus工具類 ... msgType必須使用、what僅作為第三級類型判斷使用 msgType > whatAction > what
 ***********************************************************/
public class EventBusUtil {

    private static volatile EventBusUtil instance = null;

    public static EventBusUtil Instance() {
        EventBusUtil inst = instance;
        if (inst == null) {
            synchronized (EventBusUtil.class) {
                inst = instance;
                if (inst == null) {
                    inst = new EventBusUtil();
                    instance = inst;
                }
            }
        }
        return inst;
    }

    private final int DefaultWhat = -1020302010;

    public void postEventBusMsg(String msgType, String whatAction, Object object) {
        postEventBusMsg(msgType, DefaultWhat, whatAction, object, null, false);
    }

    public void postEventBusMsg(String msgType, String whatAction, Object object, Object extendObject) {
        postEventBusMsg(msgType, DefaultWhat, whatAction, object, extendObject, false);
    }

    public void postEventBusMsg(String msgType, String whatAction, Object object, boolean _isNotifcation) {
        postEventBusMsg(msgType, DefaultWhat, whatAction, object, null, _isNotifcation);
    }

    public void postEventBusMsg(String msgType, String whatAction, Object object, Object extendObject, boolean _isNotifcation) {
        postEventBusMsg(msgType, DefaultWhat, whatAction, object, extendObject, _isNotifcation);
    }

    public void postEventBusMsg(String msgType, String whatAction, Object object, int what) {
        postEventBusMsg(msgType, what, whatAction, object, null, false);
    }

    public void postEventBusMsg(String msgType, String whatAction, Object object, int what, Object extendObject) {
        postEventBusMsg(msgType, what, whatAction, object, extendObject, false);
    }

    public void postEventBusMsg(String whatAction, Object object) {
        postEventBusMsg("", DefaultWhat, whatAction, object, null, false);
    }

    /**
     * 發送EventBus消息通知 唯一入口 （方便後期維護）
     *
     * @param msgType           消息大類型 - 一級過濾條件
     * @param whatAction        消息小類型 - 二級過濾條件
     * @param what              消息小類型 - 三級過濾條件
     * @param object            消息內容
     * @param extendObject      消息拓展內容
     * @param isFromNotifcation 是否來源通知
     */
    public void postEventBusMsg(String msgType, int what, String whatAction, Object object, Object extendObject, boolean isFromNotifcation) {
        EventBusMessage message = new EventBusMessage(msgType, isFromNotifcation);
        message.what = what;
        message.whatAction = whatAction;
        message.obj = object;
        message.extendObj = extendObject;
        EventBus.getDefault().post(message);
    }
}
