package com.router.ViewActivity.LoginViewActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.R;
import com.router.ViewModel.LoginViewModel.RegisterViewModel;
import com.router.utils.CommonUtils;
import com.router.utils.StrMatchUtil;
import com.router.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/************************************************************
 * Description: 註冊頁面
 ***********************************************************/
public class RegisterActivity extends BaseSimFragmentActivity<RegisterViewModel> {

    private final String TAG = "RegisterActivity";
    private LinearLayout llBack;
    // 用戶名、密碼
    protected EditText etName, etEmail, etCode, etAddress,
            etPwd, etPwd2;
    // 註冊
    protected Button btnRegister;
    private ImageView imgEye, imgEye2;
    private CheckBox checkBox;
    private TextView tvPwd1, tvPwd2, tvPwd3, tvPwd4, tvPwdToast;
    private ImageView imgPwd1, imgPwd2, imgPwd3, imgPwd4;
    private LinearLayout llPwd1;
    boolean isSize = false;
    boolean isUpperCase = false;
    boolean isLowerCase = false;
    boolean isNum = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 初始化視圖
    @Override
    protected void initView() {
        llBack = findViewById(R.id.ll_back_login);
        etName = findViewById(R.id.edt_name);
        etEmail = findViewById(R.id.edt_email);
        etCode = findViewById(R.id.edt_code);
        etAddress = findViewById(R.id.edt_address);
        etPwd = findViewById(R.id.edt_pwd);
        etPwd2 = findViewById(R.id.edt_pwd2);
        btnRegister = findViewById(R.id.btn_register);
        checkBox = findViewById(R.id.checkbox);
        imgEye = findViewById(R.id.img_eye);
        imgEye2 = findViewById(R.id.img_eye2);
        tvPwd1 = findViewById(R.id.tv_pwd1);
        imgPwd1 = findViewById(R.id.img_pwd1);
        tvPwd2 = findViewById(R.id.tv_pwd2);
        imgPwd2 = findViewById(R.id.img_pwd2);
        tvPwd3 = findViewById(R.id.tv_pwd3);
        imgPwd3 = findViewById(R.id.img_pwd3);
        tvPwd4 = findViewById(R.id.tv_pwd4);
        imgPwd4 = findViewById(R.id.img_pwd4);
        tvPwdToast = findViewById(R.id.tv_pwd_toast);
        llPwd1 = findViewById(R.id.ll_pwd1);
        llBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        imgEye.setOnClickListener(this);
        imgEye2.setOnClickListener(this);
        updateEditePwdType(false);
        updateEditePwd2Type(false);
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isSize = (s.length() >= 8);
                tvPwd1.setTextColor(ContextCompat.getColor(RegisterActivity.this, isSize ? R.color.text_FF333333 : R.color.text_FF999999));
                imgPwd1.setVisibility(isSize ? View.VISIBLE : View.GONE);
                isUpperCase = containsUpperCase(s.toString());
                tvPwd2.setTextColor(ContextCompat.getColor(RegisterActivity.this, isUpperCase ? R.color.text_FF333333 : R.color.text_FF999999));
                imgPwd2.setVisibility(isUpperCase ? View.VISIBLE : View.GONE);
                isLowerCase = containsLowerCase(s.toString());
                tvPwd3.setTextColor(ContextCompat.getColor(RegisterActivity.this, isLowerCase ? R.color.text_FF333333 : R.color.text_FF999999));
                imgPwd3.setVisibility(isLowerCase ? View.VISIBLE : View.GONE);
                isNum = containsNum(s.toString());
                tvPwd4.setTextColor(ContextCompat.getColor(RegisterActivity.this, isNum ? R.color.text_FF333333 : R.color.text_FF999999));
                imgPwd4.setVisibility(isNum ? View.VISIBLE : View.GONE);
                if (!TextUtils.isEmpty(s.toString())) {
                    if (tvPwdToast.getVisibility() != View.VISIBLE) {
                        tvPwdToast.setVisibility(View.VISIBLE);
                    }
                    if (isSize && isUpperCase && isLowerCase && isNum) {
                        llPwd1.setBackgroundResource(R.drawable.edt_bg);
                        tvPwdToast.setText("密碼已符合最低安全要求");
                        tvPwdToast.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.text_FF28AA91));
                    } else {
                        llPwd1.setBackgroundResource(R.drawable.edt_bg_red);
                        tvPwdToast.setText("密碼不符合最低安全要求");
                        tvPwdToast.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.text_FFFF5257));
                    }
                } else {
                    llPwd1.setBackgroundResource(R.drawable.edt_bg);
                    tvPwdToast.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public boolean containsUpperCase(String input) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public boolean containsLowerCase(String input) {
        Pattern pattern = Pattern.compile("[a-z]");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public boolean containsNum(String input) {
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(getViewModel().TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (msg.whatAction.equals(getViewModel().REGISTER_OK)) {
            //註冊成功
            ToastUtil.getInstance().show(getString(R.string.register_success));
            finish();
        } else if (msg.whatAction.equals(getViewModel().REGISTER_FAIL)) {
            //註冊失敗
            ToastUtil.getInstance().show(msg.ObjToString());
        }
    }

    @Override
    protected RegisterViewModel bindViewModel() {
        return new RegisterViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.btn_register) {
            // 註冊
            registerClick();
        } else if (view.getId() == R.id.img_eye) {
            updateEditePwdType(!mPwdVisible);
        } else if (view.getId() == R.id.img_eye2) {
            updateEditePwd2Type(!mPwd2Visible);
        } else if (view.getId() == R.id.ll_back_login) {
            finish();
        }
    }


    protected void registerClick() {
        if (!checkBox.isChecked()) {
            ToastUtil.getInstance().show(getString(R.string.register_agree));
            return;
        }
        if ((checkEditEmpty(etName) || checkEditEmpty(etEmail) || checkEditEmpty(etCode)
                || checkEditEmpty(etAddress) || checkEditEmpty(etPwd) || checkEditEmpty(etPwd2)) && isNetAvailable()) {
            return;
        }
        String code = etCode.getText().toString().trim();
        if (code.startsWith("1")) {
            ToastUtil.getInstance().show(getString(R.string.register_phone_error));
            return;
        }
        String email = etEmail.getText().toString().trim();
        if (!StrMatchUtil.isMatch(email, StrMatchUtil.EmailFormat)) {
            ToastUtil.getInstance().show(getString(R.string.register_email_error));
            return;
        }
        String pwd = etPwd.getText().toString();
        String pwd2 = etPwd2.getText().toString();
        if (!TextUtils.equals(pwd, pwd2)) {
            ToastUtil.getInstance().show(getString(R.string.register_pwd_error));
            return;
        }
        if (loadingDialog == null) {
            initLoadingDailog();
        }
        if (!loadingDialog.isShowing()) loadingDialog.show();
        loadingDialog.setTitle(getString(R.string.register_loading_title));
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        // 執行登錄
        getViewModel().register(name, email, code, address, pwd, pwd2);

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

    private boolean mPwdVisible = false;
    private boolean mPwd2Visible = false;

    private void updateEditePwdType(boolean pwdVisible) {
        this.mPwdVisible = pwdVisible;
        etPwd.setTransformationMethod(mPwdVisible ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        etPwd.setSelection(etPwd.getText().length());
        imgEye.setBackgroundResource(mPwdVisible ? R.mipmap.pwd_eye : R.mipmap.pwd_eye2);
    }

    private void updateEditePwd2Type(boolean pwdVisible) {
        this.mPwd2Visible = pwdVisible;
        etPwd2.setTransformationMethod(mPwd2Visible ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        etPwd2.setSelection(etPwd2.getText().length());
        imgEye2.setBackgroundResource(mPwd2Visible ? R.mipmap.pwd_eye : R.mipmap.pwd_eye2);
    }
}
