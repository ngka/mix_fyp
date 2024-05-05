package com.router.ViewModel.ReservationViewModel;

import android.app.Activity;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.Http.exception.ApiException;
import com.router.Core.Http.subscriber.CommonSubscriber;
import com.router.Core.Http.transformer.CommonTransformer;
import com.router.Model.ReportIssuesModel;
import com.router.Model.ReservationInfoModel;
import com.router.Model.ReservationItemModel;
import com.router.utils.ActivityManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/************************************************************
 * Description: 記錄詳情頁面的視圖模型
 ***********************************************************/
public class ReservationInfoViewModel extends ReservationViewModel {

    public static final String TAG = "ReservationInfoViewModel";


    public static final String GET_RESERVATION_INFO_OK = "GET_RESERVATION_INFO_OK";
    public static final String GET_RESERVATION_INFO_FAIL = "GET_RESERVATION_INFO_FAIL";

    // 根據id查詢單條數據
    public void getReportIssuesById(int id) {
        Map<String, Object> map=new HashMap<>();
        map.put("id",id);
        httpService.getReportIssuesById(map)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<ReservationInfoModel>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(ReservationInfoModel result) {
                        postMessageToEventBusHandler(TAG, GET_RESERVATION_INFO_OK, result);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, GET_RESERVATION_INFO_FAIL, e.message, e.code);
                    }
                });
    }


}
