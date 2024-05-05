package com.router.ViewActivity.ReservationViewActivity.ReservationViewActivity;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.ImageUrl;
import com.router.Model.ReportIssuesModel;
import com.router.Model.ReservationInfoModel;
import com.router.Model.ReservationItemModel;
import com.router.R;
import com.router.ViewModel.LoginViewModel.LoginViewModel;
import com.router.ViewModel.ReservationViewModel.ReservationInfoViewModel;
import com.router.ViewModel.ReservationViewModel.ReservationListViewModel;
import com.router.ViewModel.ReservationViewModel.ReservationViewModel;
import com.router.Widget.ChooseImageView;
import com.router.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Description: 記錄詳情頁面
 ***********************************************************/
public class ReservationInfoActivity extends BaseSimFragmentActivity<ReservationInfoViewModel> {

    private ImageView imgBack;
    private TextView tvState, tvCode, tvTime, tvAddress, tvRemark,
            tvDownloadSpeed, tvUploadSpeed, tvStrongestSignal, tvNetworkNum,
            tvDevicesNum, tvSolution, tvCompletionTime;
    private TextView tvYYSJ;
    //路由器型號
    private TextView tvLyxh;
    private ChooseImageView imageView1, imageView2, imageView3;
    private LinearLayout llImage;
    private ReservationInfoModel model;

    List<ChooseImageView> imageViews = new ArrayList<>();

    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        tvState = findViewById(R.id.tv_state);
        tvCode = findViewById(R.id.tv_code);
        tvTime = findViewById(R.id.tv_time);
        tvState = findViewById(R.id.tv_state);
        tvAddress = findViewById(R.id.tv_address);
        tvYYSJ = findViewById(R.id.tv_yysj);
        tvRemark = findViewById(R.id.tv_ramark);
        tvDownloadSpeed = findViewById(R.id.tv_downloadSpeed);
        tvUploadSpeed = findViewById(R.id.tv_uploadSpeed);
        tvStrongestSignal = findViewById(R.id.tv_strongestSignal);
        tvNetworkNum = findViewById(R.id.tv_networkNum);
        tvDevicesNum = findViewById(R.id.tv_devicesNum);
        tvSolution = findViewById(R.id.tv_solution);
        tvCompletionTime = findViewById(R.id.tv_completionTime);
        tvLyxh = findViewById(R.id.tv_lyxh);
        imageView1 = findViewById(R.id.imageview1);
        imageView2 = findViewById(R.id.imageview2);
        imageView3 = findViewById(R.id.imageview3);
        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        llImage = findViewById(R.id.ll_image);
        imgBack.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void updateView() {
        if (model == null) {
            return;
        }
        tvState.setText(ReservationViewModel.getState(model.getAppointmentState()));
        tvCode.setText(model.getAppointmentNum() + "");
        tvTime.setText(model.getCreateTime());
        tvAddress.setText(model.getAppointmentAddress());
        tvYYSJ.setText(model.getAppointmentTime());
        tvLyxh.setText(model.getRouteModel());
        tvRemark.setText(model.getRemark());
        tvDownloadSpeed.setText(model.getDownloadSpeed() + "M/S");
        tvUploadSpeed.setText(model.getUploadSpeed() + "M/S");
        tvStrongestSignal.setText(model.getStrongestSignal());
        tvNetworkNum.setText(String.valueOf(model.getNetworkNum()));
        tvDevicesNum.setText(String.valueOf(model.getDevicesNum()));
        tvSolution.setText(getSolutionType(model.getSolution()));
        tvCompletionTime.setText(model.getCompletionTime());
        List<ImageUrl> urlList = model.getImgUrlList();
        if (urlList != null && urlList.size() > 0) {
            llImage.setVisibility(View.VISIBLE);
            for (int i = 0; i < urlList.size(); i++) {
                Glide.with(this).load(urlList.get(i).getImgUrl())
                        .asBitmap()
                        .placeholder(R.mipmap.common_default_image_icon)
                        .error(R.mipmap.common_default_image_icon)
                        .into(imageViews.get(i));
            }
        } else {
            llImage.setVisibility(View.GONE);
        }
    }

    /**
     * 1上門，2自主解決
     */
    private String getSolutionType(String solution) {
        if (TextUtils.equals(solution, "1")) {
            return "上门";
        } else {
            return "2自主解決";
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reservation_info;
    }

    @Override
    protected void initData() {
        ReservationItemModel itemModel = (ReservationItemModel) getIntent().getSerializableExtra("data");
        updateView();
        initLoadDialog(false);
        loadingDialog.show();
        getViewModel().getReportIssuesById(itemModel.getIssuesId());
    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(ReservationInfoViewModel.TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (TextUtils.equals(msg.whatAction, ReservationInfoViewModel.GET_RESERVATION_INFO_OK)) {
            if (msg.obj != null) {
                model = (ReservationInfoModel) msg.obj;
                updateView();
            }
        } else if (TextUtils.equals(msg.whatAction, ReservationInfoViewModel.GET_RESERVATION_INFO_FAIL)) {
            //獲取詳情失敗
            ToastUtil.getInstance().show(msg.ObjToString());
        }
    }

    @Override
    protected ReservationInfoViewModel bindViewModel() {
        return new ReservationInfoViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        }
    }

}
