package com.router.Core.Base.ViewModel;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.router.Core.EventBus.EventBusUtil;
import com.router.Core.EventBus.event.EventBus;
import com.router.Core.Http.Http;
import com.router.Core.Http.HttpService;
import com.router.utils.GsonUtils;

import java.lang.ref.WeakReference;

/**
 * Description:應用程序中間層基類。
 * 1、當Activity被創建時,維持Activity的弱引用,通過getView()方法便可從弱引用中獲取到Activity。
 * 2、當Activity被銷毀時,解除對Activity的弱引用,使Activity可以被及時銷毀。
 */
public abstract class BaseViewModel<T> implements IBaseViewModel {
    protected static HttpService httpService;

    //初始化httpService
    static {
        httpService = Http.getHttpService();
    }

    private boolean isViewOnPause = false;
    /**
     * Activity的弱引用
     */
    protected WeakReference<T> activityRef;

    /**
     * Activity創建時調用,建立Activity和VM之間的關係綁定,即實現Controller與中間層VM之間的關聯。
     *
     * @param view
     */
    public void attachView(
            T view) {
        activityRef = new WeakReference<T>(view);
    }

    /**
     * 判斷服務器返回的json是否有效
     *
     * @param responseJson
     * @return
     */
    public boolean isRespJsonValid(
            String responseJson) {
        return !(TextUtils.isEmpty(responseJson) || "null".equalsIgnoreCase(responseJson));
    }

    /**
     * 獲取Activity的引用
     *
     * @return
     */
    public T getView() {
        return activityRef.get();
    }

    /**
     * 檢查Activity的引用是否存在
     *
     * @return如果Activity的Ref引用容器存在,並且容器中確實存在Activity,那麼,該操作將會返回true。 如果已經被釋放, 則返回false。
     */
    public boolean isViewAttached() {
        return activityRef != null && activityRef.get() != null;
    }

    /**
     * 更新Activity或Fragment的視圖狀態,是否不可見
     * 1、當Activity或Fragment不可見時,isViewOnPause的值將會為true,當Activity或Fragment恢復可見狀態時,isViewOnPause的值將會為false。
     *
     * @param viewOnPause 最新狀態標示
     */
    public void notifyViewOnPause(
            boolean viewOnPause) {
        this.isViewOnPause = viewOnPause;
    }

    /**
     * 檢查Activity或Fragment是否處於不可見狀態
     *
     * @return
     */
    public boolean isViewOnPause() {
        return this.isViewOnPause;
    }

    public EventBus getEventBus() {
        return EventBus.getDefault();
    }

    public void onActivityCreate() {
    }

    public void onActivityStart() {
    }

    public void onActivityReStart() {
    }

    public void onActivityResume() {
        isViewOnPause = false;
    }

    public void onActivityPause() {
        isViewOnPause = true;
    }

    public void onActivityStop() {
    }

    public void onActivityDestroy() {
    }

    public void onTrimMemory(
            int level) {
// LeadingCloudApplication.getInstance().trimMemory(level);
    }

    public Gson mGson() {
        return GsonUtils.getGson();
    }

    /**
     * 根據操作意圖Action和資料來源,向EventBus的消息匯流排Handler發送Message
     *
     * @param whatAction 對應每個功能模組中的不同操作意圖或行為,不同的功能模組以及不同的操作意圖,他們的Action值不能相同
     * @param object     資料來源
     */
    public void postMessageToEventBusHandler(
            String whatAction,
            Object object) {
        EventBusUtil.Instance().postEventBusMsg(whatAction, object);
    }

    public void postMessageToEventBusHandler(
            String msgType,
            String whatAction,
            Object object) {
        EventBusUtil.Instance().postEventBusMsg(msgType, whatAction, object);
    }

    public void postMessageToEventBusHandler(
            String msgType,
            String whatAction,
            Object object,
            int what) {
        EventBusUtil.Instance().postEventBusMsg(msgType, whatAction, object, what);
    }

    public void postMessageToEventBusHandler(
            String msgType,
            String whatAction,
            Object object,
            int what,
            Object extendObject) {
        EventBusUtil.Instance().postEventBusMsg(msgType, whatAction, object, what, extendObject);
    }

    public void postMessageToEventBusHandler(
            String msgType,
            String whatAction,
            boolean _isNotifcation,
            Object object) {
        EventBusUtil.Instance().postEventBusMsg(msgType, whatAction, object, _isNotifcation);
    }

    public void postMessageToEventBusHandler(
            String msgType,
            String whatAction,
            Object object,
            Object extendObject) {
        EventBusUtil.Instance().postEventBusMsg(msgType, whatAction, object, extendObject);
    }
}