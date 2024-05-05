package com.router.ViewActivity.ReservationViewActivity.CommonViewActivity;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.ReportIssuesModel;
import com.router.R;
import com.router.ViewModel.ReservationViewModel.CommonViewModel.CommonViewModel;
import com.router.utils.ActivityManager;
import com.router.utils.LoadingDialogUtil;
import com.router.utils.ToastUtil;

/************************************************************
 * Description: 回報路由器問題4頁面
 ***********************************************************/
public class Common4Activity extends BaseSimFragmentActivity<CommonViewModel> {

    private final String TAG = "Common4Activity";
    private ImageView imgBack;
    private TextView tvDes;
    private Button btnLast, btnCommon;
    private EditText edtDownloadSd, edtUplaodSD, edtXHZQ, edtKYWLSL, edtSBSL;

    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        tvDes = findViewById(R.id.tv_des);
        btnLast = findViewById(R.id.btn_last);
        btnCommon = findViewById(R.id.btn_common);
        edtDownloadSd = findViewById(R.id.edt_download_sd);
        edtUplaodSD = findViewById(R.id.edt_upload_sd);
        edtXHZQ = findViewById(R.id.edt_xhzq);
        edtKYWLSL = findViewById(R.id.edt_keywlsl);
        edtSBSL = findViewById(R.id.edt_sbsl);
        imgBack.setOnClickListener(this);
        btnLast.setOnClickListener(this);
        btnCommon.setOnClickListener(this);
        String url = "https://www.speedtest.net/";
        String text = "若對網速有疑慮，請訪問按鈕進行檢測，該問題按鈕超鏈接：";
        String des = text + url;
        SpannableString spannableString = new SpannableString(des);
        URLSpan span = new MyURLSpan(url, ContextCompat.getColor(this, R.color.text_FF2F6BF4));
        spannableString.setSpan(span, text.length(), des.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDes.setText(spannableString);
        tvDes.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common4;
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
        if (TextUtils.equals(msg.whatAction, getViewModel().UPLOAD_IMAGE_FAIL)) {
            //上傳圖片失敗
            ToastUtil.getInstance().show(msg.ObjToString());
        } else if (TextUtils.equals(msg.whatAction, getViewModel().ADD_REPORT_ISSUES_OK)) {
            if (msg.obj != null) {
                ReportIssuesModel model = (ReportIssuesModel) msg.obj;
                Intent intent;
                if (model.isVisit()) {
                    intent = new Intent(this, AnalysisActivity.class);
                } else {
                    intent = new Intent(this, AnalysisNoActivity.class);
                }
                intent.putExtra("data", model);
                startActivity(intent);
                ActivityManager.getInstance().closeActivity(Common4Activity.class);
                ActivityManager.getInstance().closeActivity(Common3Activity.class);
                ActivityManager.getInstance().closeActivity(Common2Activity.class);
                ActivityManager.getInstance().closeActivity(Common1Activity.class);
            }
        } else if (TextUtils.equals(msg.whatAction, getViewModel().ADD_REPORT_ISSUES_FAIL)) {
            //上報失敗
            ToastUtil.getInstance().show(msg.ObjToString());
        }
    }

    @Override
    protected CommonViewModel bindViewModel() {
        return new CommonViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        } else if (view.getId() == R.id.btn_last) {
            CommonManager.getInstance().setCommon4(null, null, null, null, null);
            finish();
        } else if (view.getId() == R.id.btn_common) {
            //下載速度
            String dsd = edtDownloadSd.getText().toString().trim();
            //上傳速度
            String usd = edtUplaodSD.getText().toString().trim();
            //信號最強
            String xhzq = edtXHZQ.getText().toString().trim();
            //可用網絡數量
            String kywlsl = edtKYWLSL.getText().toString().trim();
            //設備數量
            String sbsl = edtSBSL.getText().toString().trim();
            if (TextUtils.isEmpty(dsd) || TextUtils.isEmpty(usd) || TextUtils.isEmpty(xhzq)
                    || TextUtils.isEmpty(kywlsl) || TextUtils.isEmpty(sbsl)) {
                ToastUtil.getInstance().show("數據不能為空！");
                return;
            }
            CommonManager.getInstance().setCommon4(dsd, usd, xhzq, kywlsl, sbsl);
            initLoadDialog(false);
            LoadingDialogUtil.setText(loadingDialog, "提交中...");
            loadingDialog.show();
            getViewModel().common();
        }
    }

}
