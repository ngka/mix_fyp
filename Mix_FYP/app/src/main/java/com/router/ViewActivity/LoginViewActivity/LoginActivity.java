package com.router.ViewActivity.LoginViewActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Core.Permission.PermissionListener;
import com.router.R;
import com.router.ViewActivity.MainViewActivity.MainActivity;
import com.router.ViewModel.LoginViewModel.LoginViewModel;
import com.router.utils.CommonUtils;
import com.router.utils.DialogUtils;
import com.router.utils.LoadingDialogUtil;
import com.router.utils.SharePreferenceUtils;
import com.router.utils.StrMatchUtil;
import com.router.utils.ToastUtil;

import java.util.List;

/************************************************************
 * Description: 登錄頁面
 ***********************************************************/
public class LoginActivity extends BaseSimFragmentActivity<LoginViewModel> {

    private final String TAG = "LoginActivity";
    // 手機號、密碼
    protected EditText etPhone, etPwd;
    private ImageView imgEye;
    //忘記密碼
    TextView tvForgetPwd;
    // 登錄、註冊、無法登錄按鈕
    protected Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // 初始化視圖
    @Override
    protected void initView() {
        requestRunPermisssion(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted(boolean isfirst) {
                nextOperation();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    finish();
                } else {
                    nextOperation();
                }
            }
        });

    }

    private void nextOperation() {
        etPhone = findViewById(R.id.edt_name);
        etPwd = findViewById(R.id.edt_pwd);
        imgEye = findViewById(R.id.img_eye);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        tvForgetPwd = findViewById(R.id.tv_forget_pwd);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvForgetPwd.setOnClickListener(this);
        imgEye.setOnClickListener(this);
        updateEditePwdType(mPwdVisible);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initState() {
    }

    @Override
    protected void initData() {
        String phone = SharePreferenceUtils.getSharePreference(this).getLoginPhone();
        String pwd = SharePreferenceUtils.getSharePreference(this).getLoginPwd();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)) {
            return;
        }
        etPhone.setText(phone);
        etPwd.setText(pwd);
    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(getViewModel().TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        //--------------- 登錄成功
        if (msg.whatAction.equals(getViewModel().LOGIN_OK)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (msg.whatAction.equals(getViewModel().LOGIN_FAIL)) {
            //登錄失敗
            ToastUtil.getInstance().show(msg.ObjToString());
        }
    }

    @Override
    protected LoginViewModel bindViewModel() {
        return new LoginViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.btn_login) {
            // 登錄
            loginClick();
        } else if (view.getId() == R.id.img_eye) {
            updateEditePwdType(!mPwdVisible);
        } else if (view.getId() == R.id.btn_register) {
            startActivity(new Intent(this, RegisterActivity.class));
        } else if (view.getId() == R.id.tv_forget_pwd) {
//            // 忘記密碼

        }
    }


    protected void loginClick() {
        if (!checkPhonePwdEmpty() && isNetAvailable()) {
            if (!StrMatchUtil.isMatch(etPhone.getText().toString().trim(), StrMatchUtil.EmailFormat)) {
                ToastUtil.getInstance().show(getString(R.string.register_email_error));
                return;
            }
            if (loadingDialog == null) {
                initLoadingDailog();
            }
            LoadingDialogUtil.setText(loadingDialog, getString(R.string.login_loading_title));
            if (!loadingDialog.isShowing()) loadingDialog.show();
            login();
        }
    }


    // 登錄
    protected void login() {
        loadingDialog.setTitle("正在登錄...");
        String phone = etPhone.getText().toString().trim();
        String password = etPwd.getText().toString();
        // 執行登錄
        getViewModel().login(phone, password);
    }

    // 判斷用戶名密碼是否為空
    protected boolean checkPhonePwdEmpty() {
        if (checkPhoneEmpty()) {
            return true;
        }
        if (TextUtils.isEmpty(etPwd.getText().toString().trim())) {
            ToastUtil.getInstance().show(etPwd.getHint().toString());
            return true;
        }
        return false;
    }

    private boolean checkPhoneEmpty() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.getInstance().show(etPhone.getHint().toString());
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

    private boolean mPwdVisible = false;

    private void updateEditePwdType(boolean pwdVisible) {
        this.mPwdVisible = pwdVisible;
        etPwd.setTransformationMethod(mPwdVisible ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        etPwd.setSelection(etPwd.getText().length());
        imgEye.setBackgroundResource(mPwdVisible ? R.mipmap.pwd_eye : R.mipmap.pwd_eye2);
    }
}
