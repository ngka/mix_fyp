package com.router.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static String getCrashLogPath(Context context) {
        String rootPath = null;
        File file = context.getExternalFilesDir(AppUtils.getAppName(context));
        if (file != null) {
            rootPath = file.getPath();
            if (!TextUtils.isEmpty(rootPath)) {
                rootPath += "/";
            }
            rootPath += "CrashLog/";
        }
        return rootPath;
    }

    /**
     * 根據uri獲取絕對路徑
     *
     * @param context
     * @param contentUris
     * @return
     */
    public static String getRealPathFromUri(Context context, Uri contentUris) {
        if (null == contentUris)
            return null;
        String scheme = contentUris.getScheme();
        if (scheme == null) {
            return contentUris.getPath();
        }
        if (DocumentsContract.isDocumentUri(context, contentUris)) {
            // DocumentProvider
            // ExternalStorageProvider
            if (isExternalStorageDocument(contentUris)) {
                final String docId = DocumentsContract.getDocumentId(contentUris);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(contentUris)) {
                final String id = DocumentsContract.getDocumentId(contentUris);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(contentUris)) {
                final String docId = DocumentsContract.getDocumentId(contentUris);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else {
                    contentUri = contentUris;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(contentUris.getScheme())) {
            String path = null;
            if (isFileproviderPhotosUri(contentUris) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //TODO 判斷是否是7.0 Fileprovider處理生成的Uri,暫時兼容
                path = getFPUriToPath(context, contentUris);
            } else if (isGooglePhotosUri(contentUris)) {//判斷是否是google相冊圖片
                path = contentUris.getLastPathSegment();
            } else if (isGooglePlayPhotosUri(contentUris)) {//判斷是否是Google相冊圖片
                path = getImageUrlWithAuthority(context, contentUris);
            }
            if (TextUtils.isEmpty(path)) {
                //其他類似於media這樣的圖片，和android4.4以下獲取圖片path方法類似
                path = getDataColumn(context, contentUris, null, null);
            }
            return path;
        }
        // File
        else if ("file".equalsIgnoreCase(contentUris.getScheme())) {
            return contentUris.getPath();
        }
        return null;
    }

    /**
     * 獲取FileProvider path
     * author zx
     * version 1.0
     * since 2018/5/4  .
     */
    public static String getFPUriToPath(Context context, Uri uri) {
        try {
            List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
            if (packs != null) {
                String fileProviderClassName = FileProvider.class.getName();
                for (PackageInfo pack : packs) {
                    ProviderInfo[] providers = pack.providers;
                    if (providers != null) {
                        for (ProviderInfo provider : providers) {
                            if (uri.getAuthority().equals(provider.authority)) {
                                if (provider.name.equalsIgnoreCase(fileProviderClassName)) {
                                    Class<FileProvider> fileProviderClass = FileProvider.class;
                                    try {
                                        Method getPathStrategy = fileProviderClass.getDeclaredMethod("getPathStrategy", Context.class, String.class);
                                        getPathStrategy.setAccessible(true);
                                        Object invoke = getPathStrategy.invoke(null, context, uri.getAuthority());
                                        if (invoke != null) {
                                            String PathStrategyStringClass = FileProvider.class.getName() + "$PathStrategy";
                                            Class<?> PathStrategy = Class.forName(PathStrategyStringClass);
                                            Method getFileForUri = PathStrategy.getDeclaredMethod("getFileForUri", Uri.class);
                                            getFileForUri.setAccessible(true);
                                            Object invoke1 = getFileForUri.invoke(invoke, uri);
                                            if (invoke1 instanceof File) {
                                                String filePath = ((File) invoke1).getAbsolutePath();
                                                return filePath;
                                            }
                                        }
                                    } catch (NoSuchMethodException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Google相冊圖片獲取路徑
     **/
    public static String getImageUrlWithAuthority(Context context, Uri uri) {
        InputStream is = null;
        if (uri.getAuthority() != null) {
            try {
                is = context.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                return writeToTempImageAndGetPathUri(context, bmp).toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 將圖片流讀取出來保存到手機本地相冊中
     **/
    public static Uri writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    /**
     * 判斷是否是Google相冊的圖片，類似於content://com.google.android.apps.photos.content/...
     **/
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 判斷是否是Google相冊的圖片，類似於content://com.google.android.apps.photos.contentprovider/0/1/mediakey:/local%3A821abd2f-9f8c-4931-bbe9-a975d1f5fabc/ORIGINAL/NONE/1075342619
     **/
    public static boolean isGooglePlayPhotosUri(Uri uri) {
        return "com.google.android.apps.photos.contentprovider".equals(uri.getAuthority());
    }

    /**
     * 判斷是否是7.0 Fileprovider處理生成的Uri，暫時兼容
     *
     * @param uri
     * @return
     */
    public static boolean isFileproviderPhotosUri(Uri uri) {
        return uri.getAuthority().contains("fileprovider");
    }

    /**
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
                return cursor.getString(column_index);
            }
        } catch (Exception e) {
            return uri.getPath();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     * @return
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     * @return
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     * @return
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public List<String> getFiles(Activity activity, Intent intent) {
        List<String> files = new ArrayList<>();
        if (intent != null) {
            if (intent.getAction() == Intent.ACTION_VIEW) {
                Uri uri = intent.getData();
                if (uri != null) {
                    String path = getRealPathFromUri(activity, uri);
                    if (!TextUtils.isEmpty(path)) {
                        files.add(path);
                    }
                }
                return files;
            }
            if (intent.getClipData() != null) {
                ClipData clipData = intent.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    String path = getRealPathFromUri(activity, uri);
                    if (!TextUtils.isEmpty(path)) {
                        files.add(path);
                    }
                }
            }
        }
        return files;
    }

}
