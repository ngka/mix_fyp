package com.router.Model;

import java.io.Serializable;
import java.util.List;

public class ReservationInfoModel implements Serializable {
    /**
     * {
     * "appointmentAddress": "",
     * "appointmentNum": 0,
     * "appointmentState": 0,
     * "appointmentTime": "",
     * "completionTime": "",
     * "createTime": "",
     * "devicesNum": 0,
     * "downloadSpeed": "",
     * "imgUrlList": [
     * {
     * "imgUrl": ""
     * }
     * ],
     * "networkNum": 0,
     * "remark": "",
     * "routeModel": "",
     * "solution": "",
     * "strongestSignal": "",
     * "uploadSpeed": ""
     * }
     */
    /**
     * 預約狀態(1申請中，2已處理，3已取消，4已完成)
     */
    int appointmentState;
    //地址
    private String appointmentAddress;
    //申請編號
    private long appointmentNum;
    //預約日期
    private String appointmentTime;
    //完成時間
    private String completionTime;
    //創建日期
    private String createTime;
    //設備數量
    private int devicesNum;
    //下載速度
    private String downloadSpeed;
    private List<ImageUrl> imgUrlList;
    //可用網絡數量
    private int networkNum;
    //問題描述
    private String remark;
    //路由器型號
    private String routeModel;
    //解決方式（1上門，2自主解決）
    private String solution;
    //信號最強
    private String strongestSignal;
    //上傳速度
    private String uploadSpeed;

    public int getAppointmentState() {
        return appointmentState;
    }

    public void setAppointmentState(int appointmentState) {
        this.appointmentState = appointmentState;
    }

    public String getAppointmentAddress() {
        return appointmentAddress;
    }

    public void setAppointmentAddress(String appointmentAddress) {
        this.appointmentAddress = appointmentAddress;
    }

    public long getAppointmentNum() {
        return appointmentNum;
    }

    public void setAppointmentNum(long appointmentNum) {
        this.appointmentNum = appointmentNum;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDevicesNum() {
        return devicesNum;
    }

    public void setDevicesNum(int devicesNum) {
        this.devicesNum = devicesNum;
    }

    public String getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(String downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public List<ImageUrl> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<ImageUrl> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    public int getNetworkNum() {
        return networkNum;
    }

    public void setNetworkNum(int networkNum) {
        this.networkNum = networkNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRouteModel() {
        return routeModel;
    }

    public void setRouteModel(String routeModel) {
        this.routeModel = routeModel;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getStrongestSignal() {
        return strongestSignal;
    }

    public void setStrongestSignal(String strongestSignal) {
        this.strongestSignal = strongestSignal;
    }

    public String getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(String uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }
}
