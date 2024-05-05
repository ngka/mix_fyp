package com.router.Core.Base.Activity

        ;

import android.app.Activity;
import android.os.Bundle;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.EventBus.BaseEventHandler;
import com.router.Core.EventBus.EventBusMessage;

/**
 * Description: FragmentActivity底層基類
 */
public abstract class BaseFragmentActivity<T extends BaseViewModel<Activity>, E extends BaseEventHandler<T>> extends IBaseActivity {
    /**
     * 中間層ViewModel
     */
    protected T mViewModel;
    /**
     * EventBus事件匯流排消息處理器EventHandler
     */
    protected E mEventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //執行ViewModel創建操作
        mViewModel = bindViewModel();
        //創建EventHandler事件匯流排消息處理器，由子類Fragment去實現，因為不同的介面，處理的事件會不同。
        mEventHandler = createEventHandler();
        //對使中間層ViewModel持有當前Activity的弱引用。
        mViewModel.attachView(this);
        mViewModel.onActivityCreate();
        //先初始化ViewModel，父類中有注册eventbus消息組類型，註冊時具體實現介面可能會調用getViewModel()
        super.onCreate(savedInstanceState);
    }

    public T getViewModel() {
        return mViewModel;
    }

    /**
     * 創建EventBus事件處理器EventHandler
     *
     * @return子類EventHandler
     */
    protected abstract E createEventHandler();

    /**
     * 創建
     *
     * @return子類Activity中聲明的ViewModel。
     */
    protected abstract T bindViewModel();

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onActivityStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mViewModel.onActivityReStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.onActivityResume();
        //設定視圖可見狀態
        if (mEventHandler!=null){
            mEventHandler.onEventVisible(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewModel.onActivityPause();
        //設定視圖可見狀態
        if (mEventHandler!=null){
            mEventHandler.onEventVisible(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.onActivityStop();
    }

    @Override
    protected void onDestroy() {
//Activity銷毀時執行，解除Activity與ViewModel之間的綁定關係
        if (mViewModel!=null){
            mViewModel.onActivityDestroy();
        }
        super.onDestroy();
        if (mEventHandler!=null){
            mEventHandler.onEventDestroy();
            mEventHandler = null;
        }
    }

    @Override
    public void onTrimMemory(
            int level) {
        if (mViewModel!=null){
            mViewModel.onTrimMemory(level);
        }
        super.onTrimMemory(level);
    }

    /**
     * BaseEventHandler中注册，這裡無需重複注册
     *
     * @return
     */
    @Override
    protected boolean isRegisEventBus() {
        return false;
    }

    @Override
    public void registEventHandlerMsgType() {
    }

    @Override
    public void onEventHandlerMsgUIThread(
            EventBusMessage msg) {
    }
}
