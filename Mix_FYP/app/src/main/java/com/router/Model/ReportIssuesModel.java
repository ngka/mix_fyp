package com.router.Model;

import java.io.Serializable;
import java.util.List;

public class ReportIssuesModel implements Serializable {
    /**
     * {
     * "id": 17,
     * "customerId": "202404020003",
     * "customerName": "测试",
     * "phone": "15710033734",
     * "mail": "2861166260@qq.com",
     * "address": "武汉",
     * "createTime": "2024-04-10 23:21:45",
     * "orders": "1234",
     * "routeModel": "1234",
     * "installTime": "2024-3-10",
     * "remark": "测试数据",
     * "isVisit":false,
     * "issuesId": 5,
     * "uploadSpeed": "1",
     * "downloadSpeed": "1",
     * "strongestSignal": "1",
     * "networkNum": 1,
     * "devicesNum": null,
     * "imgUrlList": [
     * {
     * "imgUrl": "https://308w35g695.yicp.fun/upload/image/2024/04-10/1712762504439316017191.jpg"
     * },
     * {
     * "imgUrl": "https://308w35g695.yicp.fun/upload/image/2024/04-10/1712762504439316017191.jpg"
     * }
     * ]
     * }
     */
    private long id;
    private String customerId;
    private String customerName;
    private String phone;
    private String mail;
    private String address;
    private String createTime;
    private String orders;
    private String routeModel;
    private String installTime;
    private String remark;
    private int issuesId;
    private String uploadSpeed;
    private String downloadSpeed;
    private String strongestSignal;
    private int networkNum;
    private String devicesNum;
    private List<ImageUrl> imgUrlList;
    /**
     * 是否上門（true要，false不用）
     */
    private boolean isVisit = false;

    public boolean isVisit() {
        return isVisit;
    }

    public void setVisit(boolean visit) {
        isVisit = visit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getRouteModel() {
        return routeModel;
    }

    public void setRouteModel(String routeModel) {
        this.routeModel = routeModel;
    }

    public String getInstallTime() {
        return installTime;
    }

    public void setInstallTime(String installTime) {
        this.installTime = installTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIssuesId() {
        return issuesId;
    }

    public void setIssuesId(int issuesId) {
        this.issuesId = issuesId;
    }

    public String getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(String uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

    public String getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(String downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public String getStrongestSignal() {
        return strongestSignal;
    }

    public void setStrongestSignal(String strongestSignal) {
        this.strongestSignal = strongestSignal;
    }

    public int getNetworkNum() {
        return networkNum;
    }

    public void setNetworkNum(int networkNum) {
        this.networkNum = networkNum;
    }

    public String getDevicesNum() {
        return devicesNum;
    }

    public void setDevicesNum(String devicesNum) {
        this.devicesNum = devicesNum;
    }

    public List<ImageUrl> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<ImageUrl> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

}

