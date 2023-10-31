package com.example.upfarm.rent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.upfarm.R;
import com.example.upfarm.base.pay.PayActivity;
import com.example.upfarm.data.Product;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.user.fragment.manager.MFarmActivityAdapter;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class SelectSeedActivity extends Activity {
    private Context mContext;
    private Product.ResultBean resultBean;//返回的数据
    private RecyclerView rv_list;
    private SelectSeedAdapter adpter;
    private TextView total;
    private Button btPay;
    private TransmitData transmitData = new TransmitData();
    private MyRequest myRequest = new MyRequest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seed);
        mContext = SelectSeedActivity.this;
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
                .url(myRequest.getUrl()+"/market/rent/seed")
                .addParams("farmId", String.valueOf(transmitData.rentGroundpositon))
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
        Product product = gson.fromJson(json,Product.class);
        resultBean =product.getResult();
        if(resultBean != null) {
            //有数据
            //设置适配器
            adpter = new SelectSeedAdapter(mContext,resultBean,total);
            rv_list.setLayoutManager(new LinearLayoutManager(mContext));
            rv_list.setAdapter(adpter);
        }
        else{
            //没有数据
        }
    }

    public void findViews() {
        total = this.findViewById(R.id.total);
        btPay = this.findViewById(R.id.pay);
        rv_list = this.findViewById(R.id.rv_list);
    }

    public void initListener() {
        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SurePlantActivity.class);
                startActivity(intent);
            }
        });
    }
}
