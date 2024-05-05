package com.router.ViewModel.ReservationViewModel;

import android.app.Activity;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.Http.exception.ApiException;
import com.router.Core.Http.subscriber.CommonSubscriber;
import com.router.Core.Http.transformer.CommonTransformer;
import com.router.Model.ReservationItemModel;
import com.router.Model.UserModel;
import com.router.MyApplication;
import com.router.utils.ActivityManager;
import com.router.utils.SharePreferenceUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/************************************************************
 * Description: 記錄列表頁面的視圖模型
 ***********************************************************/
public class ReservationListViewModel extends ReservationViewModel {

    public static final String TAG = "ReservationListViewModel";

    //獲取預約歷史記錄成功
    public static final String GET_PAGE_INFO_OK = "GET_PAGE_INFO_OK";
    //獲取預約歷史記錄失敗
    public static final String GET_PAGE_INFO_FAIL = "update_info_FAIL";
    //刪除預約歷史記錄成功
    public static final String DEL_PAGE_INFO_BY_ID_OK = "DEL_PAGE_INFO_BY_ID_OK";
    //刪除預約歷史記錄失敗
    public static final String DEL_PAGE_INFO_BY_ID_FAIL = "DEL_PAGE_INFO_BY_ID_FAIL";

    // 分頁查詢歷史數據
    public void getPageInfo(int pageNum, int pageSize, boolean isLoadMore) {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        httpService.getPageInfo(map)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<List<ReservationItemModel>>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(List<ReservationItemModel> result) {
                        postMessageToEventBusHandler(TAG, GET_PAGE_INFO_OK, result, isLoadMore);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, GET_PAGE_INFO_FAIL, e.message, isLoadMore);
                    }
                });
    }

    //根據id刪除單條數據
    public void deleteReservationById(int id) {
        httpService.deleteReservationById(id)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<String>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(String result) {
                        postMessageToEventBusHandler(TAG, DEL_PAGE_INFO_BY_ID_OK, result, id);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, DEL_PAGE_INFO_BY_ID_FAIL, e.message);
                    }
                });
    }

}
