package com.router.ViewActivity.UserInfoViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.ChatModel;
import com.router.Model.ReputationRecordModel;
import com.router.Model.UserModel;
import com.router.MyApplication;
import com.router.R;
import com.router.ViewModel.LoginViewModel.LoginViewModel;
import com.router.ViewModel.RobotViewModel.RobotViewModel;
import com.router.ViewModel.UserInfoViewModel.XinyuViewModel;
import com.router.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/************************************************************
 * Description: 信譽頁面
 ***********************************************************/
public class XinyuActivity extends BaseSimFragmentActivity<XinyuViewModel> {

    private final String TAG = "XinyuActivity";
    private ImageView imgBack;
    TextView tvXy;
    private XinyuAdapter adapter;
    private ListView listView;
    private List<ReputationRecordModel> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        tvXy = findViewById(R.id.tv_xy);
        imgBack.setOnClickListener(this);
        listView = findViewById(R.id.listview);
        adapter = new XinyuAdapter(this, models, R.layout.xinyu_list_item);
        listView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xinyu;
    }

    @Override
    protected void initData() {
        UserModel userModel = MyApplication.getUserModel();
        if (userModel != null) {
            tvXy.setText(TextUtils.isEmpty(userModel.getReputationValue()) ? "0" : userModel.getReputationValue());
        }
        getViewModel().getReputationRecord();
    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(XinyuViewModel.TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (TextUtils.equals(msg.whatAction, XinyuViewModel.GET_REPUTATIONRECORD_OK)) {
            if (msg.obj != null) {
                models.addAll((List<ReputationRecordModel>) msg.obj);
                adapter.update(models);
            }
        } else if (TextUtils.equals(msg.whatAction, XinyuViewModel.GET_REPUTATIONRECORD_FAIL)) {
            //失敗
            ToastUtil.getInstance().show(msg.ObjToString());
        }
    }

    @Override
    protected XinyuViewModel bindViewModel() {
        return new XinyuViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        } else if (view.getId() == R.id.tv_update_mine) {
            startActivity(new Intent(this, UpdateInfoActivity.class));
        } else if (view.getId() == R.id.btn_register) {
        }
    }

}
