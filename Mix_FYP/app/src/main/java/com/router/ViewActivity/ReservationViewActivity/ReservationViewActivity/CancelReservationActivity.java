package com.router.ViewActivity.ReservationViewActivity.ReservationViewActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.ReservationItemModel;
import com.router.R;
import com.router.ViewModel.LoginViewModel.LoginViewModel;
import com.router.ViewModel.ReservationViewModel.ReservationListViewModel;
import com.router.ViewModel.ReservationViewModel.ReservationViewModel;
import com.router.utils.LoadingDialogUtil;
import com.router.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Description: 取消記錄頁面
 ***********************************************************/
public class CancelReservationActivity extends BaseSimFragmentActivity<ReservationViewModel> {

    private ImageView imgBack;
    private TextView tvCode, tvTime;
    private EditText edtRemark, edtEmail;
    private Button btnCancel;
    private ReservationItemModel model;
    private int checkedPosition = -1;
    private List<View[]> viewList = new ArrayList<>();

    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        tvCode = findViewById(R.id.tv_code);
        tvTime = findViewById(R.id.tv_time);
        edtRemark = findViewById(R.id.edt_remark);
        edtEmail = findViewById(R.id.edt_email);
        btnCancel = findViewById(R.id.btn_cancel);
        imgBack.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        for (int i = 0; i < 4; i++) {
            LinearLayout llItem = findViewById(getResourceId(this, "ll_item" + (i + 1)));
            ImageView imgItem = findViewById(getResourceId(this, "img_item" + (i + 1)));
            TextView tvItem = findViewById(getResourceId(this, "tv_item" + (i + 1)));
            llItem.setTag(i);
            llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = Integer.parseInt(v.getTag().toString());
                    if (checkedPosition != position) {
                        setCheckView(checkedPosition, false);
                        checkedPosition = position;
                        setCheckView(checkedPosition, true);
                    }
                }
            });
            viewList.add(new View[]{llItem, imgItem, tvItem});
        }
    }

    private void setCheckView(int postion, boolean isCheck) {
        if (postion == -1) {
            return;
        }
        viewList.get(postion)[1].setBackgroundResource(isCheck ? R.mipmap.ic_check_2 : R.mipmap.ic_check_1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cancel_reservation;
    }

    @Override
    protected void initData() {
        model = (ReservationItemModel) getIntent().getSerializableExtra("data");
        if (model == null) {
            return;
        }
        tvCode.setText(model.getAppointmentNum());
        tvTime.setText(model.getAppointmentTime());
        edtEmail.setText(model.getMail());
    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(getViewModel().TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (TextUtils.equals(msg.whatAction, ReservationListViewModel.EDIT_APPOINTMENT_OK)) {
            ToastUtil.getInstance().show(msg.obj != null ? msg.obj.toString() : "取消成功！");
            finish();
        } else if (TextUtils.equals(msg.whatAction, ReservationListViewModel.EDIT_APPOINTMENT_FAIL)) {
            //取消失敗
            ToastUtil.getInstance().show(msg.ObjToString());
        }
    }

    @Override
    protected ReservationViewModel bindViewModel() {
        return new ReservationViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        } else if (view.getId() == R.id.btn_cancel) {
            String remark = edtRemark.getText().toString().trim();
            if (checkedPosition == 3) {
                remark = edtRemark.getText().toString().trim();
            } else if (checkedPosition != -1) {
                remark = ((TextView) viewList.get(checkedPosition)[2]).getText().toString().trim();
            }
            if (TextUtils.isEmpty(remark)) {
                ToastUtil.getInstance().show("取消原因不能為空！");
                return;
            }
            String email = edtEmail.getText().toString().trim();
            if (TextUtils.isEmpty(email)) {
                ToastUtil.getInstance().show(edtEmail.getHint().toString());
                return;
            }
            initLoadingDailog();
            LoadingDialogUtil.setText(loadingDialog, "取消中...");
            loadingDialog.show();
            getViewModel().editAppointment(model.getId(), email, model.getIssuesId(), model.getAppointmentTime(), model.getAppointmentAddress(), remark);
        }
    }

}
