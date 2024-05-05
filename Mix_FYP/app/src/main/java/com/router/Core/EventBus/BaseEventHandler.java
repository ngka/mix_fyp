
package com.router.Core.EventBus;

import com.router.Core.EventBus.event.EventBus;
import com.router.Core.EventMsgFilter.IBaseFilterAction;
import com.router.Core.EventMsgFilter.IBaseFilterListener;

import java.lang.ref.WeakReference;

/************************************************************
 * Description:分擔程式碼邏輯EventBus事件注册處理….
 *
 ***********************************************************/
public abstract class BaseEventHandler<T> implements IEventBusListener {
    /**
     * ViewModel弱引用
     */
    private WeakReference<T> viewModelRef;
    /**
     * EventBus注册、消息處理…
     */
    private IEventBusManager mEventBusManager;

    public BaseEventHandler(T t) {
        viewModelRef = new WeakReference<T>(t);
//初始化並注册事件
        eventBusInit();
//初始化消息攔截器
        mEventFilterListener = initEventFilterListener();
    }

    public boolean isViewModelAttach() {
        return viewModelRef != null && viewModelRef.get() != null;
    }

    public T getViewModel() {
        return viewModelRef.get();
    }

    /**
     * 用於接收EventBus發送的事件,UI線程觸發
     *
     * @param msg
     */
    @Override
    public void onEventMainThread(EventBusMessage msg) {
//消息篩檢程式先看看是否是全域事件
        if (filterEventMsg(msg)) {
            return;
        }
/**此處做消息過濾,並不是所有的消息都需要響應**/
        if (mEventValid() && getEventBusManager().isRegisMsg(msg.getMsgType())) {
            onEventHandlerMsgUIThread(msg);
        }
    }

    /**
     * 介面銷毀時,取消綁定
     */
    public void onEventDestroy() {
// EventBus取消注册
        eventBusUnregister();
    }

    /**
     * 設定視圖可見狀態
     *
     * @param visible
     */
    public void onEventVisible(boolean visible) {
        setVisibleChanged(visible);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////// EventBus相關
    @Override
    public void addHandlerMsgType(String msgType) {
        if (mEventValid()) {
            getEventBusManager().regisEventMsgType(msgType);
        }
    }

    /**
     * 獲取事件匯流排EventBus
     *
     * @return當前介面的事件匯流排。
     */
    @Override
    public EventBus getEventBus() {
        if (mEventValid()) {
            return getEventBusManager().getEventBus();
        }
        return EventBus.getDefault();
    }

    /**
     * 注册消息組類型
     */
    @Override
    public abstract void registEventHandlerMsgType();

    /**
     * 普通的消息事件
     *
     * @param msg
     */
    @Override
    public abstract void onEventHandlerMsgUIThread(EventBusMessage msg);

    /**
     * 初始化並注册事件
     */
    private void eventBusInit() {
        mEventBusManager = new IEventBusManager(this);
        mEventBusManager.eventRegister();
    }

    /**
     * eventbus是否有效
     *
     * @return
     */
    private boolean mEventValid() {
        return mEventBusManager != null;
    }

    /**
     * 獲取eventbus管理器
     *
     * @return
     */
    private IEventBusManager getEventBusManager() {
        return mEventBusManager;
    }

    /**
     * 介面銷毀時,解除EventBus事件匯流排中綁定的訂閱服務器
     */
    private void eventBusUnregister() {
        if (mEventValid()) {
            mEventBusManager.eventUnregister();
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////// EventBus相關
//////////////////////////////////////////////////////////////////////////////////////////////// EventBus攔截器
    /**
     * 攔截消息處理
     */
    private IBaseFilterListener mEventFilterListener;

    /**
     * 獲取消息攔截器
     */
    public IBaseFilterListener initEventFilterListener() {
        return null;
    }

    /**
     * 消息篩檢程式
     *
     * @param msg
     * @return
     */
    private boolean filterEventMsg(EventBusMessage msg) {
        boolean result = !IBaseFilterAction.filterEventMsg(msg, null);
        return result || (mEventFilterListener != null && mEventFilterListener.filterEventMsg(msg, null));
    }

    /**
     * 設定視圖是否可見
     *
     * @param vis
     */
    private void setVisibleChanged(boolean vis) {
        if (mEventFilterListener != null) {
            mEventFilterListener.setVisibleChanged(vis);
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////// EventBus攔截器
}
