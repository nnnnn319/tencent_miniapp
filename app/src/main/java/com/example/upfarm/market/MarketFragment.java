package com.example.upfarm.market;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.upfarm.LoginNewActivity;
import com.example.upfarm.R;
import com.example.upfarm.base.BaseFragment;
import com.example.upfarm.data.Product;
import com.example.upfarm.data.ProductData;
import com.example.upfarm.data.User;
import com.example.upfarm.data.UserData;
import com.example.upfarm.home.fragment.adapter.HomeFragmentAdapter;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class MarketFragment extends BaseFragment {
//    private ResultBeanData.ResultBean resultBean;//返回的数据
    private Product.ResultBean resultBean;
    private RecyclerView rvMarket;
    private MarketFragmentAdapter adapter;
    private MyRequest myRequest = new MyRequest();

    @Override
    public View initView() {
        Log.e(TAG,"集市的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_market,null);
        rvMarket = view.findViewById(R.id.rv_market);
        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //okhttpUtils
        getDataFromNet();
    }
    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/market/product")
                .build()
                .execute(new StringCallback()
                {
                    //请求失败
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,"首页请求失败=="+e.getMessage());

                    }
                    //请求成功
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG,"Market请求成功=="+response);
                        //解析数据
                        processData(response);
                    }

                });

    }
    private void processData(String json) {
//        ResultBeanData resultBeanData = JSON.parseObject(json,ResultBeanData.class);
        Gson gson = new Gson();
        Product marketProduct = gson.fromJson(json,Product.class);
        resultBean =marketProduct.getResult();
        if(resultBean != null) {
            //有数据
            //设置适配器
            adapter = new MarketFragmentAdapter(mContext,resultBean);
            rvMarket.setLayoutManager(new LinearLayoutManager(mContext));
            rvMarket.setAdapter(adapter);
        }
        else{
            //没有数据
        }
        Log.e("data", "finish");
    }

    private void  initListener(){

    }
}
