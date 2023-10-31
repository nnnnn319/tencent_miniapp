package com.example.upfarm.market;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.example.upfarm.R;
import com.example.upfarm.alipay.PayDemoActivity;
import com.example.upfarm.base.pay.PayActivity;
import com.example.upfarm.data.User;
import com.example.upfarm.data.UserData;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;
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
import com.example.upfarm.rent.SurePlantActivity;
import com.example.upfarm.user.fragment.user.AddressActivity;

public class SureBuyActivity extends Activity {
    private Button btPay;
    private Context mContext;
    private TextView order_sure_username;
    private TextView order_sure_phone;
    private ImageView order_sure_image;
    private TextView order_sure_number;
    private TextView order_sure_total_money;
    private TransmitData transmitData = new TransmitData();
    private MyRequest myRequest = new MyRequest();
    private getBitmap getBitmap = new getBitmap();
    private RelativeLayout select_address;
    private TextView buy_address;
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sure_buy);
        mContext = SureBuyActivity.this;
        findViews();
        initValue();
        initListener();
        back = this.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void findViews() {
        btPay = this.findViewById(R.id.button_pay);
        order_sure_username = this.findViewById(R.id.order_sure_username);
        order_sure_phone = this.findViewById(R.id.order_sure_phone);
        order_sure_image = this.findViewById(R.id.order_sure_image);
        order_sure_number = this.findViewById(R.id.order_sure_number);
        order_sure_total_money = this.findViewById(R.id.order_sure_total_money);
        select_address =this.findViewById(R.id.select_address);
        buy_address = this.findViewById(R.id.buy_address);
    }

    public void initListener() {
        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //要去请求后端返回值!!!!! 下订单 订单类型为未支付
                Log.e("后端","ff");
                requestSpring();
//                OkHttpUtils
//                        .get()
//                        .url(myRequest.getUrl()+"/cart/add")
//                        .addParams("userId",String.valueOf(.user.getUser_id()))
//                        .addParams("quantity",String.valueOf(quantity.getValue()))
//                        .build()
//                        .execute(new StringCallback()
//                        {
//                            //请求失败
//                            @Override
//                            public void onError(Call call, Exception e, int id) {
//                                Log.e(ContentValues.TAG,"商品评价请求失败=="+e.getMessage());
//                            }
//                            //请求成功
//                            @Override
//                            public void onResponse(String response, int id) {
//                                Log.e(ContentValues.TAG,"GoodsList首页请求成功=="+response);
//                                //解析数据
//                                Toast.makeText(mContext, "成功加入购物车", Toast.LENGTH_SHORT).show();
//                            }
//
//                        });
                transmitData.buy_or_product = 1;
                Intent intent = new Intent(mContext, PayDemoActivity.class);
                startActivity(intent);
                finish();
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
    public void initValue() {
        transmitData.transmitProduct.setAddress("湖南");
        Log.e("buynumber",String.valueOf(transmitData.transmitProduct.getBuyNumber()));
        Log.e("price",String.valueOf(transmitData.transmitProduct.getProductPrice()));
        order_sure_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+transmitData.transmitProduct.getProductImg()));
        order_sure_total_money.setText(String.valueOf(transmitData.transmitProduct.getProductPrice()*(transmitData.transmitProduct.getBuyNumber())));
        order_sure_number.setText(String.valueOf(transmitData.transmitProduct.getBuyNumber()));
        if (transmitData.addressPosition == -1) {
            //没有值
//            order_sure_phone.setText(transmitData.addressBean.get(0).getUser_address_phone());
//            order_sure_username.setText(transmitData.addressBean.get(0).getUser_address_name());
            //从数据库请求默认值
        } else {
                    buy_address.setText(transmitData.addressBean.get(transmitData.addressPosition).getUser_address_choose()
                +transmitData.addressBean.get(transmitData.addressPosition).getUser_address_specific());
                    order_sure_phone.setText(transmitData.addressBean.get(transmitData.addressPosition).getUser_address_phone());
            order_sure_username.setText(transmitData.addressBean.get(transmitData.addressPosition).getUser_address_name());


        }
    }
    public void requestSpring() {
        UserData userData = new UserData();
        try{
            RequestBody requestBody = new FormBody.Builder().add("userId",String.valueOf(userData.user.getUser_id()))
                    .add("productInformation", JSONArray.toJSONString(transmitData.transmitProduct))
                    .add("address1",transmitData.addressBean.get(transmitData.addressPosition).getUser_address_choose()
                    +transmitData.addressBean.get(transmitData.addressPosition).getUser_address_specific())
                    .add("phone",transmitData.addressBean.get(transmitData.addressPosition).getUser_address_phone())
                    .add("username",transmitData.addressBean.get(transmitData.addressPosition).getUser_address_name()).build();
            Request request = new Request.Builder()
                    .url(myRequest.getUrl()+"/product/order")
                    .post(requestBody).build();
            Call call = myRequest.getOkHttpClient().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("onFailure", "onFailure: "+"fail");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override

    protected void onResume() {

        super.onResume();

        onCreate(null);

    }
}
