package com.router.ViewModel.LoginViewModel;

import android.app.Activity;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Core.Http.exception.ApiException;
import com.router.Core.Http.subscriber.CommonSubscriber;
import com.router.Core.Http.transformer.CommonTransformer;
import com.router.MyApplication;
import com.router.utils.ActivityManager;
import com.router.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/************************************************************
 * Description: 註冊界面的視圖模型
 ***********************************************************/
public class RegisterViewModel extends BaseViewModel<Activity> {

    public final String TAG = "RegisterViewModel";


    public final String REGISTER_OK = "REGISTER_OK";
    public final String REGISTER_FAIL = "REGISTER_FAIL";

    // 註冊操作
    public void register(String name, String email, String code, String address, String pwd, String pwd2) {
        Map<String, String> map = new HashMap<>();
        map.put("nikeName", name);
        map.put("address", address);
        map.put("mail", email);
        map.put("phone", code);
        map.put("password", pwd);
        map.put("passwordCheck", pwd2);
        httpService.register(map)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<String>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(String result) {
                        LogUtils.e(result);
                        postMessageToEventBusHandler(TAG, REGISTER_OK, result);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, REGISTER_FAIL, e.message, e.code);
                    }
                });
    }


}
