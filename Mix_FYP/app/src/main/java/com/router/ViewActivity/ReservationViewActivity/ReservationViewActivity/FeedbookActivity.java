package com.router.ViewActivity.ReservationViewActivity.ReservationViewActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.ReservationItemModel;
import com.router.R;
import com.router.ViewModel.ReservationViewModel.FeedbookViewModel;
import com.router.ViewModel.ReservationViewModel.ReservationListViewModel;
import com.router.ViewModel.ReservationViewModel.ReservationViewModel;
import com.router.utils.LoadingDialogUtil;
import com.router.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Description: 反饋頁面
 ***********************************************************/
public class FeedbookActivity extends BaseSimFragmentActivity<FeedbookViewModel> {

    private ImageView imgBack;
    private TextView tvCode;
    private Button btnCommon;
    private ReservationItemModel model;
    private RatingBar mRatingBar1, mRatingBar2,
            mRatingBar3, mRatingBar4, mRatingBar5;
    private EditText edtRemark;


    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        tvCode = findViewById(R.id.tv_code);
        mRatingBar1 = findViewById(R.id.ratingbar_1);
        mRatingBar2 = findViewById(R.id.ratingbar_2);
        mRatingBar3 = findViewById(R.id.ratingbar_3);
        mRatingBar4 = findViewById(R.id.ratingbar_4);
        mRatingBar5 = findViewById(R.id.ratingbar_5);
        edtRemark = findViewById(R.id.edt_remark);
        btnCommon = findViewById(R.id.btn_common);
        imgBack.setOnClickListener(this);
        btnCommon.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedbook;
    }

    @Override
    protected void initData() {
        model = (ReservationItemModel) getIntent().getSerializableExtra("data");
        if (model == null) {
            return;
        }
        tvCode.setText(model.getAppointmentNum() + "");
    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(getViewModel().TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (TextUtils.equals(msg.whatAction, getViewModel().INSERT_FEEDBACK_OK)) {
            ToastUtil.getInstance().show("添加評分成功！");
            finish();
        } else if (TextUtils.equals(msg.whatAction, getViewModel().INSERT_FEEDBACK_FAIL)) {
            //添加評分失敗
            ToastUtil.getInstance().show(msg.ObjToString());
        }
    }

    @Override
    protected FeedbookViewModel bindViewModel() {
        return new FeedbookViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        } else if (view.getId() == R.id.btn_common) {
            int num1 = (int) mRatingBar1.getRating();
            int num2 = (int) mRatingBar2.getRating();
            int num3 = (int) mRatingBar3.getRating();
            int num4 = (int) mRatingBar4.getRating();
            int num5 = (int) mRatingBar5.getRating();
            String remark = edtRemark.getText().toString().trim();
            initLoadingDailog();
            LoadingDialogUtil.setText(loadingDialog, "提交中...");
            loadingDialog.show();
            getViewModel().insertFeedback(model.getAppointmentNum(), num1 + "", num2 + "", num3 + "", num4 + "", num5 + "", remark);
        }
    }

}
