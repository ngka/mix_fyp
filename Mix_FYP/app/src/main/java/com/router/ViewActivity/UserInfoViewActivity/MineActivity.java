package com.router.ViewActivity.UserInfoViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.UserModel;
import com.router.MyApplication;
import com.router.R;
import com.router.ViewActivity.LoginViewActivity.UpdatePwdActivity;
import com.router.ViewModel.LoginViewModel.LoginViewModel;
import com.router.ViewModel.UserInfoViewModel.UpdateInfoViewModel;

/************************************************************
 * Description: 個人中心頁面
 ***********************************************************/
public class MineActivity extends BaseSimFragmentActivity<LoginViewModel> {

    private final String TAG = "MineActivity";
    private ImageView imgBack;
    private TextView tvUpdateInfo, tvName, tvCode, tvPhone, tvEmail, tvXy;
    private LinearLayout llXinyu, llUpdatePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        tvUpdateInfo = findViewById(R.id.tv_update_mine);
        llXinyu = findViewById(R.id.ll_mine_xinyu);
        llUpdatePwd = findViewById(R.id.ll_mine_update_pwd);
        tvName = findViewById(R.id.tv_name);
        tvCode = findViewById(R.id.tv_code);
        tvPhone = findViewById(R.id.tv_phone);
        tvEmail = findViewById(R.id.tv_email);
        tvXy = findViewById(R.id.tv_xy);
        imgBack.setOnClickListener(this);
        tvUpdateInfo.setOnClickListener(this);
        llXinyu.setOnClickListener(this);
        llUpdatePwd.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initData() {
        UserModel model = MyApplication.getUserModel();
        if (model != null) {
            tvName.setText(model.getNikeName());
            tvCode.setText(model.getCustomerId());
            tvPhone.setText(model.getPhone());
            tvEmail.setText(model.getMail());
            tvXy.setText(TextUtils.isEmpty(model.getReputationValue()) ? "0" : model.getReputationValue());
        }
    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(UpdateInfoViewModel.TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (msg.whatAction.equals(UpdateInfoViewModel.update_info_OK)) {
            //修改用戶信息成功更新數據
            initData();
        }
    }

    @Override
    protected LoginViewModel bindViewModel() {
        return new LoginViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        } else if (view.getId() == R.id.tv_update_mine) {
            //修改用戶信息
            startActivity(new Intent(this, UpdateInfoActivity.class));
        } else if (view.getId() == R.id.ll_mine_xinyu) {
            //信譽詳情
            startActivity(new Intent(this, XinyuActivity.class));
        } else if (view.getId() == R.id.ll_mine_update_pwd) {
            //修改密碼
            startActivity(new Intent(this, UpdatePwdActivity.class));
        }
    }

}
