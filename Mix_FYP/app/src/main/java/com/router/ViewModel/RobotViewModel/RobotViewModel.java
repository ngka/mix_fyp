package com.router.ViewModel.RobotViewModel;

import android.app.Activity;

import com.router.Core.Base.ViewModel.BaseViewModel;
import com.router.Core.Http.exception.ApiException;
import com.router.Core.Http.subscriber.CommonSubscriber;
import com.router.Core.Http.transformer.CommonTransformer;
import com.router.Model.ChatModel;
import com.router.Model.ChatResultModel;
import com.router.utils.ActivityManager;

import java.util.HashMap;
import java.util.Map;

/************************************************************
 * Description: 聊天機器人界面的視圖模型
 ***********************************************************/
public class RobotViewModel extends BaseViewModel<Activity> {

    public final String TAG = "RobotViewModel";
    public static final String GET_CHAT_SUCCESS = "GET_CHAT_SUCCESS";
    public static final String GET_CHAT_FAIL = "GET_CHAT_FAIL";

    /**
     * 聊天
     */
    public void sendChat(String question) {
        Map<String, Object> map = new HashMap<>();
        map.put("question", question);
        httpService.chat(map)
                .compose(new CommonTransformer<>())
                .subscribe(new CommonSubscriber<ChatResultModel>(ActivityManager.getInstance().getAppContext()) {
                    @Override
                    public void onNext(ChatResultModel result) {
                        if (result != null && result.getOutput() != null && !result.getOutput().getChoices().isEmpty()) {
                            ChatResultModel.ChatChoices chatChoices = result.getOutput().getChoices().get(0);
                            ChatResultModel.ChatMessage message = chatChoices.getMessage();
                            if (message != null) {
                                ChatModel chatModel = new ChatModel();
                                chatModel.setMessage(message.getContent());
                                postMessageToEventBusHandler(TAG, GET_CHAT_SUCCESS, chatModel);
                                return;
                            }
                        }
                        postMessageToEventBusHandler(TAG, GET_CHAT_FAIL, "獲取失敗！");
                    }

                    @Override
                    protected void onError(ApiException e) {
                        postMessageToEventBusHandler(TAG, GET_CHAT_FAIL, e.message, e.code);
                    }
                });
    }
}
