package com.router.ViewModel.MainViewModel;

import android.app.Activity;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Core.Http.exception.ApiException;
import com.router.Core.Http.subscriber.CommonSubscriber;
import com.router.Core.Http.transformer.CommonTransformer;
import com.router.MyApplication;

import java.util.HashMap;
import java.util.Map;

/************************************************************
 * Description: 首頁界面的視圖模型
 ***********************************************************/
public class MainViewModel extends BaseViewModel<Activity> {

    public final String TAG = "MainViewModel";

}
