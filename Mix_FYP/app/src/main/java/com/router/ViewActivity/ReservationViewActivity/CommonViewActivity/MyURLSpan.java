package com.router.ViewActivity.ReservationViewActivity.CommonViewActivity;

import android.os.Parcel;
import android.text.TextPaint;
import android.text.style.URLSpan;

import androidx.annotation.NonNull;

public class MyURLSpan extends URLSpan {
    int linkColor;

    public MyURLSpan(String url, int linkColor) {
        super(url);
        this.linkColor = linkColor;
    }

    public MyURLSpan(@NonNull Parcel src) {
        super(src);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        ds.setColor(linkColor);
        ds.setUnderlineText(false);
    }
}
