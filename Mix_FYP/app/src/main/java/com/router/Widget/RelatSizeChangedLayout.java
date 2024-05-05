package com.router.Widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

/************************************************************
 * Description: ...相對佈局可監聽佈局高度發生變化
 ***********************************************************/
public class RelatSizeChangedLayout extends RelativeLayout {

    /**
     * Activity上下文
     */
    private Context context;
    private int width;
    private int height;

    /**
     * 荧幕寬度
     */
    private int screenWidth;

    /**
     * 荧幕高度
     */
    private int screenHeight;

    /**
     * 監聽大小改變
     */
    protected OnSizeChangedListener onSizeChangedListener;

    private boolean mIsIgnoreSmallChanged = false;

    public RelatSizeChangedLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) this.context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.screenWidth = dm.widthPixels;
        this.screenHeight = dm.heightPixels;
    }

    public RelatSizeChangedLayout(Context context, AttributeSet attributeSet, int paramInt) {
        super(context, attributeSet, paramInt);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.width = widthMeasureSpec;
        this.height = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setOnSizeChangedListener(OnSizeChangedListener onSizeChangedListener) {
        this.onSizeChangedListener = onSizeChangedListener;
    }

    public void setmIsIgnoreSmallChanged(boolean mIsIgnoreSmallChanged) {
        this.mIsIgnoreSmallChanged = mIsIgnoreSmallChanged;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (onSizeChangedListener == null) return;
        if (w == oldw && oldw != 0 && oldh != 0 && oldh != h) {
            boolean softkeyShow;
            if ((h >= oldh) || (Math.abs(h - oldh) <= this.screenHeight / 4)) {
                if (!mIsIgnoreSmallChanged && (Math.abs(h - oldh) <= this.screenHeight / 4)) {
                    return;
                }
                softkeyShow = false;
            } else {
                softkeyShow = true;
            }
            this.onSizeChangedListener.onSizeChange(softkeyShow, w, h, oldw, oldh);
            measure(this.width - w + getWidth(), this.height - h + getHeight());
        }
    }

    public interface OnSizeChangedListener {
        /**
         * 高度發生改變時調用
         *
         * @param softkeyShow (如果為跟佈局，佈局高度變小時，說明鍵盤彈起...)
         * @param width       寬
         * @param height      高
         * @param oldwidth    原寬
         * @param oldheight   原高
         */
        void onSizeChange(boolean softkeyShow, int width, int height, int oldwidth, int oldheight);
    }

}
