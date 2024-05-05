package com.router.ViewActivity.MainViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.R;
import com.router.ViewActivity.ReservationViewActivity.CommonViewActivity.Common1Activity;
import com.router.ViewActivity.ReservationViewActivity.ReservationViewActivity.ReservationListActivity;
import com.router.ViewActivity.RobotViewActivity.RobotActivity;
import com.router.ViewActivity.UserInfoViewActivity.MineActivity;
import com.router.ViewModel.MainViewModel.MainViewModel;

/************************************************************
 * Description: 首頁頁面
 ***********************************************************/
public class MainActivity extends BaseSimFragmentActivity<MainViewModel> {

    private final String TAG = "MainActivity";
    private LinearLayout llTitleRight, llItem1, llItem2, llItem3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 初始化視圖
    @Override
    protected void initView() {
        llTitleRight = findViewById(R.id.ll_person);
        llItem1 = findViewById(R.id.ll_main_item_1);
        llItem2 = findViewById(R.id.ll_main_item_2);
        llItem3 = findViewById(R.id.ll_main_item_3);
        llTitleRight.setOnClickListener(this);
        llItem1.setOnClickListener(this);
        llItem2.setOnClickListener(this);
        llItem3.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void registEventHandlerMsgType() {
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
    }

    @Override
    protected MainViewModel bindViewModel() {
        return new MainViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.ll_person) {
            startActivity(new Intent(this, MineActivity.class));
        } else if (view.getId() == R.id.ll_main_item_1) {
            //回報
            startActivity(new Intent(this, Common1Activity.class));
//            startActivity(new Intent(this, AnalysisNoActivity.class));
        } else if (view.getId() == R.id.ll_main_item_2) {
            //歷史記錄
            startActivity(new Intent(this, ReservationListActivity.class));
        } else if (view.getId() == R.id.ll_main_item_3) {
            //機器人
            startActivity(new Intent(this, RobotActivity.class));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
