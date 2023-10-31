package com.example.upfarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.upfarm.data.User;
import com.example.upfarm.data.UserData;
import com.example.upfarm.network.request.MyRequest;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PwdForgetActivity extends Activity {
    private EditText phoneNum;
    private EditText iCode;
    private Button submit;
    private EditText forget_username;
    private MyRequest myRequest = new MyRequest();
    private boolean isLogin = false;
    private UserData userData;
    private TextView forgetMes;
    private Button pwd_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_forget);
        initView();
        initListener();
    }
    public void initView() {
        phoneNum = findViewById(R.id.phoneNum);
        iCode = findViewById(R.id.iCode);
        submit = findViewById(R.id.submit);
        forget_username = findViewById(R.id.fog_username);
        forgetMes = findViewById(R.id.pwd_forget);
    }
    public void initListener() {
        String phone_num = phoneNum.getText().toString();
        String i_code = iCode.getText().toString();
        String username = forget_username.getText().toString();
        forgetMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RequestBody requestBody = new FormBody.Builder().add("username",username)
                                .add("phone_num", phone_num).add("iCode", i_code).build();
                        Request request = new Request.Builder().url(myRequest.getUrl()+"/forget/password").post(requestBody).build();
                        Call call = myRequest.getOkHttpClient().newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.e("onFailure", "onFailure: "+"fail");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    if (response.isSuccessful()) {
                                        String string = response.body().string();
                                        JSONObject responseStr = new JSONObject(string);
                                        Log.e("onResponse", "onResponse: " + string);
                                        Log.e("json", "json: " + responseStr.getString("mes"));
                                        isLogin= true;
                                        User user = new User();
                                        user.setUser_id(((User) responseStr.get("user")).getUser_id());
                                        user.setUser_address(((User) responseStr.get("user")).getUser_address());
                                        user.setUser_age(((User) responseStr.get("user")).getUser_age());
                                        user.setUser_birthday(((User) responseStr.get("user")).getUser_birthday());
                                        user.setUser_name(((User) responseStr.get("user")).getUser_name());
                                        user.setUser_password(((User) responseStr.get("user")).getUser_password());
                                        user.setUser_phone(((User) responseStr.get("user")).getUser_phone());
                                        user.setUser_register_time(((User) responseStr.get("user")).getUser_register_time());
                                        userData = new UserData(user);
                                    }

                                }catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).start();
                if (isLogin) {
                    Intent intent = new Intent();
                    intent.setClass(PwdForgetActivity.this, FirstActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    forgetMes.setText("Invalid identify Code");
                }
            }
        });
//        submit.

    }
}
