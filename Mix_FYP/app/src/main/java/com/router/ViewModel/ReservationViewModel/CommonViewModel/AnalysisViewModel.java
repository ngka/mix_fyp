package com.router.ViewModel.ReservationViewModel.CommonViewModel;

import android.app.Activity;
import android.text.TextUtils;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.Http.exception.ApiException;
import com.router.Core.Http.subscriber.CommonSubscriber;
import com.router.Core.Http.transformer.CommonTransformer;
import com.router.Model.MessageTypeModel;
import com.router.Model.PhotoModel;
import com.router.Model.ReportIssuesModel;
import com.router.Model.ResultTypeModel;
import com.router.Model.UpLoadModel;
import com.router.Model.UpLoadResultModel;
import com.router.MyApplication;
import com.router.ViewActivity.ReservationViewActivity.CommonViewActivity.CommonManager;
import com.router.utils.ActivityManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/************************************************************
 * Description: 上門的視圖模型
 ***********************************************************/
public class AnalysisViewModel extends BaseViewModel<Activity> {

    public final String TAG = "AnalysisViewModel";


    public final String GET_RESULT_TYPE_OK = "GET_RESULT_TYPE_OK";
    public final String GET_RESULT_TYPE_FAIL = "GET_RESULT_TYPE_FAIL";

    //獲取分析所有結果
    public void getResultTypeAll() {
        httpService.getResultTypeAll()
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<List<ResultTypeModel>>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(List<ResultTypeModel> result) {
                        if (result != null) {
                            postMessageToEventBusHandler(TAG, GET_RESULT_TYPE_OK, result);
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, GET_RESULT_TYPE_FAIL, e.message, e.code);
                    }
                });
    }
}
