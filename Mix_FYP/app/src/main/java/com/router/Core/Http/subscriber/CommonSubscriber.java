package com.router.Core.Http.subscriber;

import android.content.Context;

import com.router.Core.Http.BaseSubscriber;
import com.router.Core.Http.exception.ApiException;
import com.router.utils.LogUtils;
import com.router.utils.NetworkUtil;


/**
 * CommonSubscriber
 */
public abstract class CommonSubscriber<T> extends BaseSubscriber<T> {

    private Context context;

    public CommonSubscriber(Context context) {
        this.context = context;
    }

    private static final String TAG = "CommonSubscriber";

    @Override
    public void onStart() {

        if (!NetworkUtil.isNetworkAvailable(context)) {
            LogUtils.e(TAG, "網絡不可用");
        } else {
            LogUtils.e(TAG, "網絡可用");
        }
    }


    @Override
    protected void onError(ApiException e) {
        LogUtils.e(TAG, "錯誤信息為 " + "code:" + e.code + "   message:" + e.message);
    }

    @Override
    public void onCompleted() {
        LogUtils.e(TAG, "成功了");
    }

}
