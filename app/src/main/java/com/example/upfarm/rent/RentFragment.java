package com.example.upfarm.rent;
import android.util.Log;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.JSON;
import com.example.upfarm.R;
import com.example.upfarm.base.BaseFragment;
import com.example.upfarm.data.Product;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;


public class RentFragment extends BaseFragment {
    private Product.ResultBean resultBean;//返回的数据
    private RecyclerView rvRent;
    private RentFragmentAdapter adapter;
    private MyRequest myRequest = new MyRequest();

        @Override
        public View initView() {
            View view = View.inflate(mContext, R.layout.fragment_rent,null);
            rvRent = view.findViewById(R.id.rv_rent);
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
                .url(myRequest.getUrl()+"/market/rent")
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
                        Log.e("rent请求","rent请求成功");
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
            adapter = new RentFragmentAdapter(mContext,resultBean);
            rvRent.setLayoutManager(new LinearLayoutManager(mContext));
            rvRent.setAdapter(adapter);
        }
        else{
            //没有数据
        }
    }
    private void  initListener(){

    }
}
