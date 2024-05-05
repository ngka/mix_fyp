package com.router.ViewModel.UserInfoViewModel;

import android.app.Activity;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.Http.exception.ApiException;
import com.router.Core.Http.subscriber.CommonSubscriber;
import com.router.Core.Http.transformer.CommonTransformer;
import com.router.Model.ReputationRecordModel;
import com.router.Model.UserModel;
import com.router.MyApplication;
import com.router.utils.ActivityManager;
import com.router.utils.SharePreferenceUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/************************************************************
 * Description: 信譽界面的視圖模型
 ***********************************************************/
public class XinyuViewModel extends BaseViewModel<Activity> {

    public static final String TAG = "XinyuViewModel";


    public static final String GET_REPUTATIONRECORD_OK = "GET_REPUTATIONRECORD_OK";
    public static final String GET_REPUTATIONRECORD_FAIL = "GET_REPUTATIONRECORD_FAIL";

    // 獲取用戶信譽變更記錄
    public void getReputationRecord() {
        httpService.getReputationRecord()
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<List<ReputationRecordModel>>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(List<ReputationRecordModel> result) {
                        postMessageToEventBusHandler(TAG, GET_REPUTATIONRECORD_OK, result);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, GET_REPUTATIONRECORD_FAIL, e.message, e.code);
                    }
                });
    }

}
