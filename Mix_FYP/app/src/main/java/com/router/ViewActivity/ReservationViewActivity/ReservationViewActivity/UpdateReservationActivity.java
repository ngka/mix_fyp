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
import com.router.ViewModel.ReservationViewModel.ReservationViewModel;
import com.router.utils.LoadingDialogUtil;
import com.router.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/************************************************************
 * Description: 修改預約時間頁面
 ***********************************************************/
public class UpdateReservationActivity extends BaseSimFragmentActivity<ReservationViewModel> {

    private final String TAG = "ReservationActivity";
    private ImageView imgBack;
    private TextView tvCode, tvTimeOld;
    private LinearLayout llMore, llDateItem3;
    private EditText edtAddress, edtEmail, edtLy;
    private Button btnClear, btnCommon;
    private List<TextView> timeViews = new ArrayList<>();
    private List<View[]> dataViews = new ArrayList<>();
    private int dateCheckPostion = -1;
    private int timeCheckPostion = -1;
    private ReservationItemModel model;

    // 初始化視圖
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.img_back);
        tvCode = findViewById(R.id.tv_code);
        tvTimeOld = findViewById(R.id.tv_time_old);
        llMore = findViewById(R.id.ll_date_more);
        llDateItem3 = findViewById(R.id.ll_date_item3);
        edtAddress = findViewById(R.id.edt_address);
        edtEmail = findViewById(R.id.edt_email);
        edtLy = findViewById(R.id.edt_ly);
        btnClear = findViewById(R.id.btn_clear);
        btnCommon = findViewById(R.id.btn_common);
        imgBack.setOnClickListener(this);
        llMore.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnCommon.setOnClickListener(this);
        for (int i = 0; i < 25; i++) {
            TextView tvTime = findViewById(getResourceId(this, "tv_time" + (i + 1)));
            tvTime.setTag(i);
            tvTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = Integer.parseInt(v.getTag().toString());
                    if (timeCheckPostion != currentPosition) {
                        setTimeView(timeCheckPostion, false);
                        timeCheckPostion = currentPosition;
                        setTimeView(timeCheckPostion, true);
                    }

                }
            });
            timeViews.add(tvTime);
        }
        Calendar calendar = Calendar.getInstance();
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH) + 1;
        int dateNow = calendar.get(Calendar.DATE);
        int maxDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i < 9; i++) {
            LinearLayout llDate = findViewById(getResourceId(this, "ll_date" + (i + 1)));
            TextView tvWeek = findViewById(getResourceId(this, "tv_week" + (i + 1)));
            TextView tvDate = findViewById(getResourceId(this, "tv_date" + (i + 1)));
            llDate.setTag(i);
            llDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = Integer.parseInt(v.getTag().toString());
                    if (dateCheckPostion != currentPosition) {
                        setDateView(dateCheckPostion, false);
                        dateCheckPostion = currentPosition;
                        setDateView(dateCheckPostion, true);
                    }

                }
            });
            String week;
            if (i == 0) {
                week = "明天";
            } else {
                int currentdayOfWeek = dayOfWeek + i + 1;
                if (currentdayOfWeek > 7) {
                    currentdayOfWeek = currentdayOfWeek - 7;
                }
                switch (currentdayOfWeek) {
                    case Calendar.SUNDAY:
                        week = "週日";
                        System.out.println("星期日");
                        break;
                    case Calendar.MONDAY:
                        week = "週一";
                        System.out.println("星期一");
                        break;
                    case Calendar.TUESDAY:
                        week = "週二";
                        System.out.println("星期二");
                        break;
                    case Calendar.WEDNESDAY:
                        week = "週三";
                        System.out.println("星期三");
                        break;
                    case Calendar.THURSDAY:
                        System.out.println("星期四");
                        week = "週四";
                        break;
                    case Calendar.FRIDAY:
                        System.out.println("星期五");
                        week = "週五";
                        break;
                    case Calendar.SATURDAY:
                        System.out.println("星期六");
                        week = "週六";
                        break;
                    default:
                        week = "週日";
                        break;
                }
            }
            tvWeek.setText(week);
            int currentDate;
            int currentMonth;
            int currentYear;
            if (dateNow + i + 1 <= maxDate) {
                //當前月份數據
                currentDate = dateNow + i + 1;
                currentYear = yearNow;
                currentMonth = monthNow;
            } else {
                //下壹月數據
                if (monthNow == 12) {
                    currentMonth = 1;
                    currentYear = yearNow + 1;
                } else {
                    currentMonth = monthNow + 1;
                    currentYear = yearNow;
                }
                currentDate = dateNow + i + 1 - maxDate;
            }
            tvDate.setText(currentMonth + "月" + currentDate + "日");
            tvDate.setTag(currentYear + "-" + currentMonth + "-" + currentDate);
            dataViews.add(new View[]{llDate, tvWeek, tvDate});
        }
        setDateView(dateCheckPostion, true);
        setTimeView(timeCheckPostion, true);
    }

    /**
     * 設置時間樣式
     */
    private void setTimeView(int postion, boolean isCheck) {
        if (postion == -1) {
            return;
        }
        timeViews.get(postion).setTextColor(ContextCompat.getColor(this, isCheck ? R.color.text_FF2F6BF4 : R.color.text_FF333333));
        timeViews.get(postion).setBackgroundResource(isCheck ? R.drawable.btn_border_blue2 : R.drawable.btn_border2);
    }

    /**
     * 設置日期樣式
     */
    private void setDateView(int postion, boolean isCheck) {
        if (postion == -1) {
            return;
        }
        dataViews.get(postion)[0].setBackgroundResource(isCheck ? R.drawable.btn_border_blue2 : R.drawable.btn_border2);
        ((TextView) dataViews.get(postion)[1]).setTextColor(ContextCompat.getColor(this, isCheck ? R.color.text_FF2F6BF4 : R.color.text_FF333333));
        ((TextView) dataViews.get(postion)[2]).setTextColor(ContextCompat.getColor(this, isCheck ? R.color.text_FF2F6BF4 : R.color.text_FF333333));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reservation_update;
    }

    @Override
    protected void initData() {
        model = (ReservationItemModel) getIntent().getSerializableExtra("data");
        if (model == null) {
            return;
        }
        tvCode.setText(model.getAppointmentNum() + "");
        tvTimeOld.setText(model.getAppointmentTime());
        edtAddress.setText(model.getAppointmentAddress());
        edtEmail.setText(model.getMail());
        edtLy.setText(model.getRemark());
    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(getViewModel().TAG);
    }

    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (TextUtils.equals(msg.whatAction, ReservationViewModel.UPDATE_APPOINTMENT_OK)) {
            //更新預約成功
            if (msg.obj != null) {
                ToastUtil.getInstance().show("更新預約成功!");
                finish();
            }
        } else if (TextUtils.equals(msg.whatAction, ReservationViewModel.UPDATE_APPOINTMENT_FAIL)) {
            //更新預約失敗
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
        } else if (view.getId() == R.id.btn_clear) {
            edtAddress.setText("");
            edtEmail.setText("");
            edtLy.setText("");
            setDateView(dateCheckPostion, false);
            setTimeView(timeCheckPostion, false);
            timeCheckPostion = -1;
            dateCheckPostion = -1;
        } else if (view.getId() == R.id.ll_date_more) {
            llMore.setVisibility(View.GONE);
            dataViews.get(5)[0].setVisibility(View.VISIBLE);
            llDateItem3.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.btn_common) {
            String address = edtAddress.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String ly = edtLy.getText().toString().trim();
            if (TextUtils.isEmpty(address) || TextUtils.isEmpty(email) || TextUtils.isEmpty(ly)) {
                ToastUtil.getInstance().show("數據不能為空！");
                return;
            }
            if (timeCheckPostion == -1 || dateCheckPostion == -1) {
                ToastUtil.getInstance().show("請選擇預約時間！");
                return;
            }
            String time = timeViews.get(timeCheckPostion).getText().toString();
            String date = dataViews.get(dateCheckPostion)[2].getTag().toString();
            initLoadDialog(true);
            LoadingDialogUtil.setText(loadingDialog, "提交中...");
            loadingDialog.show();
            getViewModel().updateAppointment(model.getId(), email, model.getIssuesId(), date + " " + time + ":00", address, ly);
        }
    }


}
