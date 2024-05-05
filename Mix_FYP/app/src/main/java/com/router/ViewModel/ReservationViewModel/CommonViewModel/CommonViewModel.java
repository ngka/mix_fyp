package com.router.ViewModel.ReservationViewModel.CommonViewModel;

import android.app.Activity;
import android.text.TextUtils;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.Http.exception.ApiException;
import com.router.Core.Http.subscriber.CommonSubscriber;
import com.router.Core.Http.transformer.CommonTransformer;
import com.router.Model.LoginModel;
import com.router.Model.MessageTypeModel;
import com.router.Model.PhotoModel;
import com.router.Model.ReportIssuesModel;
import com.router.Model.UpLoadModel;
import com.router.Model.UpLoadResultModel;
import com.router.MyApplication;
import com.router.ViewActivity.ReservationViewActivity.CommonViewActivity.CommonManager;
import com.router.utils.ActivityManager;
import com.router.utils.SharePreferenceUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/************************************************************
 * Description: 回報路由器問題界面的視圖模型
 ***********************************************************/
public class CommonViewModel extends BaseViewModel<Activity> {

    public final String TAG = "CommonViewModel";


    public final String GET_MESSGE_TYPE_OK = "GET_MESSGE_TYPE_OK";
    public final String GET_MESSGE_TYPE_FAIL = "GET_MESSGE_TYPE_FAIL";
    //上傳圖片失敗
    public final String UPLOAD_IMAGE_FAIL = "UPLOAD_IMAGE_FAIL";
    //上報成功
    public final String ADD_REPORT_ISSUES_OK = "ADD_REPORT_ISSUES_OK";
    //上報失敗
    public final String ADD_REPORT_ISSUES_FAIL = "ADD_REPORT_ISSUES_FAIL";

    //獲取問題列表
    public void getMessageTypeAll() {
        httpService.getMessageTypeAll()
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<List<MessageTypeModel>>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(List<MessageTypeModel> result) {
                        if (result != null) {
                            postMessageToEventBusHandler(TAG, GET_MESSGE_TYPE_OK, result);
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, GET_MESSGE_TYPE_FAIL, e.message, e.code);
                    }
                });
    }

    //提交
    public void common() {
        List<PhotoModel> uploadFiles = CommonManager.getInstance().getUploadImages();
        if (uploadFiles == null || uploadFiles.size() == 0) {
            addReportIssues(null);
            return;
        }

        List<MultipartBody.Part> list = new ArrayList<>();
        for (int i = 0; i < uploadFiles.size(); i++) {
            PhotoModel model = uploadFiles.get(i);
            File file = new File(model.getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
            list.add(filePart);
        }
        if (list.isEmpty()) {
            postMessageToEventBusHandler(TAG, UPLOAD_IMAGE_FAIL, "上傳圖片失敗");
            return;
        }
        httpService.uploadImages(list)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<UpLoadResultModel>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(UpLoadResultModel result) {
                        if (result != null && !result.getItems().isEmpty()) {
                            addReportIssues(result.getItems());
                        } else {
                            postMessageToEventBusHandler(TAG, UPLOAD_IMAGE_FAIL, "上傳圖片失敗！");
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, UPLOAD_IMAGE_FAIL, e.message, e.code);
                    }
                });
    }

    /**
     * 上報
     */
    public void addReportIssues(List<UpLoadModel> list) {
        Map<String, Object> map = new HashMap<>();
        map.put("address", CommonManager.getInstance().getAddress());
        //客戶編號
        map.put("customerId", MyApplication.getUserModel().getCustomerId());
        //客戶姓名
        map.put("customerName", CommonManager.getInstance().getName());
        //下載速度
        map.put("downloadSpeed", CommonManager.getInstance().getDsd());
        String url = "";
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                UpLoadModel model = list.get(i);
                if (TextUtils.isEmpty(url)) {
                    url = model.getSavePath();
                } else {
                    url = url + "," + model.getSavePath();
                }
            }
        }
        map.put("imgUrl", url);
        map.put("installTime", CommonManager.getInstance().getBuyTime());
        map.put("issuesId", CommonManager.getInstance().getIssuesId());
        map.put("mail", CommonManager.getInstance().getEmail());
        //可用網絡數量
        map.put("networkNum", CommonManager.getInstance().getKywlsl());
        map.put("orders", CommonManager.getInstance().getCode());
        map.put("phone", CommonManager.getInstance().getPhone());
        //問題描述
        map.put("remark", CommonManager.getInstance().getMsg());
        //路由器型號
        map.put("routeModel", CommonManager.getInstance().getLyxh());
        //信號最強
        map.put("strongestSignal", CommonManager.getInstance().getXhzq());
        //上傳速度
        map.put("uploadSpeed", CommonManager.getInstance().getUsd());
        map.put("devicesNum",CommonManager.getInstance().getSbsl());
        httpService.addReportIssues(map)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<ReportIssuesModel>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(ReportIssuesModel result) {
                        if (result != null) {
                            postMessageToEventBusHandler(TAG, ADD_REPORT_ISSUES_OK, result);
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, ADD_REPORT_ISSUES_FAIL, e.message, e.code);
                    }
                });
    }
}
