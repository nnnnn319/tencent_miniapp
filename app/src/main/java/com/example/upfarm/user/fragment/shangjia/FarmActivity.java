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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.upfarm.R;
import com.example.upfarm.data.Product;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.user.fragment.InformationActivity;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class FarmActivity extends Activity {
    private TextView tv_new;
    private Context mContext;
    private Product.ResultBean resultBean;//返回的数据
    private RecyclerView rv_goods;
    private FarmActivityAdapter adpter;
    private MyRequest myRequest = new MyRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm);
        mContext =  FarmActivity.this;
        initData();
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
                .url(myRequest.getUrl()+"/sj/farm")
                .addParams("storeId", "1")
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
        Product resultBeanData = gson.fromJson(json, Product.class);
        resultBean =resultBeanData.getResult();
        if(resultBean != null) {
            //有数据
            //设置适配器
            adpter = new FarmActivityAdapter(mContext,resultBean);
            rv_goods.setLayoutManager(new LinearLayoutManager(mContext));
            rv_goods.setAdapter(adpter);
        }
        else{
            //没有数据
        }
    }

    public void findViews() {
        tv_new = this.findViewById(R.id.tv_new);
        rv_goods = this.findViewById(R.id.rv_goods);
    }

    public void initListener() {
        tv_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewAndUpdateSeedActivity.class);
                intent.putExtra("type", 3);
                startActivity(intent);
            }
        });
    }
    @Override

    protected void onResume() {
        Log.e("refresh", "aaa");
        super.onResume();
        getDataFromNet();
        onCreate(null);
    }
}
