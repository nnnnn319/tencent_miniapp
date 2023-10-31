package com.example.upfarm.user.fragment.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.upfarm.R;
import com.example.upfarm.data.Comment;
import com.example.upfarm.data.UserData;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class MyCommentActivity extends Activity {

    private Comment.ResultBean resultBean;//返回的数据
    private RecyclerView rv_list;
    private MyCommentAdapter adpter;
    private Context mContext;
    private MyRequest myRequest = new MyRequest();
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);
        mContext = MyCommentActivity.this;
        initData();
        findViews();
        initListener();
        back = this.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void findViews() {
        rv_list = this.findViewById(R.id.rv_list);
    }

    public void initListener() {
//        .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, NewAddressActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public void initData() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/comment/user/get")
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
        Comment resultBeanData = gson.fromJson(json,Comment.class);
        resultBean =resultBeanData.getResult();
        if(resultBean != null) {
            //有数据
            //设置适配器
            adpter = new MyCommentAdapter(mContext,resultBean);
            rv_list.setLayoutManager(new LinearLayoutManager(mContext));
            rv_list.setAdapter(adpter);
        }
        else{
            //没有数据
        }
    }
    @Override

    protected void onResume() {

        super.onResume();

        onCreate(null);

    }
}
