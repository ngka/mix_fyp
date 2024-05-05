package com.router.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SharePreferenceUtils {
    private static final String FILE_NAME = "RouterShare";

    SharedPreferences sp;

    SharedPreferences.Editor editor;

    private static SharePreferenceUtils sharePreferenceUtils;

    public static String key_Phone = "phone";
    public static String key_Pwd = "pwd";

    private SharePreferenceUtils(Context context) {
        sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static SharePreferenceUtils getSharePreference(Context context) {
        if (sharePreferenceUtils == null) {
            sharePreferenceUtils = new SharePreferenceUtils(context);
        }
        return sharePreferenceUtils;
    }

    /**
     * 存儲
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    /**
     * 獲取保存的數據
     */
    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else {
            return sp.getString(key, null);
        }
    }

    public void setLogin(String phone, String pwd) {
        put(key_Phone, phone);
        put(key_Pwd, pwd);
    }

    public String getLoginPhone() {
        String phone = (String) get(key_Phone, "");
        return phone;
    }

    public String getLoginPwd() {
        String pwd = (String) get(key_Pwd, "");
        return pwd;
    }

    /**
     * 移除某個key值已經對應的值
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有數據
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 查詢某個key是否存在
     */
    public Boolean contain(String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有的鍵值對
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

}
