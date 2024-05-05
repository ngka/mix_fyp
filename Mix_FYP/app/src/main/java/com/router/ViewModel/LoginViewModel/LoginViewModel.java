package com.router.ViewModel.LoginViewModel;

import android.app.Activity;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Core.Http.exception.ApiException;
import com.router.Core.Http.subscriber.CommonSubscriber;
import com.router.Core.Http.transformer.CommonTransformer;
import com.router.Model.LoginModel;
import com.router.MyApplication;
import com.router.utils.ActivityManager;
import com.router.utils.SharePreferenceUtils;

import java.util.HashMap;
import java.util.Map;

/************************************************************
 * Description: 登錄界面的視圖模型
 ***********************************************************/
public class LoginViewModel extends BaseViewModel<Activity> {

    public final String TAG = "LoginViewModel";


    public final String LOGIN_OK = "LOGIN_OK";
    public final String LOGIN_FAIL = "LOGIN_FAIL";

    // 登錄操作
    public void login(String username, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("key", username);
        map.put("password", password);
        httpService.login(map)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<LoginModel>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(LoginModel result) {
                        if (result != null) {
                            //登錄成功保存登錄數據
                            MyApplication.setUserModel(result.getUserInfo());
                            MyApplication.setTokenModel(result.getTokenInfo());
                            SharePreferenceUtils.getSharePreference(ActivityManager.getInstance().getAppContext()).setLogin(username, password);
                            postMessageToEventBusHandler(TAG, LOGIN_OK, null);
                        } else {
                            postMessageToEventBusHandler(TAG, LOGIN_FAIL, "登錄失敗");
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, LOGIN_FAIL, e.message, e.code);
                    }
                });
    }


}
