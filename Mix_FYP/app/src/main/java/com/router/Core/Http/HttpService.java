package com.router.Core.Http;


import com.router.Model.ChatResultModel;
import com.router.Model.FeedbackModel;
import com.router.Model.LoginModel;
import com.router.Model.MessageTypeModel;
import com.router.Model.ReportIssuesModel;
import com.router.Model.ReputationRecordModel;
import com.router.Model.ReservationInfoModel;
import com.router.Model.ReservationItemModel;
import com.router.Model.ResultTypeModel;
import com.router.Model.UpLoadResultModel;
import com.router.Model.UserModel;
import com.router.Model.ChatModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 網絡請求的接口都在這裏
 */
public interface HttpService {
//    //獲取驗證碼
//    @GET("client/auth/captcha")
//    Observable<BaseHttpResult<String>> captcha(@Query("phone") String phone);


    /**
     * 登錄接口
     *
     * @param map "key": 17310054832,
     *            "password": "Aa123456"
     */
    @POST("app/login")
    Observable<BaseHttpResult<LoginModel>> login(@Body Map<String, String> map);

    //註冊接口
    @POST("user/registerUser")
    Observable<BaseHttpResult<String>> register(@Body Map<String, String> map);


    /**
     * 修改用戶信息接口
     *
     * @param map "address": "北京市朝陽區",
     *            "mail": "110@qq.com",
     *            "nikeName": "",
     *            "password": "Aa123456",
     *            "passwordCheck": "Aa123456",
     *            "phone": 18888888888
     */
    @POST("user/editUser")
    Observable<BaseHttpResult<UserModel>> editUser(@Body Map<String, String> map);

    /**
     * 獲取用戶信譽變更記錄
     */
    @GET("user/getReputationRecord")
    Observable<BaseHttpResult<List<ReputationRecordModel>>> getReputationRecord();

    //退出登錄
    @POST("client/auth/logout")
    Observable<BaseHttpResult<String>> logout(@Body Map<String, String> map);

    //分頁查詢歷史數據
    @POST("AppAppointment/getPageInfo")
    Observable<BaseHttpResult<List<ReservationItemModel>>> getPageInfo(@Body Map<String, Object> map);

    //新增預約
    @POST("AppAppointment/add")
    Observable<BaseHttpResult<ReservationItemModel>> addAppointment(@Body Map<String, Object> map);

    //根據id查詢詳細信息
    @POST("ReportIssues/getById")
    Observable<BaseHttpResult<ReservationInfoModel>> getReportIssuesById(@Body Map<String, Object> map);

    //修改預約時間
    @POST("AppAppointment/updateAppointment")
    Observable<BaseHttpResult<Boolean>> updateAppointment(@Body Map<String, Object> map);

    //根據id刪除單條數據
    @POST("AppAppointment/delete")
    Observable<BaseHttpResult<String>> deleteReservationById(@Query("id") int id);

    //修改預約
    @POST("AppAppointment/updateAppointment")
    Observable<BaseHttpResult<Boolean>> editAppointment(@Body Map<String, Object> map);

    //根據id查詢單條數據
    @POST("AppAppointment/getById")
    Observable<BaseHttpResult<ReservationItemModel>> getReservationInfoById(@Query("id") int id);

    //獲取問題列表
    @POST("MessageType/getMessageTypeAll")
    Observable<BaseHttpResult<List<MessageTypeModel>>> getMessageTypeAll();

    //上傳圖片
    @Multipart
    @POST("app/image")
    Observable<BaseHttpResult<UpLoadResultModel>> uploadImages(@Part List<MultipartBody.Part> files);

    //上報
    @POST("ReportIssues/addReportIssues")
    Observable<BaseHttpResult<ReportIssuesModel>> addReportIssues(@Body Map<String, Object> map);

    //獲取分析所有結果
    @POST("ResultType/getResultTypeAll")
    Observable<BaseHttpResult<List<ResultTypeModel>>> getResultTypeAll();

    //新增評分
    @POST("feedback/insertFeedback")
    Observable<BaseHttpResult<FeedbackModel>> insertFeedback(@Body Map<String, Object> map);

    //聊天
    @POST("app/chat")
    Observable<BaseHttpResult<ChatResultModel>> chat(@Body Map<String, Object> map);

}
