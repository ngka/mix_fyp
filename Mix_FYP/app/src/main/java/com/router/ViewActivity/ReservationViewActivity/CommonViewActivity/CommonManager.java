package com.router.ViewActivity.ReservationViewActivity.CommonViewActivity;

import com.router.Model.PhotoModel;

import java.util.ArrayList;
import java.util.List;

public class CommonManager {
    /**
     * 提交信息管理器
     */
    private static volatile CommonManager instance;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String code;
    //路由型號
    private String lyxh;
    private String buyTime;

    private String msg;
    private List<PhotoModel> uploadImages = new ArrayList<>();
    private int issuesId;
    //下載速度
    private String dsd;
    //上傳速度
    private String usd;
    //信號最強
    private String xhzq;
    //可用網絡數量
    private String kywlsl;
    //設備數量
    private String sbsl;

    /**
     * 獲取提交信息管理器
     *
     * @return
     */
    public static CommonManager getInstance() {
        if (instance == null) {
            synchronized (CommonManager.class) {
                if (instance == null) {
                    instance = new CommonManager();
                }
            }
        }
        return instance;
    }

    /**
     * 構造方法，初始化提交信息容器
     */
    private CommonManager() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLyxh() {
        return lyxh;
    }

    public void setLyxh(String lyxh) {
        this.lyxh = lyxh;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }


    public void setCommon1(String name, String phone, String email, String address, String code, String lyxh, String buyTime) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.code = code;
        this.lyxh = lyxh;
        this.buyTime = buyTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<PhotoModel> getUploadImages() {
        return uploadImages;
    }

    public void setUploadImages(List<PhotoModel> uploadImages) {
        this.uploadImages = uploadImages;
    }

    public void setCommon2(String msg, List<PhotoModel> uploadImages) {
        this.msg = msg;
        if (uploadImages != null) {
            this.uploadImages = new ArrayList<>(uploadImages);
        } else {
            this.uploadImages = new ArrayList<>();
        }
    }

    public int getIssuesId() {
        return issuesId;
    }

    public void setIssuesId(int issuesId) {
        this.issuesId = issuesId;
    }

    public String getDsd() {
        return dsd;
    }

    public void setDsd(String dsd) {
        this.dsd = dsd;
    }

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getXhzq() {
        return xhzq;
    }

    public void setXhzq(String xhzq) {
        this.xhzq = xhzq;
    }

    public String getKywlsl() {
        return kywlsl;
    }

    public void setKywlsl(String kywlsl) {
        this.kywlsl = kywlsl;
    }

    public String getSbsl() {
        return sbsl;
    }

    public void setSbsl(String sbsl) {
        this.sbsl = sbsl;
    }

    public void setCommon4(String dsd, String usd, String xhzq, String kywlsl, String sbsl) {
        this.dsd = dsd;
        this.usd = usd;
        this.xhzq = xhzq;
        this.kywlsl = kywlsl;
        this.sbsl = sbsl;
    }
}
