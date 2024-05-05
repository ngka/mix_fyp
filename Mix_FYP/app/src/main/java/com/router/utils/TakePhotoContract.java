package com.router.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;

/**
 * 拍照协定
 * Input type  : Unit? 不需要传值
 * Output type : Uri?  拍照完成后的uri
 */
public class TakePhotoContract extends ActivityResultContract<Integer, Uri> {
    private Uri uri = null;

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Integer integer) {
        String mimeType = "image/jpeg";
        String fileName = System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 及以上获取图片uri
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
            uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            // Android 9 及以下获取图片uri
            uri = FileProvider.getUriForFile(
                    context, context.getPackageName() + ".fileprovider",
                    new File(context.getExternalCacheDir().getAbsolutePath(), fileName)
            );
        }
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, uri);
    }

    @Override
    public Uri parseResult(int i, @Nullable Intent intent) {
        return uri;
    }
}
