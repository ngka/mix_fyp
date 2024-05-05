package com.router.Model;

import java.io.Serializable;

public class ReservationItemModel implements Serializable {
    /**
     * {
     * "appointmentAddress": "",
     * "appointmentNum": "0",
     * "appointmentState": 0,
     * "appointmentTime": "",
     * "appointmentUserId": 0,
     * "completionTime": null,
     * "appointmentPerson": null
     * "id": 0,
     * "issuesId": 0,
     * "mail": "",
     * "remark": ""
     * }
     */
    String appointmentAddress;
    String createTime;
    String appointmentNum;
    /**
     * 預約狀態(1申請中，2已處理，3已取消，4已完成)
     */
    int appointmentState;
    String appointmentTime;
    int appointmentUserId;
    int id;
    int issuesId;
    String mail;
    String remark;
    String completionTime;
    String appointmentPerson;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public String getAppointmentPerson() {
        return appointmentPerson;
    }

    public void setAppointmentPerson(String appointmentPerson) {
        this.appointmentPerson = appointmentPerson;
    }

    public String getAppointmentAddress() {
        return appointmentAddress;
    }

    public void setAppointmentAddress(String appointmentAddress) {
        this.appointmentAddress = appointmentAddress;
    }

    public String getAppointmentNum() {
        return appointmentNum;
    }

    public void setAppointmentNum(String appointmentNum) {
        this.appointmentNum = appointmentNum;
    }

    public int getAppointmentState() {
        return appointmentState;
    }

    public void setAppointmentState(int appointmentState) {
        this.appointmentState = appointmentState;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getAppointmentUserId() {
        return appointmentUserId;
    }

    public void setAppointmentUserId(int appointmentUserId) {
        this.appointmentUserId = appointmentUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIssuesId() {
        return issuesId;
    }

    public void setIssuesId(int issuesId) {
        this.issuesId = issuesId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
