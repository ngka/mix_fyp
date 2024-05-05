package com.router.ViewModel.UserInfoViewModel;

import android.app.Activity;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.Http.exception.ApiException;
import com.router.Core.Http.subscriber.CommonSubscriber;
import com.router.Core.Http.transformer.CommonTransformer;
import com.router.Model.UserModel;
import com.router.MyApplication;
import com.router.utils.ActivityManager;
import com.router.utils.SharePreferenceUtils;

import java.util.HashMap;
import java.util.Map;

/************************************************************
 * Description: 修改用戶信息界面的視圖模型
 ***********************************************************/
public class UpdateInfoViewModel extends BaseViewModel<Activity> {

    public static final String TAG = "UpdateInfoViewModel";


    public static final String update_info_OK = "update_info_OK";
    public static final String update_info_FAIL = "update_info_FAIL";
    public static final String update_info_pwd_OK = "update_info_pwd_OK";
    public static final String update_info_pwd_FAIL = "update_info_pwd_FAIL";
    // 修改用戶信息操作
    public void updateInfo(String name, String phone, String email) {
        Map<String, String> map = new HashMap<>();
        map.put("nikeName", name);
        map.put("phone", phone);
        map.put("mail", email);
        httpService.editUser(map)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<UserModel>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(UserModel result) {
                        //更新成功，修改本地數據
                        MyApplication.setUserModel(result);
                        String pwd = SharePreferenceUtils.getSharePreference(getView()).getLoginPwd();
                        SharePreferenceUtils.getSharePreference(getView()).setLogin(phone, pwd);
                        postMessageToEventBusHandler(TAG, update_info_OK, null);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, update_info_FAIL, e.message, e.code);
                    }
                });
    }

    // 修改用戶密碼操作
    public void updatePwd(String password, String passwordCheck) {
        String phone = SharePreferenceUtils.getSharePreference(getView()).getLoginPhone();
        Map<String, String> map = new HashMap<>();
        map.put("password", password);
        map.put("phone", phone);
        map.put("passwordCheck", passwordCheck);
        httpService.editUser(map)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<UserModel>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(UserModel result) {
                        //更新成功，修改本地數據
                        MyApplication.setUserModel(result);
                        SharePreferenceUtils.getSharePreference(getView()).setLogin(phone, password);
                        postMessageToEventBusHandler(TAG, update_info_pwd_OK, null);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, update_info_pwd_FAIL, e.message, e.code);
                    }
                });
    }


}
