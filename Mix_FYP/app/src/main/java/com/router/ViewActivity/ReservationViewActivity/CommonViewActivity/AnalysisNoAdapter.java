package com.router.ViewActivity.ReservationViewActivity.CommonViewActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.router.Core.Base.Adapter.BaseCommonAdapter2;
import com.router.Core.Base.Adapter.ViewHolder;
import com.router.Model.ResultTypeModel;
import com.router.R;
import com.router.Widget.ExpandableTextView;
import com.router.utils.ActivityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisNoAdapter extends BaseCommonAdapter2<ResultTypeModel> {
    Map<Integer, Boolean> map = new HashMap<>();
    SparseBooleanArray mCollapsedStatus;

    /**
     * 構造方法
     *
     * @param context  Activity上下文
     * @param datas    數據源
     * @param layoutID 布局文件ID
     */
    public AnalysisNoAdapter(Context context, List<ResultTypeModel> datas, int layoutID) {
        super(context, datas, layoutID);
        mCollapsedStatus = new SparseBooleanArray();
    }

    public void updateData(List<ResultTypeModel> datas) {
        listDatas = new ArrayList<>(datas);
        notifyDataSetChanged();
    }

    @Override
    protected void showGetView(ViewHolder holder, ViewGroup parent, int position) {
        ResultTypeModel model = getItem(position);
        TextView tvName = holder.getView(R.id.tv_name);
        ExpandableTextView expTv1 = (ExpandableTextView) holder.getView(R.id.expand_text_view)
                .findViewById(R.id.expand_text_view);

        tvName.setText((position + 1) + "." + model.getTitle());
        if (!map.containsKey(position)) {
            map.put(position, false);
        }
        String url = model.getLink();
        if (!url.startsWith("http")) {
            url = "https://" + url;
        }
        String link = "link:" + url;
        expTv1.setText(link, mCollapsedStatus, position);
        String finalUrl = url;
        expTv1.findViewById(R.id.expandable_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));
                // 啟動Intent
                context.startActivity(intent);
            }
        });
    }
}
