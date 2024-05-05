package com.router.Core.Base.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.router.Core.Base.ViewModel.BaseViewModel;

/***************************************************************************
 * Description:簡單的基礎類FragmentActivity
 ***************************************************************************/
public abstract class BaseSimFragmentActivity<T extends BaseViewModel<Activity>> extends IBaseActivity implements View.OnClickListener {
    /**
     * 中間層ViewModel
     */
    protected T mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//執行ViewModel創建操作
        mViewModel = bindViewModel();
//對使中間層ViewModel持有當前Activity的弱引用。
        mViewModel.attachView(this);
//先初始化ViewModel,父類中有注册eventbus消息組類型,註冊時具體實現介面可能會調用getViewModel()
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected abstract void otherViewClick(View view);

    /**
     * 點擊的事件的統一的處理
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                otherViewClick(view);
                break;
        }
    }

    /**
     * 獲取程式當前所在介面的名稱
     *
     * @param context 程式當前介面Activity上下文,子類實現者Activity上下文
     * @return
     */
    public String getCurrentActivityName(Activity context) {
        String contextString = context.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }

    public T getViewModel() {
        return mViewModel;
    }

    /**
     * 創建
     *
     * @return子類Fragment中聲明的ViewModel。
     */
    protected abstract T bindViewModel();

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.onActivityStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mViewModel.onActivityReStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.onActivityResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewModel.onActivityPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.onActivityStop();
    }

    @Override
    public void onDestroy() {
//銷毀時執行,解除Fragment與ViewModel之間的綁定關係
        if (mViewModel != null) {
            mViewModel.onActivityDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onTrimMemory(int level) {
        if (mViewModel != null) {
            mViewModel.onTrimMemory(level);
        }
        super.onTrimMemory(level);
    }
}