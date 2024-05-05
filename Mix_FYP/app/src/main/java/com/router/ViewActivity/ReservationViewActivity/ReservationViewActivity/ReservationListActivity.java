package com.router.ViewActivity.ReservationViewActivity.ReservationViewActivity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.Base.Adapter.ItemClickListener;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.ReservationItemModel;
import com.router.R;
import com.router.ViewModel.ReservationViewModel.ReservationListViewModel;
import com.router.ViewModel.ReservationViewModel.ReservationViewModel;
import com.router.utils.DialogUtils;
import com.router.utils.LoadingDialogUtil;
import com.router.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Description: 記錄列表頁面
 ***********************************************************/
public class ReservationListActivity extends BaseSimFragmentActivity<ReservationListViewModel> {

    private ImageView imgBack;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private TextView tvEmpty;
    private ReservationListAdapter mAdapter;
    private int pageSize = 10;
    private int pageNo = 1;
    private List<ReservationItemModel> list = new ArrayList<>();

    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerview);
        smartRefreshLayout = findViewById(R.id.refreshLayout);
        tvEmpty = findViewById(R.id.tv_empty);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //下拉刷新和上拉加載監聽
        //設置下拉刷新和上拉加載樣式，默認樣式
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        mAdapter = new ReservationListAdapter(list);
        recyclerView.setAdapter(mAdapter);
        /*3、添加item的添加和移除動畫, 這裏我們使用系統默認的動畫*/
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //下拉刷新和上拉加載更新數據
        smartRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                onRefsh();
                smartRefreshLayout.finishRefresh(1000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                smartRefreshLayout.finishLoadMore(2000);
                //上拉每次取10條記錄加到cList中
                pageNo++;
                getData(pageNo, true);
                smartRefreshLayout.finishLoadMore(2000);
            }
        });
        mAdapter.setItemClickListener(new ItemClickListener<ReservationItemModel>() {
            @Override
            public void onClick(View view, ReservationItemModel model) {
                //預約詳情
                Intent intent = new Intent(ReservationListActivity.this, ReservationInfoActivity.class);
                intent.putExtra("data", model);
                startActivity(intent);
            }
        });
        mAdapter.setItemCancelClickListener(new ItemClickListener<ReservationItemModel>() {
            @Override
            public void onClick(View view, ReservationItemModel model) {
                //取消預約
                Intent intent = new Intent(ReservationListActivity.this, CancelReservationActivity.class);
                intent.putExtra("data", model);
                startActivity(intent);
            }
        });
        mAdapter.setItemFeedBackClickListener(new ItemClickListener<ReservationItemModel>() {
            @Override
            public void onClick(View view, ReservationItemModel model) {
                //反饋
                Intent intent = new Intent(ReservationListActivity.this, FeedbookActivity.class);
                intent.putExtra("data", model);
                startActivity(intent);

            }
        });
        mAdapter.setItemUpdateTimeClickListener(new ItemClickListener<ReservationItemModel>() {
            @Override
            public void onClick(View view, ReservationItemModel model) {
                //修改預約時間
                Intent intent = new Intent(ReservationListActivity.this, UpdateReservationActivity.class);
                intent.putExtra("data", model);
                startActivity(intent);
            }
        });
        tvEmpty.setOnClickListener(this);
    }

    public void onRefsh() {
        list.clear();
        getData(pageNo = 1, false);
    }


    public void getData(int pageNo, boolean isLoadMore) {
        getViewModel().getPageInfo(pageNo, pageSize, isLoadMore);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reservation_list;
    }

    @Override
    protected void initData() {
        //首次進入加載數據
        onRefsh();
    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(ReservationListViewModel.TAG);
        addHandlerMsgType(ReservationViewModel.TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (TextUtils.equals(msg.whatAction, getViewModel().UPDATE_APPOINTMENT_OK)) {
            //更新預約成功
            if (msg.obj != null) {
                onRefsh();
            }
        } else if (TextUtils.equals(msg.whatAction, ReservationListViewModel.GET_PAGE_INFO_OK)) {
            if (msg.obj != null) {
                List<ReservationItemModel> models = (List<ReservationItemModel>) msg.obj;
                list.addAll(models);
                mAdapter.update(list);
            } else {
                //加載更多獲取數據失敗，減去頁數
                if (msg.extendObj instanceof Boolean && (Boolean) msg.extendObj) {
                    pageNo--;
                }
            }
            refreshEmpty();
        } else if (TextUtils.equals(msg.whatAction, ReservationListViewModel.GET_PAGE_INFO_FAIL)) {
            //獲取歷史記錄失敗
            //ToastUtil.getInstance().show(msg.ObjToString());
            //加載更多獲取數據失敗，減去頁數
            if (msg.extendObj instanceof Boolean && (Boolean) msg.extendObj) {
                pageNo--;
            }
            refreshEmpty();
        }
        if (TextUtils.equals(msg.whatAction, ReservationListViewModel.EDIT_APPOINTMENT_OK)) {
            //取消歷史記錄成功
            onRefsh();
        }
    }

    private void refreshEmpty() {
        if (list.size() > 0) {
            if (smartRefreshLayout.getVisibility() == View.GONE) {
                tvEmpty.setVisibility(View.GONE);
                smartRefreshLayout.setVisibility(View.VISIBLE);
            }
        } else {
            if (smartRefreshLayout.getVisibility() == View.VISIBLE) {
                tvEmpty.setVisibility(View.VISIBLE);
                smartRefreshLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected ReservationListViewModel bindViewModel() {
        return new ReservationListViewModel();
    }


    @Override
    protected void otherViewClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        } else if (view.getId() == R.id.tv_empty) {
            onRefsh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.clearTimer();
        }
    }
}
