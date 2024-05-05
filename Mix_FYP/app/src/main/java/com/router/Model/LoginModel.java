package com.router.Model;

public class LoginModel {
    /**
     * "userInfo":{
     * "id":16,
     * "phone":"15710033734",
     * "mail":"2861166260@qq.com",
     * "address":"武汉",
     * "createTime":"2024-04-02 20:33:28",
     * "loginTime":"2024-04-02 12:43:54",
     * "loginIp":"127.0.0.1",
     * "loginCount":3,
     * "customerId":null,
     * "reputationValue":"100",
     * "status":1
     * },
     * "tokenInfo":{
     * "tokenName":"satoken",
     * "tokenValue":"INjnQtJ5kSL00OIHrwnMtmnVjcW5MqVvTF0StdJtJoNR5YGeqhkkdiUSzvMjaEHh",
     * "isLogin":true,
     * "loginId":"16",
     * "loginType":"login",
     * "tokenTimeout":86400,
     * "sessionTimeout":86400,
     * "tokenSessionTimeout":-2,
     * "tokenActiveTimeout":-1,
     * "loginDevice":"default-device",
     * "tag":null
     * }
     */
    private UserModel userInfo;

    private TokenModel tokenInfo;

    public UserModel getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserModel userInfo) {
        this.userInfo = userInfo;
    }

    public TokenModel getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenModel tokenInfo) {
        this.tokenInfo = tokenInfo;
    }
}
