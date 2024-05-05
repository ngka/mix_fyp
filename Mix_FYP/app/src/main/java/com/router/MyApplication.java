package com.router;

import android.app.Application;
import android.content.Context;

import com.router.Core.Log.CrashHandler;
import com.router.Model.TokenModel;
import com.router.Model.UserModel;
import com.router.utils.ActivityManager;
import com.router.utils.CommonUtils;
import com.router.utils.LogUtils;

/**
 * application
 */
public class MyApplication extends Application {
    private static UserModel mUserModel;
    private static TokenModel mTokenModel;

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityManager.getInstance().setAppContext(this);
        LogUtils.isDebug = CommonUtils.isApkDebugable(this);
        //TODO 正式包必須删除該段程式碼
        LogUtils.isDebug = false;
        if (!LogUtils.isDebug) {
            realseMethod();
        }
    }

    private void realseMethod() {
        /**  非debug模式下記錄崩潰日誌 **/
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    public static UserModel getUserModel() {
        return mUserModel;
    }

    public static void setUserModel(UserModel userModel) {
        mUserModel = userModel;
    }

    public static TokenModel getTokenModel() {
        return mTokenModel;
    }

    public static void setTokenModel(TokenModel tokenModel) {
        mTokenModel = tokenModel;
    }
}
