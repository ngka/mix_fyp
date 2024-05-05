package com.router.ViewActivity.LoginViewActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.R;
import com.router.ViewModel.LoginViewModel.RegisterViewModel;
import com.router.ViewModel.UserInfoViewModel.UpdateInfoViewModel;
import com.router.utils.CommonUtils;
import com.router.utils.SharePreferenceUtils;
import com.router.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/************************************************************
 * Description: 修改修改頁面
 ***********************************************************/
public class UpdatePwdActivity extends BaseSimFragmentActivity<UpdateInfoViewModel> {

    private final String TAG = "UpdatePwdActivity";
    private ImageView imgBack;
    protected EditText etOldPwd, etNewPwd, etNew2Pwd;
    private ImageView imgEye, imgEye2, imgEye3;
    protected Button btnUpdate;
    private TextView tvPwd1, tvPwd2, tvPwd3, tvPwd4, tvPwdToast;
    private ImageView imgPwd1, imgPwd2, imgPwd3, imgPwd4;
    private LinearLayout llNewPwd;
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
        imgBack = findViewById(R.id.img_back);
        etOldPwd = findViewById(R.id.edt_old_pwd);
        imgEye = findViewById(R.id.img_eye);
        etNewPwd = findViewById(R.id.edt_pwd_new);
        imgEye2 = findViewById(R.id.img_eye2);
        etNew2Pwd = findViewById(R.id.edt_pwd_new2);
        imgEye3 = findViewById(R.id.img_eye3);
        btnUpdate = findViewById(R.id.btn_update);
        tvPwd1 = findViewById(R.id.tv_pwd1);
        imgPwd1 = findViewById(R.id.img_pwd1);
        tvPwd2 = findViewById(R.id.tv_pwd2);
        imgPwd2 = findViewById(R.id.img_pwd2);
        tvPwd3 = findViewById(R.id.tv_pwd3);
        imgPwd3 = findViewById(R.id.img_pwd3);
        tvPwd4 = findViewById(R.id.tv_pwd4);
        imgPwd4 = findViewById(R.id.img_pwd4);
        tvPwdToast = findViewById(R.id.tv_new_pwd_toast);
        llNewPwd = findViewById(R.id.ll_new_pwd);
        imgBack.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        imgEye.setOnClickListener(this);
        imgEye2.setOnClickListener(this);
        imgEye3.setOnClickListener(this);
        updateEditePwdType(false);
        updateEditePwd2Type(false);
        updateEditePwd3Type(false);

        etNewPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isSize = (s.length() >= 8);
                tvPwd1.setTextColor(ContextCompat.getColor(UpdatePwdActivity.this, isSize ? R.color.text_FF333333 : R.color.text_FF999999));
                imgPwd1.setVisibility(isSize ? View.VISIBLE : View.GONE);
                isUpperCase = containsUpperCase(s.toString());
                tvPwd2.setTextColor(ContextCompat.getColor(UpdatePwdActivity.this, isUpperCase ? R.color.text_FF333333 : R.color.text_FF999999));
                imgPwd2.setVisibility(isUpperCase ? View.VISIBLE : View.GONE);
                isLowerCase = containsLowerCase(s.toString());
                tvPwd3.setTextColor(ContextCompat.getColor(UpdatePwdActivity.this, isLowerCase ? R.color.text_FF333333 : R.color.text_FF999999));
                imgPwd3.setVisibility(isLowerCase ? View.VISIBLE : View.GONE);
                isNum = containsNum(s.toString());
                tvPwd4.setTextColor(ContextCompat.getColor(UpdatePwdActivity.this, isNum ? R.color.text_FF333333 : R.color.text_FF999999));
                imgPwd4.setVisibility(isNum ? View.VISIBLE : View.GONE);
                if (!TextUtils.isEmpty(s.toString())) {
                    if (tvPwdToast.getVisibility() != View.VISIBLE) {
                        tvPwdToast.setVisibility(View.VISIBLE);
                    }
                    if (isSize && isUpperCase && isLowerCase && isNum) {
                        llNewPwd.setBackgroundResource(R.drawable.edt_bg);
                        tvPwdToast.setText("密碼已符合最低安全要求");
                        tvPwdToast.setTextColor(ContextCompat.getColor(UpdatePwdActivity.this, R.color.text_FF28AA91));
                    } else {
                        llNewPwd.setBackgroundResource(R.drawable.edt_bg_red);
                        tvPwdToast.setText("密碼不符合最低安全要求");
                        tvPwdToast.setTextColor(ContextCompat.getColor(UpdatePwdActivity.this, R.color.text_FFFF5257));
                    }
                } else {
                    llNewPwd.setBackgroundResource(R.drawable.edt_bg);
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
        return R.layout.activity_update_pwd;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(UpdateInfoViewModel.TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (msg.whatAction.equals(UpdateInfoViewModel.update_info_pwd_OK)) {
            //修改用戶密碼成功
            ToastUtil.getInstance().show(getString(R.string.update_pwd_success));
            finish();
        } else if (msg.whatAction.equals(UpdateInfoViewModel.update_info_pwd_FAIL)) {
            //修改用戶密碼失敗
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
        } else if (view.getId() == R.id.img_eye) {
            updateEditePwdType(!mPwdVisible);
        } else if (view.getId() == R.id.img_eye2) {
            updateEditePwd2Type(!mPwd2Visible);
        } else if (view.getId() == R.id.img_eye3) {
            updateEditePwd3Type(!mPwd3Visible);
        }
    }


    protected void updateClick() {
        if (!checkEditEmpty(etOldPwd) && !checkEditEmpty(etNewPwd)
                && !checkEditEmpty(etNew2Pwd) && isNetAvailable()) {
            String oldPwd = etOldPwd.getText().toString().trim();
            String pwd = SharePreferenceUtils.getSharePreference(this).getLoginPwd();
            if (!TextUtils.equals(oldPwd, pwd)) {
                ToastUtil.getInstance().show(getString(R.string.register_pwd_error));
                return;
            }
            String newPwd = etNewPwd.getText().toString().trim();
            String new2Pwd = etNew2Pwd.getText().toString().trim();
            if (!TextUtils.equals(newPwd, new2Pwd)) {
                ToastUtil.getInstance().show(getString(R.string.register_pwd_error2));
                return;
            }
            if (loadingDialog == null) {
                initLoadingDailog();
            }
            if (!loadingDialog.isShowing()) loadingDialog.show();
            loadingDialog.setTitle(getString(R.string.update_info_loading));
            getViewModel().updatePwd(newPwd, new2Pwd);
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

    private boolean mPwdVisible = false;
    private boolean mPwd2Visible = false;
    private boolean mPwd3Visible = false;

    private void updateEditePwdType(boolean pwdVisible) {
        this.mPwdVisible = pwdVisible;
        etOldPwd.setTransformationMethod(mPwdVisible ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        etOldPwd.setSelection(etOldPwd.getText().length());
        imgEye.setBackgroundResource(mPwdVisible ? R.mipmap.pwd_eye : R.mipmap.pwd_eye2);
    }

    private void updateEditePwd2Type(boolean pwdVisible) {
        this.mPwd2Visible = pwdVisible;
        etNewPwd.setTransformationMethod(mPwd2Visible ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        etNewPwd.setSelection(etNewPwd.getText().length());
        imgEye2.setBackgroundResource(mPwd2Visible ? R.mipmap.pwd_eye : R.mipmap.pwd_eye2);
    }

    private void updateEditePwd3Type(boolean pwdVisible) {
        this.mPwd3Visible = pwdVisible;
        etNew2Pwd.setTransformationMethod(mPwd2Visible ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        etNew2Pwd.setSelection(etNew2Pwd.getText().length());
        imgEye3.setBackgroundResource(mPwd2Visible ? R.mipmap.pwd_eye : R.mipmap.pwd_eye2);
    }
}
