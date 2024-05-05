package com.router.Core.Base.Adapter;

import android.view.View;

public interface ItemClickListener<T> {
    void onClick(View view, T model);
}
