package com.router.ViewActivity.ReservationViewActivity.CommonViewActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import com.router.Core.Base.Adapter.BaseCommonAdapter2;
import com.router.Core.Base.Adapter.ViewHolder;
import com.router.Model.MessageTypeModel;
import com.router.R;

import java.util.ArrayList;
import java.util.List;

public class Common3ListAdapter extends BaseCommonAdapter2<MessageTypeModel> {
    private int checked = -1;

    /**
     * 構造方法
     *
     * @param context  Activity上下文
     * @param datas    數據源
     * @param layoutID 布局文件ID
     */
    public Common3ListAdapter(Context context, List<MessageTypeModel> datas, int layoutID) {
        super(context, datas, layoutID);
    }

    public void updateData(List<MessageTypeModel> datas) {
        listDatas = new ArrayList<>(datas);
        checked = -1;
        notifyDataSetChanged();
    }

    public void update(int position) {
        checked = position;
        notifyDataSetChanged();
    }

    public int getCheckedId() {
        if (checked == -1) {
            return -1;
        }
        MessageTypeModel model = getItem(checked);
        return model.getId();
    }

    @Override
    protected void showGetView(ViewHolder holder, ViewGroup parent, int position) {
        MessageTypeModel model = getItem(position);
        LinearLayout itemTop = holder.getView(R.id.ll_item_top);
        LinearLayout item = holder.getView(R.id.ll_item);
        TextView tvName = holder.getView(R.id.tv_name);
        ImageView imgCheck = holder.getView(R.id.img_check);
        itemTop.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        tvName.setText(model.getContent());
        imgCheck.setBackgroundResource(checked == position ? R.mipmap.ic_check_2 : R.mipmap.ic_check_1);
        item.setBackgroundResource(checked == position ? R.drawable.btn_border_blue2 : R.drawable.edt_bg);
        tvName.setTextColor(ContextCompat.getColor(context, checked == position ? R.color.text_FF2F6BF4 : R.color.text_FF666666));
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked != position) {
                    update(position);
                }
            }
        });
    }
}
