package com.router.ViewActivity.ReservationViewActivity.CommonViewActivity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.ReportIssuesModel;
import com.router.Model.ResultTypeModel;
import com.router.R;
import com.router.ViewModel.ReservationViewModel.CommonViewModel.AnalysisViewModel;
import com.router.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Description: 不上門頁面
 ***********************************************************/
public class AnalysisNoActivity extends BaseSimFragmentActivity<AnalysisViewModel> {

    private final String TAG = "AnalysisNoActivity";
    private ImageView imgBack;
    private Button btnResult, btnCommon;
    private List<ResultTypeModel> models = new ArrayList<>();
    private ListView listView;
    private AnalysisNoAdapter adapter;
    private ReportIssuesModel model;

    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        btnResult = findViewById(R.id.btn_result);
        btnCommon = findViewById(R.id.btn_common);
        listView = findViewById(R.id.listview);
        imgBack.setOnClickListener(this);
        btnResult.setOnClickListener(this);
        btnCommon.setOnClickListener(this);
        adapter = new AnalysisNoAdapter(this, models, R.layout.analysisno_item);
        listView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_analysis_no;
    }

    @Override
    protected void initData() {
        model = (ReportIssuesModel) getIntent().getSerializableExtra("data");
        initLoadingDailog();
        loadingDialog.show();
        getViewModel().getResultTypeAll();
    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(getViewModel().TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (TextUtils.equals(msg.whatAction, getViewModel().GET_RESULT_TYPE_FAIL)) {
            ToastUtil.getInstance().show(msg.ObjToString());
        } else if (TextUtils.equals(msg.whatAction, getViewModel().GET_RESULT_TYPE_OK)) {
            if (msg.obj != null) {
                List<ResultTypeModel> list = (List<ResultTypeModel>) msg.obj;
                models.addAll(list);
                adapter.updateData(models);
            }
        }
    }

    @Override
    protected AnalysisViewModel bindViewModel() {
        return new AnalysisViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        } else if (view.getId() == R.id.btn_result) {
            finish();
        } else if (view.getId() == R.id.btn_common) {
            Intent intent = new Intent(this, AnalysisActivity.class);
            intent.putExtra("data", model);
            startActivity(intent);
            finish();
        }
    }

}
