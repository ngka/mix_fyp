package com.router.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Description: Activity 管理器
 * **  初始化此類後
 * **     需調用 {@linkplain #setRootActivity} 設置根級 activity ,否則回退相關函數可能會收到影響
 * **     需調用 {@linkplain #setAppContext} 設置全局上下文
 ************************************************************/
public class ActivityManager {

    /**
     * Activity管理器
     */
    private static volatile ActivityManager instance;

    /**
     * 記錄所有Activity的容器
     */
    private volatile ArrayList<Activity> mActivityList = new ArrayList<Activity>();

    /**
     * 全局上下文
     */
    private Context mApplicationContext;

    /**
     * 根界面 class ,用於插件回退時，判斷臨界值
     */
    private Class mRootActivityClass = null;

    /**
     * 獲取Activity管理器
     *
     * @return
     */
    public static ActivityManager getInstance() {
        if (instance == null) {
            synchronized (ActivityManager.class) {
                if (instance == null) {
                    instance = new ActivityManager();
                }
            }
        }
        return instance;
    }

    /**
     * 構造方法，初始化Activity容器
     */
    private ActivityManager() {
    }

    /**
     * 設置根Activity
     *
     * @param rootActivity
     */
    public void setRootActivity(Class<?> rootActivity) {
        mRootActivityClass = rootActivity;
    }

    /**
     * 設置全局上下文
     *
     * @param context
     */
    public void setAppContext(Context context) {
        this.mApplicationContext = context;
    }

    /**
     * 獲取全局上下文
     */
    public Context getAppContext() {
        return this.mApplicationContext;
    }

    /**
     * 判斷兩個 class 是否是同壹個
     *
     * @param c1
     * @param c2
     * @return
     */
    public boolean classCompare(Class<?> c1, Class<?> c2) {
        if (c1 == null || c2 == null) {
            return false;
        }
        return c1.getName().equals(c2.getName());
    }

    /**
     * 添加Activity到容器中
     *
     * @param activity
     */
    public synchronized void addActivity(Activity activity) {
        if (mActivityList != null && !mActivityList.contains(activity)) {
            mActivityList.add(activity);
        }
    }

    /**
     * 關閉指定界面
     *
     * @param closeA
     */
    public synchronized void closeActivity(Class<?> closeA) {
        List<Activity> list = getActivitys();
        for (Activity activity : list) {
            if (classCompare(activity.getClass(), closeA)) {
                activity.finish();
            }
        }
    }

    /**
     * 結束指定的Activity
     */
    public synchronized void closeActivity(Activity activity) {
        if (activity != null) {
            removeActivity(activity);
            activity.finish();
        }
    }

    /**
     * 移除指定的Activity，界面退出執行onDestory()時調用
     *
     * @param activity
     */
    public synchronized void removeActivity(Activity activity) {
        if (mActivityList != null && mActivityList.contains(activity)) {
            mActivityList.remove(activity);
        }
    }

    /**
     * 得到activity集合棧
     *
     * @return
     */
    public synchronized List<Activity> getActivitys() {
        return getActivitys(true);
    }

    /**
     * 獲取當前Activity棧
     *
     * @param isCopyMemory 是否重新開辟內存
     * @return
     */
    public synchronized List<Activity> getActivitys(boolean isCopyMemory) {
        if (mActivityList == null) {
            mActivityList = new ArrayList<>();
        }
        if (!isCopyMemory) {
            return mActivityList;
        }
        return new ArrayList<>(mActivityList);
    }

    /**
     * 獲取 activity 數量
     *
     * @return
     */
    public int getActivityNum() {
        return mActivityList == null ? 0 : mActivityList.size();
    }

    /**
     * 得到當前位於棧頂的activity
     *
     * @return
     */
    public synchronized Activity getTopActivity() {
        if (mActivityList != null && mActivityList.size() > 0) {
            return mActivityList.get(mActivityList.size() - 1);
        }
        return null;
    }

    /**
     * 得到當前位於棧底的activity
     *
     * @return
     */
    public synchronized Activity getBottomActivity() {
        if (mActivityList != null && mActivityList.size() > 0) {
            return mActivityList.get(0);
        }
        return null;
    }

    /**
     * 判斷指定Activity否存在
     *
     * @param activityClass
     * @return
     */
    public synchronized boolean isExistActivity(Class<?> activityClass) {
        boolean result = false;
        if (activityClass == null) return result;
        List<Activity> list = getActivitys();
        for (Activity activity : list) {
            if (activity != null && classCompare(activity.getClass(), activityClass)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 退出所有Activity
     */
    public synchronized void exitAllActivitys() {
        // 為了避免 並發修改異常（ConcurrentModificationException），不直接修改原數據
        List<Activity> list = getActivitys();
        if (list != null && list.size() > 0) {
            for (Activity activity : list) {
                activity.finish();
                removeActivity(activity);
            }
            // 改為單個移除，不再全部清除
            // mActivityList.clear();
        }
    }

    /**
     * 返回桌面
     *
     * @param context
     */
    public void toHome(Activity context) {
        if (context == null) {
            context = getTopActivity();
        }
        if (context == null) {
            return;
        }
        // 返回桌面
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(home);
    }

    /**
     * 結束幾個Activity
     */
    public synchronized void finishLevelActivity(int level) {
        if (mActivityList != null) {
            if (level >= mActivityList.size()) {
                level = mActivityList.size() - 1;
            }
            for (int i = 0; i < level; i++) {
                int index = mActivityList.size() - 1;
                // if (index <= 0 || mActivityList.get(index) instanceof MainTabActivity) {
                if (index <= 0 || classCompare(mActivityList.get(index).getClass(), mRootActivityClass)) {
                    break;
                }
                closeActivity(mActivityList.get(index));
            }
        }
    }

    /**
     * 主界面為0  level為打開界面級別
     *
     * @return
     */
    public synchronized void mainActivityToLevel(int level) {
        int index = -1;
        if (mActivityList != null) {
            for (int i = 0; i < mActivityList.size(); i++) {
                //  if (mActivityList.get(i) instanceof MainTabActivity) {
                if (classCompare(mActivityList.get(i).getClass(), mRootActivityClass)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                int lastLevel = mActivityList.size() - index - level - 1;
                finishLevelActivity(lastLevel);
            }
        }
    }

    /**
     * 當前界面在Activity列表中的位置
     *
     * @param cla
     * @return
     */
    public synchronized int pageInActivitysIndex(Class cla) {
        int index = -1;
        if (mActivityList != null) {
            for (int i = 0; i < mActivityList.size(); i++) {
                if (classCompare(mActivityList.get(i).getClass(), cla)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    /**
     * 關閉當前Activity之後的Activity
     *
     * @param activity
     */
    public synchronized void closeAfterActivity(Activity activity, int addpageindex) {
        if (activity != null) {
            List<Activity> needCloseAcitivity = new ArrayList<>();
            boolean isClose = false;
            boolean isRootActivityExist = false;
            int index = 0;
            for (int i = 0; i < mActivityList.size(); i++) {
                Activity activity1 = mActivityList.get(i);
                if (isClose && isRootActivityExist && (i > index + addpageindex)) {
                    needCloseAcitivity.add(activity1);
                }
                // if (!isHaveMainActivity && MainTabActivity.class.getName().equals(activity1.getClass().getName())) {
                if (!isRootActivityExist && classCompare(activity1.getClass(), mRootActivityClass)) {
                    isRootActivityExist = true;
                }
                if (isRootActivityExist && !isClose && activity == activity1) {
                    index = i;
                    isClose = true;
                }
            }
            if (needCloseAcitivity.size() > 0) {
                for (Activity activity1 : needCloseAcitivity) {
                    activity1.finish();
                }
            } else {
                finishLevelActivity(1);
            }
        }
    }
}
