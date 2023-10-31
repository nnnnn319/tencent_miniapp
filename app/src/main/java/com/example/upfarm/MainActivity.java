package com.example.upfarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

import static androidx.constraintlayout.widget.Constraints.TAG;


//主界面
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //两秒种进入主页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //执行在主线程
                //启动主页面

                startActivity(new Intent(MainActivity.this,LoginNewActivity.class));

                //关闭当前页面
                finish();
            }
        },2000);
    }
}

