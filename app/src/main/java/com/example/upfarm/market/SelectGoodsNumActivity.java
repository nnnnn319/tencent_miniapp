package com.example.upfarm.market;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upfarm.R;
import com.example.upfarm.data.UserData;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;

import com.example.upfarm.cart.fragment.view.AddSubView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

public class SelectGoodsNumActivity extends Activity {
    private TextView buy_button;
    private Context mContext;
    private ImageView product_image;
    private TextView product_price;
    private TextView product_number;
    private EditText buy_number;
    private getBitmap getBitmap = new getBitmap();
    private MyRequest myRequest = new MyRequest();
    TransmitData transmitData = new TransmitData();
    private AddSubView quantity;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_goods_num);
        mContext = SelectGoodsNumActivity.this;
        findViews();
        intiValue();
        initListener();
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().getAttributes().gravity= Gravity.BOTTOM;

    }
    public void findViews() {
        quantity = this.findViewById(R.id.quantity);
        buy_button = this.findViewById(R.id.sure);
        product_image = this.findViewById(R.id.product_image);
        product_number = this.findViewById(R.id.product_number);
        product_price = this.findViewById(R.id.product_price);
        total = this.findViewById(R.id.total);
    }

    public void initListener() {
        quantity.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                Log.w(TAG,"123345567");
                //单价*数量
                total.setText(String.valueOf(quantity.getValue()*transmitData.transmitProduct.getProductPrice()));
                //数量的值
                Log.w(TAG,quantity.getValue()+"");
            }
        });

        /**
         * 如果是从立即购买而来，跳SureBuyActivity
         * 如果是从购物车来，直接提示信息，加入购物车成功， finish()关掉
         */
        buy_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent_pre = getIntent();
                int type = intent_pre.getIntExtra("type",0);
                if (type == 1) {
                    //直接购买来的
                    Intent intent = new Intent(mContext, SureBuyActivity.class);
                    startActivity(intent);
                } else {
                    sendHttpRequest();
                    //购物车来的
                    //这里要向后端发送插入购物车请求
                    finish();
                }

            }
        });

    }
    public void intiValue() {
        quantity.setValue(1);
        product_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+transmitData.transmitProduct.getProductImg()));
        product_number.setText("库存： "+String.valueOf(transmitData.transmitProduct.getProductNumber()));
        product_price.setText(String.valueOf(transmitData.transmitProduct.getProductPrice())+"元");
        total.setText("￥"+transmitData.transmitProduct.getProductPrice()+"元");
        transmitData.transmitProduct.setBuyNumber(quantity.getValue());
    }
    public void sendHttpRequest() {
        UserData userData = new UserData();
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/cart/add")
                .addParams("productId", String.valueOf(transmitData.transmitProduct.getProdcutId()))
                .addParams("userId",String.valueOf(userData.user.getUser_id()))
                .addParams("quantity",String.valueOf(quantity.getValue()))
                .build()
                .execute(new StringCallback()
                {
                    //请求失败
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(ContentValues.TAG,"商品评价请求失败=="+e.getMessage());
                    }
                    //请求成功
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(ContentValues.TAG,"GoodsList首页请求成功=="+response);
                        //解析数据
                        Toast.makeText(mContext, "成功加入购物车", Toast.LENGTH_SHORT).show();
                    }

                });
    }
}
