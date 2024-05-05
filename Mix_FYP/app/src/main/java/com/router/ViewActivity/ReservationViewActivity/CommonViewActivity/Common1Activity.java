package com.router.ViewActivity.ReservationViewActivity.CommonViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.UserModel;
import com.router.MyApplication;
import com.router.R;
import com.router.ViewModel.LoginViewModel.LoginViewModel;
import com.router.utils.TimePickerUtil;
import com.router.utils.ToastUtil;

/************************************************************
 * Description: 回報路由器問題1頁面
 ***********************************************************/
public class Common1Activity extends BaseSimFragmentActivity<LoginViewModel> {

    private final String TAG = "Common1Activity";
    private ImageView imgBack;
    TextView tvBuyTime;
    EditText edtName, edtPhone, edtEmail, edtAddress, edtCode, edtLyxh;
    LinearLayout llBuyTime;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        btnNext = findViewById(R.id.btn_next);
        edtName = findViewById(R.id.edt_name);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtAddress = findViewById(R.id.edt_address);
        edtCode = findViewById(R.id.det_dingdan);
        edtLyxh = findViewById(R.id.edt_lyxh);
        tvBuyTime = findViewById(R.id.tv_buy_time);
        llBuyTime = findViewById(R.id.ll_buy_time);
        imgBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        llBuyTime.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common1;
    }

    @Override
    protected void initData() {
        UserModel userModel = MyApplication.getUserModel();
        edtName.setText(userModel.getNikeName());
        edtPhone.setText(userModel.getPhone());
        edtEmail.setText(userModel.getMail());
        edtAddress.setText(userModel.getAddress());
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
            CommonManager.getInstance().setCommon1(null, null, null,
                    null, null, null, null);
            finish();
        } else if (view.getId() == R.id.ll_buy_time) {
            TimePickerUtil timePickerCustom = new TimePickerUtil(this, "", (year, month, day) -> {
                // 確定按鈕的回調
                tvBuyTime.setText(year + "-" + month + "-" + day);
            });
            // 彈出
            timePickerCustom.show();
        } else if (view.getId() == R.id.btn_next) {
            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String address = edtAddress.getText().toString().trim();
            String code = edtCode.getText().toString().trim();
            String lyxh = edtLyxh.getText().toString().trim();
            String buyTime = tvBuyTime.getText().toString().trim();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email)
                    || TextUtils.isEmpty(address) || TextUtils.isEmpty(code) || TextUtils.isEmpty(lyxh)
                    || TextUtils.isEmpty(buyTime)) {
                ToastUtil.getInstance().show("數據不能為空！");
                return;
            }
            CommonManager.getInstance().setCommon1(name, phone, email, address, code, lyxh, buyTime);
            startActivity(new Intent(this, Common2Activity.class));
        }
    }

}
