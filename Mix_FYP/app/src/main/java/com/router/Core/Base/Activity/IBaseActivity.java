package com.router.Core.Base.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.router.Core.EventBus.event.EventBus;
import com.router.Core.EventMsgFilter.IBaseFilterAction;
import com.router.Core.EventMsgFilter.IBaseFilterListener;
import com.router.Core.Permission.PermissionListener;
import com.router.R;
import com.router.utils.ActivityManager;
import com.router.utils.CommonUtils;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Core.EventBus.IEventBusListener;
import com.router.Core.EventBus.IEventBusManager;
import com.router.utils.LoadingDialogUtil;
import com.router.utils.NetStateUtil;
import com.router.utils.StatusBarCompat;
import com.router.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Description: 基類activity
 ***********************************************************/
public abstract class IBaseActivity extends FragmentActivity implements IEventBusListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            getWindow().getDecorView().setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//4.4全透明
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | params.flags;
        }
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        setOrientation();
        initState();
        // 初始化並注册事件
        eventBusInit();
        //初始化消息攔截器
        mEventFilterListener = initEventFilterListener();
    }

    protected boolean isNetAvailable() {
        boolean result = true;
        if (!NetStateUtil.isNetAvailable()) {
            result = false;
            ToastUtil.getInstance().show(getString(R.string.common_request_nonet_toast));
        }
        return result;
    }

    /**
     * 沉浸式狀態列
     */
    protected void initState() {
//        StatusBarCompat.compat(this, ContextCompat.getColor(this,R.color.transparent));
    }

    /**
     * 設定荧幕方向
     */
    public void setOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  // 禁止横屏
    }

    @Override
    protected void onResume() {
        super.onResume();
        //設定視圖可見狀態
        setVisibleChanged(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 設定視圖可見狀態
        setVisibleChanged(false);
    }

    // 介面結束時觸發,在onPause之前觸發
    @Override
    public void finish() {
        super.finish();
        ActivityManager.getInstance().removeActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // dialog....
        if (loadingDialog != null) {
            if (loadingDialog.isShowing())
                loadingDialog.dismiss();
            loadingDialog = null;
        }
        // EventBus 取消注册
        eventBusUnregister();
    }

    /**
     * 彈出層的loading需要各自初始化
     */
    protected Dialog loadingDialog;

    /**
     * 初始化加載框
     */
    protected void initLoadingDailog() {
        initLoadDialog(true);
    }

    public Dialog initLoadDialog(boolean cancelAble) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialogUtil.getLoadingDialog(this);
        }
        loadingDialog.setTitle(getString(R.string.common_loading_toast));
        loadingDialog.setCancelable(cancelAble);
        return loadingDialog;
    }

    protected void resetLoadingDialogTitle() {
        LoadingDialogUtil.setText(loadingDialog, getString(R.string.common_loading_toast));
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    public Dialog getLoadingDialog() {
        return loadingDialog;
    }

    /**
     * 獲取程式當前所在介面的名稱
     *
     * @param context 程式當前介面Activity上下文,子類實現者Activity上下文
     * @return
     */
    public String getCurrentActivityName(Activity context) {
        String contextString = context.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////基礎配寘(加載框、獲取當前介面名稱)
////////////////////////////////////////////////////////////////////////////////////////////////防止多次點擊,與點擊空白區域是否自動隱藏鍵盤
    private boolean mAutoHidenInput;// 是否自動隱藏軟鍵盤
    private long mLastClickTime = 0;// 上次點擊觸發時間
    /**
     *防止按鈕多次重複點擊,需要過濾點擊的地方,邏輯執行前調用
     *
     * @return true:間隔太小,需要過濾false:無需過濾
     */
    public boolean checkClickIsFilter(){
        long currentTime = System.currentTimeMillis();// 當前時間
        long intervalTime = currentTime - mLastClickTime;// 間隔時間
//點擊事件時間間隔,距離上一次有效點擊,未超過這個時間的,需要過濾
        long mClickTimeInterval = 800;
        if(intervalTime < mClickTimeInterval){
            return true;
        }
        mLastClickTime = currentTime;
        return false;
    }
    /**
     *設定點擊空白處自動隱藏軟鍵盤
     *
     * @param ifAutoHiden
     */
    public void setAutoHidenInput(boolean ifAutoHiden){
        mAutoHidenInput = ifAutoHiden;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
//點擊空白隱藏軟鍵盤
        if(mAutoHidenInput && ev.getAction()== MotionEvent. ACTION_DOWN){
//獲得當前得到焦點的View,一般情况下就是EditText(特殊情况就是軌跡求或者實體案件會移動焦點)
            View v = getCurrentFocus();
            if(CommonUtils.isEventOnEdit(v,ev)){
                CommonUtils.hideSoftInput(getApplicationContext(),v);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////防止多次點擊,與點擊空白區域是否自動隱藏鍵盤
////////////////////////////////////////////////////////////////////////////////////////////////許可權相關操作
    private PermissionListener mListener;
    private static final int PERMISSION_REQUESTCODE = 100;
    /**
     *申請許可權方法
     *
     * @param permissions
     * @param listener
     */
    public void requestRunPermisssion(String[] permissions,PermissionListener listener){
        mListener = listener;
        List<String> permissionLists = new ArrayList<>();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(String permission:permissions){
                if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager. PERMISSION_GRANTED){
                    permissionLists.add(permission);
                }
            }
            if(!permissionLists.isEmpty()){
                ActivityCompat.requestPermissions(this,permissionLists.toArray(new String[permissionLists.size()]),PERMISSION_REQUESTCODE);
            } else {
//表示全都授權了
                mListener.onGranted(false);
            }
        } else {
            mListener.onGranted(false);
        }
    }
/**
 *許可權申請響應
 *
 * @param requestCode
 * @param permissions
 * @param grantResults
 */
    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch(requestCode){
            case PERMISSION_REQUESTCODE:
                if(grantResults.length > 0){
//存放沒授權的許可權
                List<String> deniedPermissions = new ArrayList<>();
                for(int i = 0;i < grantResults.length;i++){
                    int grantResult = grantResults[i];
                    String permission = permissions[i];
                    if(grantResult!= PackageManager.PERMISSION_GRANTED){
                        deniedPermissions.add(permission);
                    }
                }
                if(deniedPermissions.isEmpty()){
//說明都授權了
                    mListener.onGranted(true);
                } else {
                    showMissingPermissionDialog(deniedPermissions);
                }
            }
            break;
            default:
                break;
        }
    }
    /**
     *顯示提示資訊
     */
    public void showMissingPermissionDialog(final List<String> deniedPermissions){
        AlertDialog. Builder builder = new AlertDialog. Builder(this);
        builder.setTitle("許可權申請");
        builder.setMessage("當前應用缺少必要許可權。請點擊\"設定\"-\"許可權\"-打開所需許可權。");
//拒絕,退出應用
        builder.setNegativeButton("取消",
        new DialogInterface. OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                mListener.onDenied(deniedPermissions);
            }
        });
        builder.setPositiveButton("設定",
        new DialogInterface. OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){
                startAppSettings();
                mListener.onDenied(deniedPermissions);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    /**
     *啟動應用的設定
     */
    private void startAppSettings(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:"+ getPackageName()));
        startActivity(intent);
    }
////////////////////////////////////////////////////////////////////////////////////////////////許可權相關操作
//////////////////////////////////////////////////////////////////////////////////////////////// EventBus相關
/**
 *用於接收EventBus發送的事件,UI線程觸發
 *
 * @param msg
 */
    @Override
    public void onEventMainThread(EventBusMessage msg){
//消息篩檢程式先看看是否是全域事件
        if(filterEventMsg(msg)){
            return;
        }
/**此處做消息過濾,並不是所有的消息都需要響應**/
        if(mEventValid()&& getEventBusManager().isRegisMsg(msg.getMsgType(),isMsgTypeEmpytValid())){
            onEventHandlerMsgUIThread(msg);
        }
    }
    /**
     *消息組類型為null時,消息是否有效
     *
     * @return
     */
    protected boolean isMsgTypeEmpytValid(){
        return true;
    }
    /**
     * EventBus注册、消息處理…
     */
    protected IEventBusManager mEventBusManager;
    @Override
    public void addHandlerMsgType(String msgType){
        if(mEventValid()){
            getEventBusManager().regisEventMsgType(msgType);
        }
    }
/**
 *獲取事件匯流排EventBus
 *
 * @return當前介面的事件匯流排。
 */
    @Override
    public EventBus getEventBus(){
        if(mEventValid()){
            return getEventBusManager().getEventBus();
        }
        return EventBus.getDefault();
    }
/**
 *注册消息組類型
 */
    @Override
    public abstract void registEventHandlerMsgType();
/**
 *普通的消息事件
 *
 * @param msg
 */
    @Override
    public abstract void onEventHandlerMsgUIThread(EventBusMessage msg);
    /**
     *初始化並注册事件
     */
    private void eventBusInit(){
        if(isRegisEventBus()){
            mEventBusManager = new IEventBusManager(this);
            mEventBusManager.eventRegister();
        }
    }
    /**
     *是否注册EventBus,有EventHandler的介面,無需註冊
     *
     * @return
     */
    protected boolean isRegisEventBus(){
        return true;
    }
    /**
     * eventbus是否有效
     *
     * @return
     */
    protected boolean mEventValid(){
        return mEventBusManager!= null;
    }
    /**
     *獲取eventbus管理器
     *
     * @return
     */
    protected IEventBusManager getEventBusManager(){
        return mEventBusManager;
    }
    /**
     *介面銷毀時,解除EventBus事件匯流排中綁定的訂閱服務器
     */
    private void eventBusUnregister(){
        if(mEventValid()){
            mEventBusManager.eventUnregister();
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////// EventBus相關
//////////////////////////////////////////////////////////////////////////////////////////////// EventBus攔截器
    /**
     *攔截消息處理
     */
    private IBaseFilterListener mEventFilterListener;
    /**
     *獲取消息攔截器
     */
    public IBaseFilterListener initEventFilterListener(){
        return null;
    }
    /**
     *消息篩檢程式
     *
     * @param msg
     * @return
     */
    private boolean filterEventMsg(EventBusMessage msg){
        boolean result =! IBaseFilterAction.filterEventMsg(msg,this);
        return result ||(mEventFilterListener!= null && mEventFilterListener.filterEventMsg(msg,this));
    }
    /**
     *設定視圖是否可見
     *
     * @param vis
     */
    private void setVisibleChanged(boolean vis){
        if(mEventFilterListener!= null){
            mEventFilterListener.setVisibleChanged(vis);
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////// EventBus攔截器
    /**
     *我們通過該函數實現獲取特定資源(包括xml控制項、資源等)的id數值
     **/
    public static int getResourceId(Context context,String name){
        Resources resources = context.getResources();
        int id = resources.getIdentifier(name,"id",context.getPackageName());
        return id;
    }
}
