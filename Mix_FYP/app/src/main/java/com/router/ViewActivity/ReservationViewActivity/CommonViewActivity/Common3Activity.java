package com.router.ViewActivity.ReservationViewActivity.CommonViewActivity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.MessageTypeModel;
import com.router.R;
import com.router.ViewModel.ReservationViewModel.CommonViewModel.CommonViewModel;
import com.router.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/************************************************************
 * Description: 回報路由器問題3頁面
 ***********************************************************/
public class Common3Activity extends BaseSimFragmentActivity<CommonViewModel> {

    private final String TAG = "Common3Activity";
    private ImageView imgBack;
    private Button btnLast, btnNext;
    private ListView listView;
    private List<MessageTypeModel> models = new ArrayList<>();
    private Common3ListAdapter adapter;

    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        btnLast = findViewById(R.id.btn_last);
        btnNext = findViewById(R.id.btn_next);
        listView = findViewById(R.id.listview);
        imgBack.setOnClickListener(this);
        btnLast.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        adapter = new Common3ListAdapter(this, models, R.layout.common3_item);
        listView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common3;
    }

    @Override
    protected void initData() {
        initLoadingDailog();
        loadingDialog.show();
        getViewModel().getMessageTypeAll();
    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(getViewModel().TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (TextUtils.equals(msg.whatAction, getViewModel().GET_MESSGE_TYPE_OK)) {
            if (msg.obj != null) {
                models.addAll((List<MessageTypeModel>) msg.obj);
                adapter.updateData(models);
            }
        } else if (TextUtils.equals(msg.whatAction, getViewModel().GET_MESSGE_TYPE_FAIL)) {
            //獲取失敗
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
            CommonManager.getInstance().setIssuesId(-1);
            finish();
        } else if (view.getId() == R.id.btn_next) {
            int id = adapter.getCheckedId();
            if (id == -1) {
                ToastUtil.getInstance().show("請選擇相應錯誤信息！");
                return;
            }
            CommonManager.getInstance().setIssuesId(id);
            startActivity(new Intent(this, Common4Activity.class));
        }
    }

}
