package com.router.ViewModel.ReservationViewModel;

import android.app.Activity;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.Http.exception.ApiException;
import com.router.Core.Http.subscriber.CommonSubscriber;
import com.router.Core.Http.transformer.CommonTransformer;
import com.router.Model.FeedbackModel;
import com.router.Model.ReportIssuesModel;
import com.router.Model.ReservationItemModel;
import com.router.utils.ActivityManager;

import java.util.HashMap;
import java.util.Map;

/************************************************************
 * Description: 反饋視圖模型
 ***********************************************************/
public class FeedbookViewModel extends BaseViewModel<Activity> {
    public static final String TAG = "FeedbookViewModel";
    public final String INSERT_FEEDBACK_OK = "INSERT_FEEDBACK_OK";
    public final String INSERT_FEEDBACK_FAIL = "INSERT_FEEDBACK_FAIL";

    /**
     * 新增評分
     * {
     * "oid": 0,
     * "other": "",
     * "scoreFive": "",
     * "scoreFour": "",
     * "scoreOne": "",
     * "scoreThree": "",
     * "scoreTwo": ""
     * }
     */
    public void insertFeedback(String oid, String scoreOne, String scoreTwo, String scoreThree, String scoreFour, String scoreFive, String other) {
        Map<String, Object> map = new HashMap<>();
        map.put("oid", oid);
        map.put("scoreOne", scoreOne);
        map.put("scoreTwo", scoreTwo);
        map.put("scoreThree", scoreThree);
        map.put("scoreFour", scoreFour);
        map.put("scoreFive", scoreFive);
        map.put("other", other);
        httpService.insertFeedback(map)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<FeedbackModel>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(FeedbackModel result) {
                        postMessageToEventBusHandler(TAG, INSERT_FEEDBACK_OK, result);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, INSERT_FEEDBACK_FAIL, e.message, e.code);
                    }
                });
    }

}
