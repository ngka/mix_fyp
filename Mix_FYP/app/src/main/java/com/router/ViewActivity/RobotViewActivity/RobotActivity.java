package com.router.ViewActivity.RobotViewActivity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.router.Core.Base.Activity.BaseSimFragmentActivity;
import com.router.Core.EventBus.EventBusMessage;
import com.router.Model.ChatModel;
import com.router.R;
import com.router.ViewModel.RobotViewModel.RobotViewModel;
import com.router.utils.AndroidBug5497Workaround;
import com.router.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/************************************************************
 * Description: 聊天機器人頁面
 ***********************************************************/
public class RobotActivity extends BaseSimFragmentActivity<RobotViewModel> {
    private ListView listView;
    private ChatAdapter adpter;
    private List<ChatModel> chatBeanList; //存放所有聊天數據的集合
    private EditText et_send_msg;
    private Button btn_send;
    private String sendMsg;    //發送的信息
    private String welcome[];  //存儲歡迎信息
    public static final int MSG_OK = 1;//獲取數據


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        // 相容全屏狀態下adjust Resize無效
        AndroidBug5497Workaround.assistActivity(this);
        chatBeanList = new ArrayList<>();
        //獲取內置的歡迎信息
        welcome = getResources().getStringArray(R.array.welcome);
        listView = findViewById(R.id.list);
        et_send_msg = findViewById(R.id.edt_send_msg);
        btn_send = findViewById(R.id.btn_send);
        adpter = new ChatAdapter(chatBeanList, this);
        listView.setAdapter(adpter);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendData();//點擊發送按鈕，發送信息
            }
        });
        et_send_msg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() ==
                        KeyEvent.ACTION_DOWN) {
                    sendData();//點擊Enter鍵也可以發送信息
                }
                return false;
            }
        });
        int position = (int) (Math.random() * welcome.length - 1); //獲取壹個隨機數
        showData(welcome[position]); //用隨機數獲取機器人的首次聊天信息
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_robot;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    protected RobotViewModel bindViewModel() {
        return new RobotViewModel();
    }

    @Override
    public void registEventHandlerMsgType() {
        addHandlerMsgType(getViewModel().TAG);
    }

    @Override
    public void onEventHandlerMsgUIThread(EventBusMessage msg) {
        if (loadingDialog != null && loadingDialog.isShowing()) loadingDialog.dismiss();
        if (TextUtils.equals(msg.whatAction, RobotViewModel.GET_CHAT_SUCCESS)) {
            if (msg.obj != null) {
                ChatModel chatBean = (ChatModel) msg.obj;
                chatBean.setState(ChatModel.RECEIVE);//RECEIVE表示接收到機器人發送的信息
                chatBeanList.add(chatBean);  //將機器人發送的信息添加到chatBeanList集合中
                adpter.notifyDataSetChanged();
            }
        } else if (TextUtils.equals(msg.whatAction, RobotViewModel.GET_CHAT_FAIL)) {
            //發送消息失敗
            ToastUtil.getInstance().show(msg.ObjToString());
        }
    }

    private void sendData() {
        sendMsg = et_send_msg.getText().toString().trim(); //獲取妳輸入的信息
        if (TextUtils.isEmpty(sendMsg)) {             //判斷是否爲空
            ToastUtil.getInstance().show("您還未輸任何信息哦");
            return;
        }
        et_send_msg.setText("");
        //替換空格和換行
//        sendMsg = sendMsg.replaceAll(" ", "").replaceAll("\n", "").trim();
        ChatModel chatBean = new ChatModel();
        chatBean.setMessage(sendMsg);
        chatBean.setState(ChatModel.SEND); //SEND表示自己發送的信息
        chatBeanList.add(chatBean);        //將發送的信息添加到chatBeanList集合中
        adpter.notifyDataSetChanged();    //更新ListView列表
        getViewModel().sendChat(sendMsg);            //從服務器獲取機器人發送的信息
    }

    private void showData(String message) {
        ChatModel chatBean = new ChatModel();
        message = message.replaceAll(" ", "").trim();
        while ("\\n".equals(message.substring(0, 2))) {
            message = message.substring(2);
        }
        chatBean.setMessage(message);
        chatBean.setState(ChatModel.RECEIVE);//RECEIVE表示接收到機器人發送的信息
        chatBeanList.add(chatBean);  //將機器人發送的資訊添加到chat Bean List集合中
        adpter.notifyDataSetChanged();
    }


}
