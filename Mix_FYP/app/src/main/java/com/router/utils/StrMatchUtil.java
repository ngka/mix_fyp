package com.router.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/************************************************************
 * Description: 格式驗證
 ***********************************************************/
public class StrMatchUtil {

    public static final String EmailFormat = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";

    //判斷格式是否正確
    public static boolean isMatch(String str, String format) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str.trim())) return false;
        Pattern p = Pattern.compile(format);
        Matcher m = p.matcher(str);
        return m.matches();
    }


}
