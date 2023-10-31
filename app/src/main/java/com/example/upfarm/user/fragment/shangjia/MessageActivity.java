package com.example.upfarm.user.fragment.shangjia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.upfarm.LoginNewActivity;
import com.example.upfarm.R;
import com.example.upfarm.data.Message;
import com.example.upfarm.data.UserData;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.market.GoodsCommentActivity;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class MessageActivity extends Activity {
    private Context mContext;
    private Message.ResultBean resultBean;//返回的数据
//    private RecyclerView rv_list;
    private MessageAdapter adpter;
    private MyRequest myRequest = new MyRequest();
    private TransmitData transmitData = new TransmitData();
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_message_grid_view);
        mContext =  MessageActivity.this;
        image=this.findViewById(R.id.image);
//        findViews();
        initData();
        initListener();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LoginNewActivity.class);
//                startActivity(new Intent(MessageActivity.this, LoginNewActivity.class));
//                intent.putExtra("position", position);
                mContext.startActivity(intent);
                //退出登录 成为管理员
                Activity activity = (Activity) mContext;
                activity.finish();
            }
        });
    }
    public void initData() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/from/message")
                .addParams("userId", String.valueOf(UserData.user.getUser_id()))
                .build()
                .execute(new StringCallback()
                {
                    //请求失败
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,"GoodsList首页请求失败=="+e.getMessage());
                    }
                    //请求成功
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG,"GoodsList首页请求成功=="+response);
                        //解析数据
                        processData(response);
                    }

                });
    }
    private void processData(String json) {
        Gson gson = new Gson();
        Message resultBeanData = gson.fromJson(json,Message.class);
        resultBean =resultBeanData.getResult();
        if(resultBean != null) {
            //有数据
            //设置适配器
            adpter = new MessageAdapter(mContext,resultBean);
//            rv_list.setLayoutManager(new LinearLayoutManager(mContext));
//            rv_list.setAdapter(adpter);
        }
        else{
            //没有数据
        }
    }

//    public void findViews() {
//        rv_list = this.findViewById(R.id.rv_list);
//    }

    public void initListener() {

    }

    //接收到别人发送的信息后进行的回调
    @Override
    public void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                for (EMMessage message:list) {
                    Log.e("qq接收消息成功",message.getFrom());
                    if (message.getType()== EMMessage.Type.VOICE){
                        String url = ((EMVoiceMessageBody)message.getBody()).getRemoteUrl();
                        int  length = ((EMVoiceMessageBody)message.getBody()).getLength();
                        String fromUser = message.getFrom();
                        transmitData.textMessage.add(url);
                        transmitData.lengths.add(length);
                        transmitData.sendUsername.add(fromUser);
                        Log.e("qq接收消息成功",url+","+length+","+fromUser);
                    }else if (message.getType()== EMMessage.Type.TXT){
                        String text=((EMTextMessageBody) message.getBody()).getMessage();
                        Log.e("qq接收文本消息成功",text);
                        transmitData.textMessage.add(text);
                        transmitData.lengths.add(-1);
                        transmitData.sendUsername.add(message.getFrom());
                    }else if (message.getType()== EMMessage.Type.IMAGE){

                    }

                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageRecalled(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {

            }
        });
    }
}
