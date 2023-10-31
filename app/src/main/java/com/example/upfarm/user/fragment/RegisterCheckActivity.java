package com.example.upfarm.user.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upfarm.R;
import com.example.upfarm.data.Manager;
import com.example.upfarm.lwhChat.LwhChatActivity;
import com.example.upfarm.network.request.MyRequest;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.w3c.dom.Text;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class RegisterCheckActivity extends Activity {
    private TextView register_farm_name;
    private TextView register_farm_address;
    private TextView register_real_name;
    private TextView register_identify_code;
    private TextView register_request_time;
    private Button back_to_cart_button;
    Manager.ResultBean.RequestBean requestBean;
    MyRequest myRequest = new MyRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_check);
        initView();
        initData();
        initListener();
    }
    public void initView() {
        register_farm_name = findViewById(R.id.register_farm_name);
        register_real_name = findViewById(R.id.register_real_name);
        register_farm_address = findViewById(R.id.register_farm_address);
        register_identify_code = findViewById(R.id.register_identify_code);
        register_request_time = findViewById(R.id.register_request_time);
        back_to_cart_button = findViewById(R.id.back_to_cart_button);
        Intent intent = getIntent();
        Gson gson = new Gson();
        requestBean = gson.
                fromJson(intent.getStringExtra("requestBean"),Manager.ResultBean.RequestBean.class);
    }
    public void initData() {
        register_request_time.setText("2020-07-18");
        register_identify_code.setText("1");
        register_farm_address.setText("山西省晋城市城区");
        register_farm_name.setText("小何的农场");
        register_real_name.setText("何馨雨");
    }
    public void initListener() {
        back_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LwhChatActivity lwhChatActivity = new LwhChatActivity();
                lwhChatActivity.sendTextMessage("已同意申请，请重新登录","heather");
                //发送请求变成一
                OkHttpUtils
                        .get()
                        .addParams("userId", String.valueOf(requestBean.getUser_id()))
                        .url(myRequest.getUrl()+"/register/sure")
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
                                finish();
                            }

                        });
                finish();
            }
        });
    }
}
