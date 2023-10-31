package com.example.upfarm;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import okhttp3.Call;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.upfarm.lwhChat.LwhChatActivity;
import com.example.upfarm.network.request.MyRequest;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends Activity {
    private EditText re_username;
    private EditText re_password;
    private EditText re_phone;
    private EditText i_code;
    private Button register_btn;
    private Button submit;
    private MyRequest myRequest = new MyRequest();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = RegisterActivity.this;
        setContentView(R.layout.activity_register);
        initView();
        initListener();

    }
    public void initView() {
        re_username = findViewById(R.id.re_username);
        re_password = findViewById(R.id.re_password);
        re_phone = findViewById(R.id.re_phone);
        i_code = findViewById(R.id.i_code);
        register_btn = findViewById(R.id.register_btn);
        submit = findViewById(R.id.submit);
    }
    public void  initListener() {
        register_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Log.e("register","请求");
//                            RequestBody requestBody = new FormBody.Builder().add("username", new_username)
//                                    .add("password", new_password).add("phone", new_phone).add("code", new_i_code).build();
//                            Request request = new Request.Builder().url(myRequest.getUrl()+"/register/user/first").post(requestBody).build();
//                            Call call = myRequest.getOkHttpClient().newCall(request);
//                            call.enqueue(new Callback() {
//                                @Override
//                                public void onFailure(Call call, IOException e) {
//                                    Log.e("onFailure", "onFailure: "+"fail");
//                                }
//
//                                @Override
//                                public void onResponse(Call call, Response response) throws IOException {
//                                    try {
//                                        if (response.isSuccessful()) {
//                                            String string = response.body().string();
//                                            JSONObject responseStr = new JSONObject(string);
//                                            Log.e("onResponse", "onResponse: " + string);
//                                            Log.e("json", "json: " + responseStr.getString("mes"));
//                                            talk(new_username,new_password);
//
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//
//                        }catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
                String new_username = re_username.getText().toString();
                String new_password = re_password.getText().toString();
                String new_phone = re_phone.getText().toString();
                String new_i_code = i_code.getText().toString();
                OkHttpUtils
                        .get()
                        .url(myRequest.getUrl()+"/register/user/first")
                        .addParams("username", new_username)
                        .addParams("password",new_password)
                        .addParams("phone",new_phone)
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
//                                processData(response);

                            }

                        });
                Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginNewActivity.class));
            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送验证码请求；
                OkHttpUtils
                        .get()
                        .url(myRequest.getUrl()+"/get/code")
                        .addParams("phone", "13835649105")
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
//                                processData(response);
                            }

                        });
            }
        });
    }
    public void talk(String new_username,String password) {
        LwhChatActivity lwhChatActivity = new LwhChatActivity();
        lwhChatActivity.register(new_username,password);
    }
}
