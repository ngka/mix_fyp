package com.router.utils;

import android.content.res.Resources;

/**
 * Description: 像素轉換工具
 */
public class PixelUtil {

    /**
     * dp轉px
     *
     * @param value dp值
     * @return px像素
     */
    public static int dp2px(float value) {
        final float scale = Resources.getSystem().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + 0.5f);
    }

    /**
     * dp轉px
     *
     * @param dpValue dp值
     * @return px像素
     */
    public static int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px轉dp，將像素px轉換為dp
     *
     * @param value 像素px
     * @return 轉換後得到的dp值
     */
    public static int px2dp(float value) {
        final float scale = Resources.getSystem().getDisplayMetrics().densityDpi;
        return (int) ((value * 160) / scale + 0.5f);
    }

    /**
     * sp轉px，將字體大小sp單位轉換為像素單位px
     *
     * @param value 字體大小sp單位
     * @return 轉換後得到的字體像素px
     */
    public static int sp2px(float value) {
        float spvalue = value * Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spvalue + 0.5f);
    }

    /**
     * px轉sp，將像素單位轉換為字體大小sp單位
     *
     * @param value 像素單位px
     * @return 轉換後得到的字體大小sp單位
     */
    public static int px2sp(float value) {
        final float scale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (value / scale + 0.5f);
    }

}
