package com.example.upfarm.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.upfarm.R;
import com.example.upfarm.data.Product;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.home.fragment.adapter.HomeFragmentAdapter;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class GoodsListActivity extends Activity {
    private Product.ResultBean resultBean;//返回的数据
    private RecyclerView rvGoodList;
    private GoodsListAdapter goodsListAdapter;
    private Context mContext;
    private MyRequest myRequest = new MyRequest();
    public TransmitData transmitData = new TransmitData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        rvGoodList = this.findViewById(R.id.rv_good_lists);
        mContext = GoodsListActivity.this;
        initData();
    }


    public void initData() {
        //okhttpUtils
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/market/product/type")
                .addParams("type", String.valueOf(transmitData.marketChnannelType+1))
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
        Product resultBeanData = gson.fromJson(json,Product.class);
//        Product resultBeanData = JSON.parseObject(json,Product.class);
        resultBean =resultBeanData.getResult();
        if(resultBean != null) {
            //有数据
            //设置适配器
            goodsListAdapter = new GoodsListAdapter(mContext,resultBean);
            rvGoodList.setLayoutManager(new LinearLayoutManager(mContext));
            rvGoodList.setAdapter(goodsListAdapter);
        }
        else{
            //没有数据
        }
    }

}
