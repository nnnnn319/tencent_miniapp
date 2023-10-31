package com.example.upfarm.home.fragment;

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
import com.example.upfarm.home.fragment.adapter.HomeFragmentAdapter;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.lwhChat.LwhChatActivity;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
/*
 JSON
   code
   msg
   result：
    act_info
    banner_info
    channel_info
    hot_info
    recommend_info
    seckill_info
<<<<<<< HEAD

=======
>>>>>>> 8bca9c107342e30ab319d7c6d18739779588ae8b
 */

//首页
public class HomeFragment extends BaseFragment {
    private MyRequest myRequest = new MyRequest();
    private ResultBeanData.ResultBean resultBean;//返回的数据
    private RecyclerView rvHome;
    private HomeFragmentAdapter adapter;

    //new
    private Product.ResultBean dataBean;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home,null);
        rvHome = view.findViewById(R.id.rv_home);
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
                .url(myRequest.getUrl()+"/home/show")
                .build()
                .execute(new StringCallback()
                {
                    //请求失败
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        Log.e(TAG,"首页请求失败=="+e.getMessage());
                    }
                    //请求成功
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("talk","talk");
                        Log.e(TAG,"首页请求成功=="+response);

                        //解析数据
                        processData(response);
                    }

                });

    }


    private void  initListener(){


    }


    private void processData(String json){
        try {
            Gson gson = new Gson();
            Product product = gson.fromJson(json,Product.class);

//            Log.e("response",responseStr.get("result").toString());
            dataBean = product.getResult();

            if(dataBean != null) {
                //有数据
                //设置适配器
//                Log.e("dataBean",dataBean.getTop().get(0).getProduct_nameX());
                adapter = new HomeFragmentAdapter(mContext,dataBean);
                rvHome.setLayoutManager(new LinearLayoutManager(mContext));
                rvHome.setAdapter(adapter);
            }
            else {
                //没有数据
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void talk() {
        Log.e("talk","talk");
        LwhChatActivity lwhChatActivity = new LwhChatActivity();
        lwhChatActivity.login("lwh","lwh");
    }
}
