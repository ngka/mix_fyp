package com.router.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/************************************************************
 * Description:沈浸式狀態欄工具類
 ***********************************************************/

public class StatusBarCompat {

    private static final int INVALID_VAL = -1;
    private static final int COLOR_DEFAULT = Color.parseColor("#20000000");

    public static void compat(Activity activity, int statusColor) {
        compat(activity, statusColor, false);
    }

    public static void compat(Activity activity, int statusColor, boolean isFitsSystemWindows) {
        int type = StatusBarTextColorUtils.StatusBarLightMode(activity);
        if (type == 4 || type == 2 || type == 0) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x開始需要把顏色設置透明，否則導航欄會呈現系統默認的淺灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //兩個 flag 要結合使用，表示讓應用的主體內容占用系統狀態欄的空間
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //導航欄顏色也可以正常設置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentStatus;
                window.setAttributes(attributes);
            }
        } else {
            return;
        }
        StatusBarTextColorUtils.StatusBarLightMode(activity);
        //設置 paddingTop
        //當前手機版本為5.0及以上
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        if (!isFitsSystemWindows) {
            rootView.setPadding(0, CommonUtils.getStatusBarHeight(activity), 0, 0);
        } else {
            rootView.setPadding(0, 0, 0, 0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            int color = COLOR_DEFAULT;
//            if (statusColor != INVALID_VAL) {
//                color = statusColor;
//            }
            activity.getWindow().setStatusBarColor(statusColor);
            return;
        }
        //當前手機版本為4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            int color = COLOR_DEFAULT;
            //根布局添加占位狀態欄
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
//            if (statusColor != INVALID_VAL) {
//                color = statusColor;
//            }
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    CommonUtils.getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(statusColor);
            decorView.addView(statusBarView, lp);
        }
    }

    public static void compat(Activity activity) {
        compat(activity, INVALID_VAL);
    }


}
