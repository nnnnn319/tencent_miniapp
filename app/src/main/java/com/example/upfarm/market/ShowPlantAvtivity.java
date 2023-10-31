package com.example.upfarm.market;

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
import com.example.upfarm.base.pay.PayActivity;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.user.fragment.user.AddressActivity;
import com.example.upfarm.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class ShowPlantAvtivity extends Activity {
    private ResultBeanData.ResultBean resultBean;//返回的数据
    private RecyclerView rv_list;
    private PlantAdapter adpter;
    private TextView close;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plant_avtivity);
        mContext =  ShowPlantAvtivity.this;
        initData();
        findViews();
        initListener();
    }

    public void findViews() {
        close = this.findViewById(R.id.close);
        rv_list = this.findViewById(R.id.rv_list);
    }

    public void initListener() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
//        ResultBeanData resultBeanData = JSON.parseObject(json,ResultBeanData.class);
//        resultBean =resultBeanData.getResult();
//        if(resultBean != null) {
//            //有数据
//            //设置适配器
//            adpter = new PlantAdapter(mContext,resultBean);
//            rv_list.setLayoutManager(new LinearLayoutManager(mContext));
//            rv_list.setAdapter(adpter);
//        }
//        else{
//            //没有数据
//        }
    }


}
