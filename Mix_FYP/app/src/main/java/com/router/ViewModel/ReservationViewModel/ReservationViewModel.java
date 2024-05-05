package com.router.ViewModel.ReservationViewModel;

import android.app.Activity;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.Http.exception.ApiException;
import com.router.Core.Http.subscriber.CommonSubscriber;
import com.router.Core.Http.transformer.CommonTransformer;
import com.router.Model.ReservationItemModel;
import com.router.utils.ActivityManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/************************************************************
 * Description: 記錄基礎視圖模型
 ***********************************************************/
public class ReservationViewModel extends BaseViewModel<Activity> {
    public static final String TAG = "ReservationViewModel";
    public final String ADD_APPOINTMENT_OK = "ADD_APPOINTMENT_OK";
    public final String ADD_APPOINTMENT_FAIL = "ADD_APPOINTMENT_FAIL";
    public static final String UPDATE_APPOINTMENT_OK = "UPDATE_APPOINTMENT_OK";
    public static final String UPDATE_APPOINTMENT_FAIL = "UPDATE_APPOINTMENT_FAIL";
    public static final String EDIT_APPOINTMENT_OK = "EDIT_APPOINTMENT_OK";
    public static final String EDIT_APPOINTMENT_FAIL = "EDIT_APPOINTMENT_FAIL";

    /**
     * 新增預約
     * {
     * "appointmentAddress": "",
     * "appointmentTime": "",
     * "issuesId": 0,
     * "mail": "",
     * "remark": ""
     * }
     */
    public void addAppointment(String mail, long issuesId, String appointmentTime, String appointmentAddress, String remark) {
        Map<String, Object> map = new HashMap<>();
        map.put("appointmentAddress", appointmentAddress);
        map.put("appointmentTime", appointmentTime);
        map.put("issuesId", issuesId);
        map.put("mail", mail);
        map.put("remark", remark);
        httpService.addAppointment(map)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<ReservationItemModel>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(ReservationItemModel result) {
                        postMessageToEventBusHandler(TAG, ADD_APPOINTMENT_OK, result);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, ADD_APPOINTMENT_FAIL, e.message, e.code);
                    }
                });
    }

    /**
     * 新增預約
     * {
     * "appointmentAddress": "",
     * "appointmentTime": "",
     * "issuesId": 0,
     * "mail": "",
     * "remark": ""
     * }
     */
    public void updateAppointment(int id, String mail, int issuesId, String appointmentTime, String appointmentAddress, String remark) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("appointmentAddress", appointmentAddress);
        map.put("appointmentTime", appointmentTime);
        map.put("issuesId", issuesId);
        map.put("mail", mail);
        map.put("remark", remark);
        httpService.updateAppointment(map)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<Boolean>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(Boolean result) {
                        if (result) {
                            postMessageToEventBusHandler(TAG, UPDATE_APPOINTMENT_OK, result);
                        } else {
                            postMessageToEventBusHandler(TAG, UPDATE_APPOINTMENT_FAIL, "修改失败！");
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, UPDATE_APPOINTMENT_FAIL, e.message, e.code);
                    }
                });
    }

    /**
     * 新增預約
     * {
     * "appointmentAddress": "",
     * "appointmentTime": "",
     * "issuesId": 0,
     * "mail": "",
     * "remark": ""
     * }
     */
    public void editAppointment(int id, String mail, int issuesId, String appointmentTime, String appointmentAddress, String remark) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("appointmentAddress", appointmentAddress);
        //預約狀態(1申請中，2已處理，3已取消，4已完成)
        map.put("appointmentState", 3);
        map.put("appointmentTime", appointmentTime);
        map.put("issuesId", issuesId);
        map.put("mail", mail);
        map.put("remark", remark);
        httpService.editAppointment(map)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<Boolean>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(Boolean result) {
                        if (result) {
                            postMessageToEventBusHandler(TAG, EDIT_APPOINTMENT_OK, result);
                        } else {
                            postMessageToEventBusHandler(TAG, EDIT_APPOINTMENT_FAIL, "取消失败！");
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, EDIT_APPOINTMENT_FAIL, e.message, e.code);
                    }
                });
    }

    /**
     * 預約狀態(1申請中，2已處理，3已取消，4已完成)
     */
    public static String getState(int state) {
        String stateS;
        switch (state) {
            case 1:
                stateS = "申請中";
                break;
            case 2:
                stateS = "已處理";
                break;
            case 3:
                stateS = "已取消";
                break;
            default:
                stateS = "已完成";
                break;
        }
        return stateS;
    }
}
