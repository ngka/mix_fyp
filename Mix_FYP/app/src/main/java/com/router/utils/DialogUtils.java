package com.router.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


import com.router.R;

import java.io.Serializable;

public class DialogUtils {

    public interface DialogClickListener {
        void confirm();

        void cancel();
    }

    /**
     * 普通Dialog，(創建壹個單選對話框，創建壹個選擇對話框)
     */
    public static Dialog ShowDialog(Context context, String time, String confirmText, String cancelText, final DialogClickListener dialogClickListener) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog2, null);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView confirm = view.findViewById(R.id.ok);
        cancel.setText(cancelText);
        confirm.setText(confirmText);
        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.ok)).setText(confirmText);

        TextView tv_Toast = view.findViewById(R.id.toast);
        TextView tv_des = view.findViewById(R.id.tv_des);
        ImageView imgCheck = view.findViewById(R.id.img_check);
        String toast = "您已成功預約於<font color=\"#2F6BF4\">[" + time +
                "]</font>";
        setTextHtml(tv_Toast, toast);
        String des = "取消或修改預約:請註意，您將<font color=\"#2F6BF4\">*無法在預約時間前5小時內取消或修改預約*</font>。這樣做將會影您的用戶信譽值，並可能導致信譽值的扣減。";
        setTextHtml(tv_des, des);
        imgCheck.setTag(false);
        imgCheck.setBackgroundResource(R.mipmap.ic_check_1);
        imgCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = (boolean) imgCheck.getTag();
                imgCheck.setBackgroundResource(!checked ? R.mipmap.ic_check_2 : R.mipmap.ic_check_1);
                imgCheck.setTag(!checked);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                if (dialogClickListener != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogClickListener.cancel();
                        }
                    }, 200);
                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                boolean checked = (boolean) imgCheck.getTag();
                if (!checked) {
                    ToastUtil.getInstance().show("請同意提醒！");
                    return;
                }
                dialog.dismiss();
                if (dialogClickListener != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogClickListener.confirm();
                        }
                    }, 200);
                }
            }
        });

        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//橫屏
            lp.width = getScreenHeight(context) / 10 * 8;
        } else {
            lp.width = getScreenWidth(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }
        //        if (!dialog.isShowing()) {
        //        	dialog.show();
        //		}
        //向Activity調用者返回dialog
        return dialog;
    }

    private static void setTextHtml(TextView textView, String content) {
        if (Build.VERSION.SDK_INT >= 24)
            textView.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT));
        else
            textView.setText(Html.fromHtml(content));

    }

    /**
     * 獲取屏幕分辨率高
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm.heightPixels;
    }

    public static Dialog ShowDialog(Context context, String[] items, DialogItemClickListener dialogClickListener) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle);
        dialog.setCanceledOnTouchOutside(true);
        final View view = LayoutInflater.from(context).inflate(R.layout.common_dialog_actionsheet, null);
        dialog.setContentView(view);
        //根據items動態創建
        LinearLayout parent = view.findViewById(R.id.dialogLayout);
        parent.removeAllViews();
        int length = items.length;
        int count = 0;
        int currentCount = 0;
        for (int i = 0; i < length; i++) {
            if (!TextUtils.isEmpty(items[i])) {
                count++;
            }
        }
        for (int i = 0; i < length; i++) {
            if (TextUtils.isEmpty(items[i]))
                continue;
            currentCount++;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
            params.rightMargin = 1;
            params.height = LZPixelUtil.dp2px(44);
            final TextView tv = new TextView(context);
            tv.setTextSize(16);
            tv.setText(items[i]);
            tv.setTextColor(ContextCompat.getColor(context, R.color.black));
            int pad = 10;
            tv.setPadding(pad, 0, pad, 0);
            tv.setSingleLine(true);
            tv.setGravity(Gravity.CENTER);
            tv.setLayoutParams(params);
            if (currentCount == 1) {
                tv.setBackgroundResource(R.drawable.s_common_actionsheet_top);
            } else if (count == currentCount) {
                tv.setBackgroundResource(R.drawable.s_common_actionsheet_bottom);
            } else {
                tv.setBackgroundResource(R.drawable.s_common_actionsheet_middle);
            }
            parent.addView(tv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    dialogClickListener.confirm(tv.getText().toString());
                }
            });
        }
        final TextView textView = view.findViewById(R.id.ok);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                if (dialogClickListener != null) {
                    dialogClickListener.confirm(textView.getText().toString().trim());
                }
            }
        });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.width = getScreenWidth(context);
        mWindow.setGravity(Gravity.BOTTOM);
        //添加動畫
        mWindow.setWindowAnimations(R.style.dialogAnim);
        mWindow.setAttributes(lp);
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }
        //向Activity調用者返回dialog
        return dialog;
    }

    /**
     * 獲取屏幕分辨率寬
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm.widthPixels;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        if (context != null) {
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        } else {
            Resources resources = ActivityManager.getInstance().getAppContext().getResources();
            dm = resources.getDisplayMetrics();
        }
        return dm;
    }

    /**
     * 對話框中條目點擊事件監聽器
     */
    public interface DialogItemClickListener extends Serializable {
        /**
         * 向調用者返回所點擊的內容，可根據點擊的內容，執行不同操作
         *
         * @param result
         */
        void confirm(String result);
    }
}
