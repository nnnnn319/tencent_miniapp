package com.example.upfarm.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.upfarm.R;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.lwhChat.LwhChatActivity;
import com.example.upfarm.lwhChat.MediaRecordingUtils;
import com.example.upfarm.map.MapRoutingActivity;
import com.example.upfarm.rent.SelectSeedActivity;
import com.example.upfarm.rent.SelectSeedAdapter;
import com.example.upfarm.rent.SurePlantActivity;
import com.example.upfarm.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class ChatActivity extends Activity {
    private Context mContext;
    private ResultBeanData.ResultBean resultBean;//返回的数据
    private RecyclerView rv_list;
    private ChatAdapter adpter;
    private TextView shangjia_name;
    private EditText input;
    private Button send;
    private Button voice;
    private Button voice_1;

    String address = " ";
    long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mContext = ChatActivity.this;
//        initData();
        findViews();
        initListener();
    }

    public void initData() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .addParams("username", "hyman")
                .addParams("password", "123")
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
        ResultBeanData resultBeanData = JSON.parseObject(json,ResultBeanData.class);
        resultBean =resultBeanData.getResult();
        if(resultBean != null) {
            //有数据
            //设置适配器
            adpter = new ChatAdapter(mContext,resultBean);
            rv_list.setLayoutManager(new LinearLayoutManager(mContext));
            rv_list.setAdapter(adpter);
        }
        else{
            //没有数据
        }
    }

    public void findViews() {
        shangjia_name = this.findViewById(R.id.shangjia_name);
        send = this.findViewById(R.id.send);
        input = this.findViewById(R.id.input);
        rv_list = this.findViewById(R.id.rv_list);
        voice = this.findViewById(R.id.voice);
        voice_1 = this.findViewById(R.id.voice_1);
    }

    public void initListener() {
        MediaRecordingUtils mediaRecordingUtils = new MediaRecordingUtils();
        mediaRecordingUtils.setOnAudioStatusUpdateListener(new MediaRecordingUtils.OnAudioStatusUpdateListener() {
            @Override
            public void onUpdate(double db, long time) {

            }

            @Override
            public void onStop(String filePath) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LwhChatActivity lwhChatActivity = new LwhChatActivity();
                lwhChatActivity.sendTextMessage("hello","x");
            }
        });
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MediaRecordingUtils mediaRecordingUtils = new MediaRecordingUtils();
                address = mediaRecordingUtils.startRecord();
            }
        });
        voice_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MediaRecordingUtils mediaRecordingUtils = new MediaRecordingUtils();
                endTime = mediaRecordingUtils.stopRecord();
                LwhChatActivity lwhChatActivity = new LwhChatActivity();
                lwhChatActivity.sendVoiceMessage(address,(int)(endTime),"x",mContext);
                lwhChatActivity.isPlayOrPauseLocal(address,mContext);
            }
        });


    }
}
