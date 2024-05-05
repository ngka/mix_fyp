package com.router.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.content.ContextCompat;

/************************************************************
 * Description: .... 網絡狀態 判斷
 ***********************************************************/
public class NetStateUtil {

    /**
     * 沒有網絡
     */
    public static final int NETWORN_NONE = 0;

    /**
     * WIIF
     */
    public static final int NETWORN_WIFI = 1;

    /**
     * 數據流量
     */
    public static final int NETWORN_MOBILE = 2;

    /**
     * 當前網絡狀態
     **/
    private static int mNetWorkState = -1;

    /**
     * 獲取當前網絡狀態
     *
     * @return
     */
    public static int getNetWorkState() {
        return getNetWorkState(true);
    }

    /**
     * 獲取當前網絡狀態
     *
     * @param isNoneNetReset 無網絡狀態時，是否重置
     * @return
     */
    public static int getNetWorkState(boolean isNoneNetReset) {
        if (mNetWorkState == -1 || (isNoneNetReset && mNetWorkState == NetStateUtil.NETWORN_NONE)) {
            mNetWorkState = NetStateUtil.getNetWorkState(ActivityManager.getInstance().getAppContext());
        }
        return mNetWorkState;
    }

    /**
     * 設置當前網絡狀態
     *
     * @param netState
     */
    public static void setNetWorkState(int netState) {
        mNetWorkState = netState;
    }

    /**
     * 設置是否有網
     *
     * @return
     */
    public static boolean isNetAvailable() {
        return getNetWorkState() > 0;
    }

    /**
     * 判斷 是 wifi網絡 還是 手機網絡 還是 沒有網絡
     *
     * @param context
     * @return
     */
    public static int getNetWorkState(Context context) {
        long startTime = System.currentTimeMillis();
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo networkinfo = connManager.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isAvailable()) {
                if (networkinfo.getState() == NetworkInfo.State.CONNECTED) {
                    if (networkinfo.getType() == ConnectivityManager.TYPE_WIFI) {

                        long endTime = System.currentTimeMillis();
                        netLogPrint(NETWORN_WIFI, ">>> " + (endTime - startTime));

                        return NETWORN_WIFI;
                    } else if (networkinfo.getType() == ConnectivityManager.TYPE_MOBILE) {

                        long endTime = System.currentTimeMillis();
                        netLogPrint(NETWORN_MOBILE, ">>> " + (endTime - startTime));

                        return NETWORN_MOBILE;
                    }
                }
            }
        }

        long endTime = System.currentTimeMillis();
        netLogPrint(NETWORN_NONE, ">>> " + (endTime - startTime));

        return NETWORN_NONE;
    }

    /**
     * 判斷是否存在訪問網絡權限
     *
     * @param context
     */
    public static void isNetYx(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET));
        // pm.checkPermission("android.permission.INTERNET", context.getPackageName()));
        if (permission) {
            ToastUtil.getInstance().show("存在訪問網絡的權限");
            LogUtils.e("aaaaa", "存在訪問網絡的權限");
        } else {
            ToastUtil.getInstance().show("不存在訪問網絡的權限");
            LogUtils.e("aaaaa", "不存在訪問網絡的權限");
        }
    }

    /**
     * 判斷 網絡是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetActive(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo networkinfo = connManager.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    private static void netLogPrint(int netstate, String msg) {

        msg += "---->>>";

        switch (netstate) {
            case 0:
                msg += "無網絡 ";
            case -1:
                msg += "未獲取網絡狀態 ";
                break;
            case 1:
                msg += "WIFI ";
                break;
            case 2:
                msg += "數據流量 ";
                break;
        }

        LogUtils.e("NetStateUtilTAG", msg);
    }
}
