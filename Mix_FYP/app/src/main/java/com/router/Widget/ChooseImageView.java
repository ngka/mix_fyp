package com.router.Widget;

import android.content.Context;
import android.util.AttributeSet;

/***************************************************************************
 * Description:選擇圖像功能中，用於展示本地圖像的ImageView
 ***************************************************************************/

public class ChooseImageView extends ImageRoundedCornerView {
    private OnMeasureListener onMeasureListener;

    public ChooseImageView(Context context) {
        super(context);
    }

    public ChooseImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChooseImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //將圖片測量的大小回調到onMeasureSize()方法中
        if (onMeasureListener != null) {
            onMeasureListener.onMeasureSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public interface OnMeasureListener {
        public void onMeasureSize(int width, int height);
    }
}
