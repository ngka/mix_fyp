package com.router.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/***************************************************************************
 * Description:選擇圖像功能中的自定義布局，將寬度和高度定義為相同
 ***************************************************************************/

public class ChooseSquareLayout extends RelativeLayout {

    public ChooseSquareLayout(Context context) {
        super(context);
    }

    public ChooseSquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChooseSquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // For simple implementation, or internal size is always 0.
        // We depend on the container to specify the layout size of
        // our view. We can't really know what it is since we will be
        // adding and removing different arbitrary views and do not
        // want the layout to change as this happens.
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        // Children are just made to fill our space.
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();
        //高度和寬度壹樣
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
