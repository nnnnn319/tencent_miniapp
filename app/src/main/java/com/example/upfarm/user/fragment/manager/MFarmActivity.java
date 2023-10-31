package com.example.upfarm.user.fragment.manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.upfarm.R;
import com.example.upfarm.data.Manager;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.user.fragment.shangjia.FarmActivity;
import com.example.upfarm.user.fragment.shangjia.FarmActivityAdapter;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class MFarmActivity extends Activity {

    private Context mContext;
    private Manager.ResultBean resultBean;//返回的数据
    private RecyclerView rv_list;
    private MFarmActivityAdapter adpter;
    private MyRequest myRequest = new MyRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mfarm);
        mContext =  MFarmActivity.this;
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
                .url(myRequest.getUrl()+"/store/farm")
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
        Manager manager = gson.fromJson(json,Manager.class);
//        ResultBeanData resultBeanData = JSON.parseObject(json,ResultBeanData.class);
        resultBean =manager.getResult();
        if(resultBean != null) {
            //有数据
            //设置适配器
            adpter = new MFarmActivityAdapter(mContext,resultBean);
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

    @Override

    protected void onResume() {

        super.onResume();

        onCreate(null);

    }
}
