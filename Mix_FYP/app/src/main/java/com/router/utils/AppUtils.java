package com.router.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.router.R;

public class AppUtils {
    public static String getAppName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            String appName = packageManager.getApplicationLabel(applicationInfo).toString();
            return appName;
        } catch (PackageManager.NameNotFoundException e) {
            return ContextCompat.getString(context, R.string.app_name);
        }
    }
}
