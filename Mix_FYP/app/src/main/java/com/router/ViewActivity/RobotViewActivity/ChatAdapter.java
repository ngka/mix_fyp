package com.router.ViewActivity.RobotViewActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.router.Model.ChatModel;
import com.router.R;

import java.util.List;

/**
 * 聊天介面轉接器
 */
class ChatAdapter extends BaseAdapter {
    private List<ChatModel> chatBeanList; //聊天数据
    private LayoutInflater layoutInflater;
    private Context mContext;

    public ChatAdapter(List<ChatModel> chatBeanList, Context context) {
        this.mContext = context;
        this.chatBeanList = chatBeanList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return chatBeanList.size();
    }

    @Override
    public ChatModel getItem(int position) {
        return chatBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        Holder holder = new Holder();
        //判斷當前的資訊是發送的資訊還是接收到的資訊，不同資訊加載不同的view
        if (chatBeanList.get(position).getState() == ChatModel.RECEIVE) {
            //加載左邊佈局，也就是機器人對應的佈局資訊
            contentView = layoutInflater.inflate(R.layout.chatting_left_item,
                    null);
        } else {
            //加載右邊佈局，也就是用戶對應的佈局資訊
            contentView = layoutInflater.inflate(R.layout.chatting_right_item,
                    null);
        }
        holder.tv_chat_content = (TextView) contentView.findViewById(R.id.
                tv_chat_content);
        String msgContentSpannable = chatBeanList.get(position).getMessage();
        holder.tv_chat_content.setText(msgContentSpannable);
        return contentView;
    }

    class Holder {
        public TextView tv_chat_content; // 聊天内容
    }
}

