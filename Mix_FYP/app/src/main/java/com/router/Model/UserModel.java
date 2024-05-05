package com.router.Model;

public class UserModel {
    /**
     * "id": 16,
     * "phone": "15710033734",
     * "mail": "2861166260@qq.com",
     * "address": "武漢",
     * "createTime": "2024-04-02 20:33:28",
     * "loginTime": "2024-04-02 12:43:54",
     * "loginIp": "127.0.0.1",
     * "loginCount": 3,
     * "customerId": null,
     * "reputationValue": "100",
     * "status": 1
     */
    private int id;
    private String nikeName;
    private String phone;
    private String mail;
    private String address;
    private String createTime;
    private String loginTime;
    private String loginIp;
    private long loginCount;
    private String customerId;
    private String reputationValue;
    private int status;

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(long loginCount) {
        this.loginCount = loginCount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getReputationValue() {
        return reputationValue;
    }

    public void setReputationValue(String reputationValue) {
        this.reputationValue = reputationValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
