package com.router.Core.Base.Activity;

import android.os.Bundle;

import com.router.Core.Permission.PermissionRequestListener;
import com.router.Core.EventBus.EventBusMessage;

/************************************************************
 * Description:…. 乾淨FragmentActivity的基本設定
 ***********************************************************/
public class PureFragmentActivity extends IBaseActivity implements PermissionRequestListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //先初始化ViewModel，父類中有注册eventbus消息組類型，註冊時具體實現介面可能會調用getViewModel()
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void registEventHandlerMsgType() {
    }

    @Override
    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
    }
}
