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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.upfarm.R;
import com.example.upfarm.alipay.PayDemoActivity;
import com.example.upfarm.base.pay.PayActivity;
import com.example.upfarm.data.Product;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.market.GoodsCommentAdapter;
import com.example.upfarm.market.PlantAdapter;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.user.fragment.RegisterListActivity;
import com.example.upfarm.user.fragment.user.AddressActivity;
import com.example.upfarm.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class SurePlantActivity extends Activity {
    private Product.ResultBean resultBean;//返回的数据
    private RecyclerView rv_list;
    private PlantAdapter adpter;
    private Button btPay;
    private Context mContext;
    private RelativeLayout select_address;
    private TextView seed_address;
    private TextView seed_name;
    private TextView seed_phone;
    private TransmitData transmitData = new TransmitData();
    private MyRequest myRequest = new MyRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sure_plant);
        mContext = SurePlantActivity.this;
        findViews();
        initData();
        initListener();
    }
    public void initData() {
        getDataFromNet();
        if (transmitData.addressPosition == -1) {
            //没有值
//            order_sure_phone.setText(transmitData.addressBean.get(0).getUser_address_phone());
//            order_sure_username.setText(transmitData.addressBean.get(0).getUser_address_name());
            //从数据库请求默认值
        } else {
            seed_address.setText(transmitData.addressBean.get(transmitData.addressPosition).getUser_address_choose()
                    +transmitData.addressBean.get(transmitData.addressPosition).getUser_address_specific());
            seed_phone.setText(transmitData.addressBean.get(transmitData.addressPosition).getUser_address_phone());
            seed_name.setText(transmitData.addressBean.get(transmitData.addressPosition).getUser_address_name());
        }
    }

    private void getDataFromNet() {
        processData();
    }
    private void processData() {
        Product resultBeanData = new Product();
        Product.ResultBean resultBean = new Product.ResultBean();
        resultBeanData.setResult(resultBean);
        resultBean.setHot(transmitData.hotBeans);
        if(resultBean != null) {
            //有数据
            //设置适配器
            adpter = new PlantAdapter(mContext,resultBean);
            rv_list.setLayoutManager(new LinearLayoutManager(mContext));
            rv_list.setAdapter(adpter);
        }
        else{
            //没有数据
        }
    }

    public void findViews() {
        btPay = this.findViewById(R.id.button_pay);
        select_address =this.findViewById(R.id.select_address);
        rv_list = this.findViewById(R.id.rv_list);
        seed_address = this.findViewById(R.id.seed_address);
        seed_name = this.findViewById(R.id.seed_name);
        seed_phone = this.findViewById(R.id.seed_phone);
    }

    public void initListener() {
        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, PayActivity.class);
//                startActivity(intent);
                httpRequest();
                transmitData.buy_or_product = 2;
                Intent intent = new Intent(mContext, PayDemoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("totalPrice", String.valueOf(109));
                bundle.putString("imageNum",String.valueOf(11));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddressActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override

    protected void onResume() {

        super.onResume();

        onCreate(null);

    }
    public void httpRequest() {
        for (int i=0;i<transmitData.hotBeans.size();i++) {
            if (i==0) {
                OkHttpUtils
                        .get()
                        .url(myRequest.getUrl() + "/buy/seed")
                        .addParams("farmId", String.valueOf(transmitData.hotBeans.get(i).getStore_idX()))
                        .addParams("userId", "1")
                        .addParams("quantity", String.valueOf(transmitData.hotBeans.get(i).getProduct_numberX()))
                        .addParams("productId", String.valueOf(transmitData.hotBeans.get(i).getProduct_idX()))
                        .addParams("same_order",String.valueOf(transmitData.hotBeans.size()))
                        .addParams("address",String.valueOf(transmitData.addressBean.get(transmitData.addressPosition).getUser_address_choose()
                                +transmitData.addressBean.get(transmitData.addressPosition).getUser_address_specific()))
                        .addParams("phone",transmitData.addressBean.get(transmitData.addressPosition).getUser_address_phone())
                        .addParams("username",transmitData.addressBean.get(transmitData.addressPosition).getUser_address_name())
                        .build()
                        .execute(new StringCallback() {
                            //请求失败
                            @Override
                            public void onError(Call call, Exception e, int id) {
//                        Log.e(TAG,"首页请求失败=="+e.getMessage());
                            }

                            //请求成功
                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("rent请求", "rent请求成功");
                                //解析数据

                            }

                        });
            }
            else {
                OkHttpUtils
                        .get()
                        .url(myRequest.getUrl() + "/buy/seed")
                        .addParams("farmId", String.valueOf(transmitData.hotBeans.get(i).getStore_idX()))
                        .addParams("userId", "1")
                        .addParams("quantity", String.valueOf(transmitData.hotBeans.get(i).getProduct_numberX()))
                        .addParams("productId", String.valueOf(transmitData.hotBeans.get(i).getProduct_idX()))
                        .addParams("same_order",String.valueOf(-1))
                        .build()
                        .execute(new StringCallback() {
                            //请求失败
                            @Override
                            public void onError(Call call, Exception e, int id) {
//                        Log.e(TAG,"首页请求失败=="+e.getMessage());
                            }

                            //请求成功
                            @Override
                            public void onResponse(String response, int id) {
                                Log.e("rent请求", "rent请求成功");
                                //解析数据

                            }

                        });
            }
        }
    }
}
