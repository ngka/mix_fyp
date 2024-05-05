package com.router.Core.Log;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.router.MyApplication;
import com.router.utils.FileUtils;
import com.router.utils.LogUtils;
import com.router.utils.ToastUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: 崩潰異常日誌處理。
 **/
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = CrashHandler.class.getSimpleName();
    private Context mContext;
    private static volatile CrashHandler instance;
    private Thread.UncaughtExceptionHandler defalutHandler;
    private Map<String, String> infos = new HashMap<String, String>();

    private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private CrashHandler() {
    }

    /**
     * 獲得單例
     *
     * @return 單例
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        defalutHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 取得系統預設的UncaughtException處理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        // 設定該CrashHandler為程式的預設處理器
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        boolean hasHandle = handleException(ex);
        //是否處理
        if (!hasHandle && defalutHandler != null) {
            defalutHandler.uncaughtException(thread, ex);
            //如果使用者沒有處理就讓系統預設的異常處理器來處理
        } else {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                LogUtils.e(TAG, "error : " + e.getMessage());
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                ToastUtil.getInstance().showThread("應用異常，5秒後退出", Toast.LENGTH_LONG);
            }
        }.start();
        // 收集設備參數資訊,日誌訊息
// collectDeviceInfo(mContext);
        saveCrashInfoToFile(ex);
        // 保存日誌文件
        return true;
    }

    /**
     * 儲存出錯訊息
     *
     * @param ex 待保存的出錯訊息
     */
    private void saveCrashInfoToFile(Throwable ex) {
        Date date = new Date();
        String strDate = dateFormatter.format(date);
        String strDateTime = dateTimeFormatter.format(date);
        String logFileName = strDate + ".txt";
        String logFilePath = "";
        StringBuffer sb = new StringBuffer();
        FileWriter fw = null;
        PrintWriter pw = null;
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        String result = writer.toString().replace("\n", "\r\n");
        sb.append(result);
        LogUtils.e("CrashLog", result);
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                String savePath = FileUtils.getCrashLogPath(mContext);
                File dir = new File(savePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                logFilePath = savePath + logFileName;
            }
            File logFile = new File(logFilePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            fw = new FileWriter(logFile, true);
            pw = new PrintWriter(fw);
            pw.println("===========================\r\n");
            pw.println("時  間：" + strDateTime + "\r\n");
            pw.println("類  型：" + ex.getClass().getName() + "\r\n");
            pw.println("程  度： 崩潰錯誤\r\n");
            pw.println("詳  情：\r\n" + sb.toString() + "\r\n");
            pw.println("===========================\r\n");
            pw.close();
            fw.close();
        } catch (Exception e) {
            LogUtils.e(TAG, "an error occured while writing file..." + e.getMessage());
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                }
            }
        }
    }
}


