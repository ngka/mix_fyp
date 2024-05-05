package com.router.ViewActivity.ReservationViewActivity.CommonViewActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.ReportIssuesModel;
import com.router.R;
import com.router.ViewActivity.ReservationViewActivity.ReservationViewActivity.ReservationActivity;
import com.router.ViewModel.LoginViewModel.LoginViewModel;

/************************************************************
 * Description: 上門頁面
 ***********************************************************/
public class AnalysisActivity extends BaseSimFragmentActivity<LoginViewModel> {

    private final String TAG = "AnalysisActivity";
    private ImageView imgBack;
    private Button btnCancel, btnCommon;
    private ReportIssuesModel model;

    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        btnCancel = findViewById(R.id.btn_cancel);
        btnCommon = findViewById(R.id.btn_common);
        imgBack.setOnClickListener(this);
        btnCommon.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_analysis;
    }

    @Override
    protected void initData() {
        model = (ReportIssuesModel) getIntent().getSerializableExtra("data");
    }

    @Override
    public void registEventHandlerMsgType() {
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
    }

    @Override
    protected LoginViewModel bindViewModel() {
        return new LoginViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        } else if (view.getId() == R.id.btn_cancel) {
            finish();
        } else if (view.getId() == R.id.btn_common) {
            Intent intent = new Intent(this, ReservationActivity.class);
            intent.putExtra("data", model);
            startActivity(intent);
            finish();
        }
    }

}
