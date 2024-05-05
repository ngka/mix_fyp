package com.router.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.router.R;

/************************************************************
 * Description: .... 圓角圖片
 ***********************************************************/
public class ImageRoundedCornerView extends ImageMaskedView {
    private static final int DEFAULT_CORNER_RADIUS = 8;
    private int cornerRadius = DEFAULT_CORNER_RADIUS;

    public ImageRoundedCornerView(Context paramContext) {
        super(paramContext);
    }

    public ImageRoundedCornerView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        attributeAnalyse(paramContext, paramAttributeSet);
    }

    public ImageRoundedCornerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        attributeAnalyse(paramContext, paramAttributeSet);
    }

    private void attributeAnalyse(Context context, AttributeSet attributs) {
        TypedArray a = context.obtainStyledAttributes(attributs, R.styleable.RoundedCornersImage);
        this.cornerRadius = a.getInteger(R.styleable.RoundedCornersImage_corner_radius, DEFAULT_CORNER_RADIUS);
        a.recycle();
    }

    public Bitmap createMask() {
        int i = getWidth();
        int j = getHeight();
        Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
        Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint(1);
        localPaint.setColor(-16777216);
        float f1 = getWidth();
        float f2 = getHeight();
        RectF localRectF = new RectF(0.0F, 0.0F, f1, f2);
        float f3 = this.cornerRadius;
        float f4 = this.cornerRadius;
        localCanvas.drawRoundRect(localRectF, f3, f4, localPaint);
        return localBitmap;
    }
}
