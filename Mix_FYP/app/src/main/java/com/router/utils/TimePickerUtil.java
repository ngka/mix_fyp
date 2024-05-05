package com.router.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.router.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 時間選擇器
 */
public class TimePickerUtil {
    private final BottomSheetDialog bottomDialog;
    private final NumberPicker year;
    private final NumberPicker month;
    private final NumberPicker day;

    /**
     * 時間選擇回調
     *
     * @author DengLS
     * @date 2023/01/12
     */
    public interface TimePickerCallback {
        /**
         * 回調
         *
         * @param year  年
         * @param month 月
         * @param day   日
         */
        void setDate(int year, int month, int day);
    }

    public TimePickerUtil(Context context, String title, TimePickerCallback callback) {
        // 設置時間選擇器的布局以及彈窗的高度
        bottomDialog = getBottomDialog(context, R.layout.view_time_picker, dpToPx(context, 350));
        Calendar calendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(title)) {
            // 設置標題
            ((TextView) bottomDialog.findViewById(R.id.title)).setText(title);
        }
        // 年
        year = (NumberPicker) bottomDialog.findViewById(R.id.year);
        int yearNow = calendar.get(Calendar.YEAR);
        year.setMinValue(yearNow - 100);
        year.setMaxValue(yearNow + 100);
        year.setValue(yearNow);

        // 月
        month = (NumberPicker) bottomDialog.findViewById(R.id.month);
        String[] monthNum = new String[12];
        for (int i = 0; i < 12; i++) {
            monthNum[i] = (i + 1) + "月";
        }
        month.setMinValue(1);
        month.setMaxValue(monthNum.length);
        month.setDisplayedValues(monthNum);
        month.setValue(calendar.get(Calendar.MONTH));

        // 日
        day = (NumberPicker) bottomDialog.findViewById(R.id.day);
        day.setMinValue(1);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String[] newDays = getNewDays(days);
        day.setMaxValue(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        day.setDisplayedValues(newDays);
        day.setValue(calendar.get(Calendar.DATE));

        // 年份和月份更改時對應的天數需要更改
        year.setOnValueChangedListener((picker, oldVal, newVal) -> {
            updateNumberOfDays();
            day.setValue(calendar.get(Calendar.DATE));
        });
        month.setOnValueChangedListener((picker, oldVal, newVal) -> {
            updateNumberOfDays();
            day.setValue(calendar.get(Calendar.DATE));
        });

        // 取消按鈕和確定按鈕事件綁定
        View cancel = bottomDialog.findViewById(R.id.cancel);
        View ok = bottomDialog.findViewById(R.id.ok);
        if (cancel != null) {
            cancel.setOnClickListener(v -> bottomDialog.dismiss());
        }
        if (ok != null) {
            ok.setOnClickListener(v -> {
                bottomDialog.dismiss();
                callback.setDate(year.getValue(), month.getValue(), day.getValue());
            });
        }
    }

    /**
     * 底部彈出框
     *
     * @param id     彈窗中的布局
     * @param height 彈窗高度
     */
    private BottomSheetDialog getBottomDialog(Context context, Integer id, int height) {
        BottomSheetDialog bottomSheet = new BottomSheetDialog(context);
        // 設置對框框中的布局
        bottomSheet.setContentView(id);
        // 設置點擊外部是否可以取消
        bottomSheet.setCancelable(true);
        FrameLayout bottom = (FrameLayout) bottomSheet.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (bottom != null) {
            // 設置背景透明顏色
            bottom.setBackgroundResource(R.color.transparent);
            // 修改彈窗的高度
            ViewGroup.LayoutParams layoutParams = bottom.getLayoutParams();
            layoutParams.height = height;
            bottom.setLayoutParams(layoutParams);
        }
        return bottomSheet;
    }

    /**
     * dp轉px
     */
    private int dpToPx(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5);
    }

    /**
     * 顯示
     */
    public void show() {
        bottomDialog.show();
    }

    /**
     * 設置選中年份
     *
     * @param yearValue 年
     */
    public void setYearValue(int yearValue) {
        year.setValue(yearValue);
        updateNumberOfDays();
    }

    /**
     * 設置選中月份
     *
     * @param monthValue 月
     */
    public void setMonthValue(int monthValue) {
        month.setValue(monthValue);
        updateNumberOfDays();
    }

    /**
     * 設置選中天數
     *
     * @param dayValue 天
     */
    public void setDayValue(int dayValue) {
        day.setValue(dayValue);
    }

    /**
     * 更新天數
     */
    private void updateNumberOfDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year.getValue());
        calendar.set(Calendar.MONTH, (month.getValue() - 1));
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int date = calendar.get(Calendar.DATE);
        day.setMaxValue(date);
        day.setDisplayedValues(getNewDays(date));
    }

    /**
     * 格式化天數
     *
     * @param days 天數
     * @return {@link String[]}
     */
    private String[] getNewDays(int days) {
        List<String> dayList = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            dayList.add((i + 1) + "日");
        }
        return dayList.toArray(new String[dayList.size()]);
    }

}
