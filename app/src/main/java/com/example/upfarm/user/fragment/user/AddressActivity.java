package com.example.upfarm.user.fragment.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.upfarm.R;
import com.example.upfarm.data.User;
import com.example.upfarm.data.UserAddress;
import com.example.upfarm.data.UserData;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.market.SelectGoodsNumActivity;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.user.fragment.shangjia.NewAndUpdateGoodsActivity;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.hengyi.wheelpicker.listener.OnCityWheelComfirmListener;
import com.hengyi.wheelpicker.ppw.CityWheelPickerPopupWindow;
import com.hmy.popwindow.window.PopDownWindow;
import com.hxb.hxbaddressselectionviewlibrary.view.AddressPickerPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class AddressActivity extends Activity {

    private UserAddress.ResultBean resultBean;//返回的数据
    private RecyclerView rv_list;
    private AddressAdapter adpter;
    private TextView add;
    private TextView sure;
    private  Context mContext;
    private MyRequest myRequest = new MyRequest();
    int type=-1;
    TransmitData transmitData = new TransmitData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        mContext = AddressActivity.this;
        initData();
        findViews();
        initListener();
        transmitData.type_address = getIntent().getIntExtra("type",-1);
    }

    public void findViews() {
        add = this.findViewById(R.id.add);
        rv_list = this.findViewById(R.id.rv_list);
        sure = this.findViewById(R.id.sure);
    }

   public void initListener() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewAddressActivity.class);
                startActivity(intent);
            }
        });
       sure.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                finish();
           }
       });

   }

    public void initData() {
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/user/address")
                .addParams("userId", String.valueOf(UserData.user.getUser_id()))
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
        TransmitData transmitData = new TransmitData();
        Gson gson = new Gson();
        UserAddress resultBeanData = gson.fromJson(json, UserAddress.class);
        resultBean =resultBeanData.getResult();
        if(resultBean != null) {
            //有数据
            //设置适配器
            adpter = new AddressAdapter(mContext,resultBean);
            rv_list.setLayoutManager(new LinearLayoutManager(mContext));
            rv_list.setAdapter(adpter);
            transmitData.addressBean = resultBeanData.getResult().getAddress();
        }
        else{
            //没有数据
        }
    }

    @Override

    protected void onResume() {
        Log.e("refresh", "aaa");
        super.onResume();
        onCreate(null);
    }


}
