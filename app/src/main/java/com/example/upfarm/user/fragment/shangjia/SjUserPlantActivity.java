package com.example.upfarm.user.fragment.shangjia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.upfarm.R;
import com.example.upfarm.data.Order;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class SjUserPlantActivity extends Activity {

    private Context mContext;
    private Order.ResultBean resultBean;//返回的数据
    private RecyclerView rv_list;
    private SjUserPlantAdapter adpter;
    private MyRequest myRequest = new MyRequest();
    private int farmOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sj_plant_order);
        mContext =  SjUserPlantActivity.this;
        Intent intent = getIntent();
        farmOrderId = intent.getIntExtra("farmOrderId",0);
//        Log.e("farmOrder", farmOrderId);
        initData();
        findViews();
    }

    public void initData() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/sj/farm/user/order")
                .addParams("farmOrderId", String.valueOf(farmOrderId))
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
                        Log.e(TAG,"sUderProduct=="+response);
                        //解析数据
                        processData(response);
                    }

                });
    }
    private void processData(String json) {
        Gson gson = new Gson();
        Order resultBeanData = gson.fromJson(json,Order.class);
        resultBean =resultBeanData.getResult();
        if(resultBean != null) {
            //有数据
            //设置适配器
            adpter = new SjUserPlantAdapter(mContext,resultBean);
            rv_list.setLayoutManager(new LinearLayoutManager(mContext));
            rv_list.setAdapter(adpter);
        }
        else{
            //没有数据
        }
    }

    public void findViews() {
        rv_list = this.findViewById(R.id.rv_list);
    }

    public void initListener() {
//        tv_new.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(mContext, InformationActivity.class);
////                startActivity(intent);
//            }
//        });
    }
}
