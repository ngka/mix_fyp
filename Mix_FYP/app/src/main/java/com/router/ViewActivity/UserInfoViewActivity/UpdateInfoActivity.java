package com.router.ViewActivity.UserInfoViewActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.UserModel;
import com.router.MyApplication;
import com.router.R;
import com.router.ViewModel.LoginViewModel.RegisterViewModel;
import com.router.ViewModel.UserInfoViewModel.UpdateInfoViewModel;
import com.router.utils.AndroidBug5497Workaround;
import com.router.utils.CommonUtils;
import com.router.utils.ToastUtil;

/************************************************************
 * Description: 修改用戶信息頁面
 ***********************************************************/
public class UpdateInfoActivity extends BaseSimFragmentActivity<UpdateInfoViewModel> {

    private final String TAG = "UpdateInfoActivity";
    private ImageView imgBack;
    protected EditText etName, etPhone, etEmail;
    protected Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        etName = findViewById(R.id.edt_name);
        etPhone = findViewById(R.id.edt_phone);
        etEmail = findViewById(R.id.edt_email);
        btnUpdate = findViewById(R.id.btn_update);
        imgBack.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_info;
    }

    @Override
    protected void initData() {
        UserModel userModel = MyApplication.getUserModel();
        if (userModel != null) {
            etName.setText(userModel.getNikeName());
            etEmail.setText(userModel.getMail());
            etPhone.setText(userModel.getPhone());
        }
    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(UpdateInfoViewModel.TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (msg.whatAction.equals(UpdateInfoViewModel.update_info_OK)) {
            //修改用戶信息成功
            ToastUtil.getInstance().show(getString(R.string.update_info_success));
            finish();
        } else if (msg.whatAction.equals(UpdateInfoViewModel.update_info_FAIL)) {
            //修改用戶信息失敗
            ToastUtil.getInstance().show(msg.ObjToString());
        }
    }

    @Override
    protected UpdateInfoViewModel bindViewModel() {
        return new UpdateInfoViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.btn_update) {
            updateClick();
        } else if (view.getId() == R.id.img_back) {
            finish();
        }
    }


    protected void updateClick() {
        if (!checkEditEmpty(etEmail) && !checkEditEmpty(etName)
                && !checkEditEmpty(etPhone) && isNetAvailable()) {
            if (loadingDialog == null) {
                initLoadingDailog();
            }
            if (!loadingDialog.isShowing()) loadingDialog.show();
            loadingDialog.setTitle(getString(R.string.update_info_loading));
            String email = etEmail.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            getViewModel().updateInfo(name, phone, email);
        }
    }


    private boolean checkEditEmpty(EditText editText) {
        String text = editText.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtil.getInstance().show(editText.getHint().toString());
            return true;
        }
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CommonUtils.hideSoftInput(getBaseContext(), getCurrentFocus());
    }
}
