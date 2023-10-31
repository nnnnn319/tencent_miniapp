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
import com.example.upfarm.lwhChat.LwhChatActivity;
import com.example.upfarm.network.request.MyRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginNewActivity extends Activity {
    private boolean isUser=false;
    private boolean isBack=false;
    private EditText loginUsername;
    private EditText loginPassword;
    private Button loginBtn;
    private TextView toRegister;
    private TextView toFixPwd;
    private TextView message;
    private MyRequest myRequest = new MyRequest();
    private UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        initView();
        initListener();
    }
    public void initView() {
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.loginButton);
        toRegister = findViewById(R.id.goToRegister);
        toFixPwd = findViewById(R.id.goToFixPwd);
        message = findViewById(R.id.message);
    }
    public void initListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();
                Log.d(username+password,username);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            RequestBody requestBody = new FormBody.Builder().add("username",username).add("password",password).build();
                            Request request = new Request.Builder()
                                    .url(myRequest.getUrl()+"/login")
                                    .post(requestBody).build();
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
                                            isBack = true;
                                            String string = response.body().string();
                                            JSONObject responseStr = new JSONObject(string);
                                            Log.e("onResponse", "onResponse: " + string);
//                                            Log.e("json", "json: " + responseStr.getString("mes"));
                                            isUser = true;
                                            User user = new User();
                                            JSONObject responseStrUser = new JSONObject(responseStr.get("user").toString());
                                            Log.e("a",responseStrUser.getString("user_id"));
                                            user.setUser_id((int)responseStrUser.get("user_id"));
                                            user.setUser_address((String)responseStrUser.get("user_address"));
                                            user.setUser_age((int)responseStrUser.get("user_age"));
                                            user.setUser_birthday((String) responseStrUser.get("user_birthday"));
                                            user.setUser_name((String)responseStrUser.get("user_name"));
                                            user.setUser_password((String)responseStrUser.get("user_password"));
                                            user.setUser_phone((String)responseStrUser.get("user_phone"));
                                            user.setUser_register_time((String) responseStrUser.get("user_register_time"));
                                            user.setUser_type((int)responseStrUser.get("user_type"));
//                                            user.setUser_register_time((String)responseStrUser.get("register_time"));
                                            userData = new UserData(user);
                                            Log.e("test",userData.user.getUser_password());
                                            talk(user.getUser_name(),user.getUser_password());
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                if (isBack) {
                    if (isUser) {
                        startActivity(new Intent(LoginNewActivity.this, FirstActivity.class));
                        //关闭当前页面
                        finish();
                    } else {
                        message.setText("Invalid username or password");
                    }
                } else {
//                    message.setText("waiting");

                }
            }
        });
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("register","register");
                startActivity(new Intent(LoginNewActivity.this, RegisterActivity.class));
                //关闭当前页面
                finish();
            }
        });
        toFixPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("fix ","password");
                startActivity(new Intent(LoginNewActivity.this, PwdForgetActivity.class));
                //关闭当前页面
                finish();
            }
        });
    }
    public void talk(String username,String password) {
        LwhChatActivity  lwhChatActivity = new LwhChatActivity();
        lwhChatActivity.login(username,password);
    }
}

