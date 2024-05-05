package com.router.Core.Base.ViewModel;

/************************************************************
 * Description: ....
 ***********************************************************/
public interface IBaseViewModel {
    void onActivityCreate();

    void onActivityStart();

    void onActivityReStart();

    void onActivityResume();

    void onActivityPause();

    void onActivityStop();

    void onActivityDestroy();
}
