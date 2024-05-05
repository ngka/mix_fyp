package com.router.utils;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.router.R;


/**
 * Description: Dialog工具類
 */
public class LoadingDialogUtil {

    private static final String TAG = "LoadingDialogUtil";

    /**
     * 數據加載 dialog
     *
     * @param context
     * @return
     */
    public static Dialog getLoadingDialog(Activity context) {
        if (context == null) {
            return null;
        }
        return getLoadingDialog(context, PixelUtil.dp2px(100), PixelUtil.dp2px(100));
    }

    public static Dialog getLoadingDialog(Activity context, int width, int height) {
        if (context == null) {
            return null;
        }
        final Dialog dialog = new Dialog(context, R.style.DialogStyle);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.common_dialog_progress);
        RelativeLayout loadingDialogLayout = dialog.findViewById(R.id.dialogRoot);
        dialog.setContentView(loadingDialogLayout);
        // 設置dialog params
        ViewGroup.LayoutParams loadingDialogParams = loadingDialogLayout.getLayoutParams();
        loadingDialogParams.width = width;
        loadingDialogParams.height = height;
        loadingDialogLayout.setLayoutParams(loadingDialogParams);
        // 獲取文字
        TextView titleText = (TextView) dialog.findViewById(R.id.dialogText);
        titleText.setText(ActivityManager.getInstance().getAppContext().
                getString(R.string.common_loading_toast));
        return dialog;
    }

    public static void setText(Dialog mDialog, String text) {
        if (mDialog != null) {
            if (TextUtils.isEmpty(text)) {
                text = ActivityManager.getInstance().getAppContext().
                        getString(R.string.common_loading_toast);
            }
            TextView titleText = mDialog.findViewById(R.id.dialogText);
            titleText.setText(text);
        }
    }

    public static void setRlWidth(Dialog mDialog, int width) {
        if (mDialog != null && width > 0) {
            RelativeLayout rl = (RelativeLayout) mDialog.findViewById(R.id.dialogRoot);
            ViewGroup.LayoutParams loadingDialogParams = rl.getLayoutParams();
            loadingDialogParams.width = width;
            rl.setLayoutParams(loadingDialogParams);
        }
    }

    public static void setWidth(Dialog mDialog, int width) {
        if (mDialog != null) {
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = width;
            window.setAttributes(lp);
        }
    }
}
