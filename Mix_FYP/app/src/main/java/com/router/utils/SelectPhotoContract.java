package com.router.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 選擇照片的協定
 * Input type  : Unit? 不需要傳值
 * Output type : Uri?  選擇完成後的 image uri
 * <p>
 * 區別於 [androidx.activity.result.contract.ActivityResultContracts.GetContent]
 */
public class SelectPhotoContract extends ActivityResultContract<Integer, Uri> {

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Integer unused) {
        return new Intent(Intent.ACTION_PICK).setType("image/*");
    }

    @Override
    public Uri parseResult(int i, @Nullable Intent intent) {
        return intent != null ? intent.getData() : null;
    }
}
