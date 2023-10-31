package com.example.upfarm.user.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.upfarm.R;
import com.example.upfarm.data.Manager;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.market.GoodsListActivity;
import com.example.upfarm.market.GoodsListAdapter;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.GenericSignatureFormatError;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class RegisterListActivity extends Activity {
    private Manager.ResultBean resultBean;//返回的数据
    private RecyclerView rv_register_list;
    private RegisterListAdapter adpter;
    private Context mContext;
    private MyRequest myRequest = new MyRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_list);
        rv_register_list = this.findViewById(R.id.rv_register_list);
        mContext = RegisterListActivity.this;
        initData();
    }
    public void initData() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/store/request")
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
        Manager resultBeanData = gson.fromJson(json,Manager.class);
        resultBean =resultBeanData.getResult();
        if(resultBean != null) {
            //有数据
            //设置适配器
            adpter = new RegisterListAdapter(mContext,resultBean);
            rv_register_list.setLayoutManager(new LinearLayoutManager(mContext));
            rv_register_list.setAdapter(adpter);
        }
        else{
            //没有数据
        }
    }
}
