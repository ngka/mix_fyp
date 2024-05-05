package com.router.Model;

public class TokenModel {
    /**
     * "tokenName": "satoken",
     * "tokenValue": "INjnQtJ5kSL00OIHrwnMtmnVjcW5MqVvTF0StdJtJoNR5YGeqhkkdiUSzvMjaEHh",
     * "isLogin": true,
     * "loginId": "16",
     * "loginType": "login",
     * "tokenTimeout": 86400,
     * "sessionTimeout": 86400,
     * "tokenSessionTimeout": -2,
     * "tokenActiveTimeout": -1,
     * "loginDevice": "default-device",
     * "tag": null
     */
    private String tokenName;

    private String tokenValue;
    private boolean isLogin;
    private String loginId;
    private String loginType;
    private long tokenTimeout;
    private long sessionTimeout;
    private long tokenSessionTimeout;
    private long tokenActiveTimeout;
    private String loginDevice;
    private String tag;

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public long getTokenTimeout() {
        return tokenTimeout;
    }

    public void setTokenTimeout(long tokenTimeout) {
        this.tokenTimeout = tokenTimeout;
    }

    public long getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public long getTokenSessionTimeout() {
        return tokenSessionTimeout;
    }

    public void setTokenSessionTimeout(long tokenSessionTimeout) {
        this.tokenSessionTimeout = tokenSessionTimeout;
    }

    public long getTokenActiveTimeout() {
        return tokenActiveTimeout;
    }

    public void setTokenActiveTimeout(long tokenActiveTimeout) {
        this.tokenActiveTimeout = tokenActiveTimeout;
    }

    public String getLoginDevice() {
        return loginDevice;
    }

    public void setLoginDevice(String loginDevice) {
        this.loginDevice = loginDevice;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
