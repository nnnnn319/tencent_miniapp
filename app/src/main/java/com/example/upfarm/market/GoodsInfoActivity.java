package com.example.upfarm.market;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.upfarm.R;
import com.example.upfarm.data.User;
import com.example.upfarm.data.UserData;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.user.fragment.user.MyCommentActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import com.example.upfarm.base.pay.PayActivity;
import com.example.upfarm.chat.ChatActivity;
import com.example.upfarm.user.fragment.user.MyCommentActivity;

public class GoodsInfoActivity extends Activity {

    private Button buy_button;
    private Button  add_to_cart_button;
    private TextView text;
    private ImageView goods_image;
    private TextView goods_name;
    private TextView goods_price;
    private TextView goods_number;
    private TextView goods_sale_number;
    private TextView goods_farm_name;

    private MyRequest myRequest = new MyRequest();
    private TransmitData transmitData = new TransmitData();
    private Context mContext;
    private TextView test_username;
    private TextView product_description;
    private TextView back;
    /**
     * 其实是商家聊天，我也不知道为啥是这个id
     */
    private TextView back_to_cart_button;//




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info_activity);
        mContext = GoodsInfoActivity.this;
        initView();
        httpGetData();
        initProductData();
        initListener();
        back = this.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initView() {
        goods_image = (ImageView) findViewById(R.id.goods_image);
        goods_name = findViewById(R.id.goods_name);
        goods_price = findViewById(R.id.goods_price);
        goods_number = findViewById(R.id.goods_number);
        goods_sale_number = findViewById(R.id.goods_sale_number);
        goods_farm_name = findViewById(R.id.goods_farm_name);
        buy_button = this.findViewById(R.id.buy_button);
        test_username= this.findViewById(R.id.test_username);
        add_to_cart_button= this.findViewById(R.id.add_to_cart_button);
        back_to_cart_button= this.findViewById(R.id.back_to_cart_button);
        product_description = this.findViewById(R.id.product_description);
    }
    public void initListener() {
        /**
         * 注意加入购物车和立即购买跳往同一个界面
         */
        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //购买
                Intent intent = new Intent(mContext, SelectGoodsNumActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);

            }
        });
        test_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转评论
                Intent intent = new Intent(mContext, GoodsCommentActivity.class);
                intent.putExtra("type",1);
                TransmitData transmitData = new TransmitData();
                transmitData.commentType = 1;
                startActivity(intent);
            }
        });
        add_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SelectGoodsNumActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
            }
        });
        back_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initProductData() {
        String image = myRequest.getUrl()+transmitData.transmitProduct.getProductImg();
        Log.e("urlsuccess", image);
        goods_name.setText(transmitData.transmitProduct.getProductName());
        goods_sale_number.setText(String.valueOf(transmitData.transmitProduct.getProductSales())+"人付款");
        goods_number.setText("库存"+String.valueOf(transmitData.transmitProduct.getProductNumber())+"件");
        goods_price.setText("￥"+String.valueOf(transmitData.transmitProduct.getProductPrice())+"元");
        goods_image.setImageBitmap(returnBitMap(myRequest.getUrl()+transmitData.transmitProduct.getProductImg()));
        product_description.setText(transmitData.transmitProduct.getProductDescription());
    }
    public Bitmap returnBitMap(String url) {

        URL myFileUrl = null;

        Bitmap bitmap = null;

        try {

            myFileUrl = new URL(url);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

        try {

            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();

            conn.setDoInput(true);

            conn.connect();

            InputStream is = conn.getInputStream();

            bitmap = BitmapFactory.decodeStream(is);

            is.close();

        } catch (IOException e) {

            e.printStackTrace();

        }
        Log.e("bitmap",bitmap.toString());
        return  bitmap;


    }
    public void httpGetData() {
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/market/goods")
                .addParams("productId", String.valueOf(transmitData.transmitProduct.getProdcutId()))
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
                        //解析数据
                        processData(response);
                    }

                });
    }
    public void processData(String json) {
        try{
            JSONObject responseStr = new JSONObject(json);
            String farmName = responseStr.getString("farm");
            String address = responseStr.getString("address");
            goods_farm_name.setText(farmName);
        }catch (Exception e) {
            e.printStackTrace();
        }


    }
}
