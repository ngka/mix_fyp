package com.router.ViewActivity.UserInfoViewActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.router.Core.Base.Adapter.BaseCommonAdapter2;
import com.router.Core.Base.Adapter.ItemClickListener;
import com.router.Core.Base.Adapter.ViewHolder;
import com.router.Model.MessageTypeModel;
import com.router.Model.ReputationRecordModel;
import com.router.Model.ReservationItemModel;
import com.router.R;
import com.router.ViewModel.ReservationViewModel.ReservationViewModel;
import com.router.utils.ActivityManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XinyuAdapter extends BaseCommonAdapter2<ReputationRecordModel> {

    /**
     * 構造方法
     *
     * @param context  Activity上下文
     * @param datas    資料來源
     * @param layoutID 佈局檔案ID
     */
    public XinyuAdapter(Context context, List<ReputationRecordModel> datas, int layoutID) {
        super(context, datas, layoutID);
    }

    public void update(List<ReputationRecordModel> list) {
        listDatas = new ArrayList<>(list);
        notifyDataSetChanged();
    }


    @Override
    protected void showGetView(ViewHolder holder, ViewGroup parent, int position) {
        ReputationRecordModel model = getItem(position);
        TextView tvMsg = holder.getView(R.id.tv_item_msg);
        TextView tvTime = holder.getView(R.id.tv_item_time);
        TextView tvNum = holder.getView(R.id.tv_item_num);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(model.getCreateTime());
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd");
            tvTime.setText( dateFormat2.format(date));
        } catch (
                ParseException e) {
            throw new RuntimeException(e);
        }
        tvMsg.setText(model.getOperation());
        tvNum.setText(model.getRecord());
    }
}
