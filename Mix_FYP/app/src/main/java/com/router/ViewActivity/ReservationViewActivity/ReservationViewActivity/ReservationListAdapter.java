package com.router.ViewActivity.ReservationViewActivity.ReservationViewActivity;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.router.Core.Base.Adapter.ItemClickListener;
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
import java.util.Set;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ItemViewHolder> {
    private List<ReservationItemModel> list;

    public ReservationListAdapter(List list) {
        this.list = new ArrayList(list);
    }

    public void update(List list) {
        clearTimer();
        this.list = new ArrayList(list);
        notifyDataSetChanged();
    }

    ItemClickListener itemClickListener;
    //取消
    ItemClickListener cancelListtener;
    //反饋
    ItemClickListener feedbackListtener;
    //修改時間
    ItemClickListener updateTimeListtener;
    Map<String, CountDownTimer> map = new HashMap<>();

    public void setItemClickListener(ItemClickListener<ReservationItemModel> listener) {
        this.itemClickListener = listener;
    }

    public void setItemCancelClickListener(ItemClickListener<ReservationItemModel> listener) {
        this.cancelListtener = listener;
    }

    public void setItemFeedBackClickListener(ItemClickListener<ReservationItemModel> listener) {
        this.feedbackListtener = listener;
    }

    public void setItemUpdateTimeClickListener(ItemClickListener<ReservationItemModel> listener) {
        this.updateTimeListtener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_list_item, null, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ReservationItemModel model = list.get(position);
        holder.tvCode.setText(model.getAppointmentNum());
        holder.tvTime.setText(model.getCreateTime());
        holder.tvMsg.setText(model.getRemark());
        holder.tvState.setText(ReservationViewModel.getState(model.getAppointmentState()));
        if (model.getAppointmentState() == 1) {
            holder.tvCancel.setVisibility(View.VISIBLE);
            holder.tvState.setTextColor(ActivityManager.getInstance().getAppContext().getResources().getColor(R.color.text_FF2F6BF4));
        } else {
            holder.tvCancel.setVisibility(View.GONE);
            holder.tvState.setTextColor(ActivityManager.getInstance().getAppContext().getResources().getColor(R.color.text_FF28AA91));
        }
        holder.tvFeedback.setVisibility(model.getAppointmentState() == 4 ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onClick(v, model);
                }
            }
        });
        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelListtener != null) {
                    cancelListtener.onClick(v, model);
                }
            }
        });
        holder.tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedbackListtener != null) {
                    feedbackListtener.onClick(v, model);
                }
            }
        });
        holder.tvUpdateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateTimeListtener != null) {
                    updateTimeListtener.onClick(v, model);
                }
            }
        });
        if (model.getAppointmentState() == 1) {
            try {
                long time = 5 * 60 * 60 * 1000;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = dateFormat.parse(model.getAppointmentTime());
                // 獲取時間戳
                long timestamp = date.getTime();
                long currentTime = System.currentTimeMillis();
                if (timestamp - currentTime < time) {
                    holder.tvUpdateTime.setVisibility(View.GONE);
                    holder.tvCtime.setVisibility(View.GONE);
                    if (map.containsKey(model.getAppointmentNum())) {
                        CountDownTimer timer = map.remove(model.getAppointmentNum());
                        if (timer != null) {
                            timer.cancel();
                        }
                    }
                } else {
                    holder.tvUpdateTime.setVisibility(View.VISIBLE);
                    holder.tvCtime.setVisibility(View.VISIBLE);
                    if (!map.containsKey(model.getAppointmentNum())) {
                        startCountdown(model, holder.tvCtime, timestamp - currentTime - time);
                    }
                }
            } catch (
                    ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            holder.tvUpdateTime.setVisibility(View.GONE);
            holder.tvCtime.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    private String formatTime(long millisUntilFinished) {
        // 格式化倒計時時間（例如：將毫秒轉換為分:秒的形式）
        String time;
        if (millisUntilFinished >= 3600000) {
            time = (millisUntilFinished / 3600000) + "小時" + (millisUntilFinished % 3600000 / 60000) + "分鐘";
        } else {
            long m = millisUntilFinished / 60000;
            time = (m == 0 ? 1 : m) + "分鐘";
        }
        return time;
    }

    private void startCountdown(ReservationItemModel model, final TextView textView, long countdownTime) {
        CountDownTimer downTimer = new CountDownTimer(countdownTime, 1000) {
            public void onTick(long millisUntilFinished) {
                // 更新倒計時時間
                textView.setText(formatTime(millisUntilFinished));
            }

            public void onFinish() {
                CountDownTimer timer = map.remove(model.getAppointmentNum());
                if (timer != null) {
                    timer.cancel();
                }
                // 倒計時結束處理
                textView.setVisibility(View.GONE);
            }
        };
        downTimer.start();
        map.put(model.getAppointmentNum(), downTimer);
    }


    /**
     * 內部類，繼承RecyclerView.ViewHolder，作用就是聲明item中的控件，findViewById
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvCode;
        TextView tvTime;
        TextView tvState;
        TextView tvMsg;
        TextView tvCancel;
        TextView tvFeedback;
        TextView tvUpdateTime;
        TextView tvCtime;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCode = itemView.findViewById(R.id.tv_code);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvState = itemView.findViewById(R.id.tv_state);
            tvMsg = itemView.findViewById(R.id.tv_msg);
            tvCancel = itemView.findViewById(R.id.tv_cancel);
            tvFeedback = itemView.findViewById(R.id.tv_feedback);
            tvUpdateTime = itemView.findViewById(R.id.tv_update_time);
            tvCtime = itemView.findViewById(R.id.tv_ctime);
        }

    }

    public void clearTimer() {
        if (map.size() > 0) {
            for (String key : map.keySet()) {
                map.get(key).cancel();
            }
            map.clear();
        }
    }
}
