package com.example.upfarm.cart.fragment;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.fastjson.JSON;
import com.example.upfarm.FirstActivity;
import com.example.upfarm.R;
import com.example.upfarm.base.BaseFragment;
import com.example.upfarm.data.Cart;
import com.example.upfarm.data.UserData;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.market.GoodsCommentAdapter;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.utils.Constants;
import com.example.upfarm.utils.LogUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class CartFragment extends BaseFragment{
    private RecyclerView rv_list;
    private Cart.ResultBean resultBean;//返回的数据
    private CartFragmentAdapter adapter;
    private MyRequest myRequest = new MyRequest();
    private UserData userData = new UserData();

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_cart,null);
        rv_list = view.findViewById(R.id.rv_list);
        initData();
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
                .url(myRequest.getUrl()+"/user/cart")
                .addParams("userId", String.valueOf(userData.user.getUser_id()))
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
                        Log.e(TAG,"首页请求成功=="+response);
                        //解析数据
                        processData(response);
                    }

                });
    }
    private void processData(String json) {
        Gson gson = new Gson();
        Cart resultBeanData = gson.fromJson(json,Cart.class);
        resultBean =resultBeanData.getResult();
        if(resultBean != null) {
            //有数据
            //设置适配器
            adapter = new CartFragmentAdapter(mContext,resultBean);
            rv_list.setLayoutManager(new LinearLayoutManager(mContext));
            rv_list.setAdapter(adapter);
        }
        else{
            //没有数据
        }
    }
}
