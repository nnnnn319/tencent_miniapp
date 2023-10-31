package com.example.upfarm.rent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upfarm.R;
import com.example.upfarm.data.Product;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class LandSelectionActivity extends Activity {

    private Product.ResultBean resultBean;//返回的数据
    private RecyclerView rvSeed;
    private LandSelectionAdapter landSelectAdapter;
    private Context mContext;
    private MyRequest myRequest = new MyRequest();
    private Button btSurePlant;
    private TransmitData transmitData = new TransmitData();

    private TextView landName;
    private TextView landAddress;
    private ImageView imageView;
    private TextView landStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_selection_activty);
        findViews();
        initListener();
        mContext = LandSelectionActivity.this;
        initData();
        //换具体值
    }

    public void findViews() {
        rvSeed = this.findViewById(R.id.rv_seed);
        btSurePlant = this.findViewById(R.id.bt_sure_plant);

        //设置
        landName = this.findViewById(R.id.landName);
        landAddress = this.findViewById(R.id.landAddress);
        imageView = this.findViewById(R.id.farm_img);
        landStatus = this.findViewById(R.id.landStatus);

    }

    public void initListener(){
        btSurePlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SelectSeedActivity.class);
                startActivity(intent);
            }
        });
    }


    public void initData() {
        //okhttpUtils
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/market/rent/seed")
                .addParams("farmId", String.valueOf(transmitData.rentGroundpositon))
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
        //根据farmid获取名字和地址
      OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/market/farm/information")
                .addParams("farmId", String.valueOf(transmitData.rentGroundpositon))
                .build()
                .execute(new StringCallback()
                {
                    //请求失败
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,"FarmInformation=="+e.getMessage());
                    }
                    //请求成功
                    @Override
                    public void onResponse(String response, int id) {
                        //解析数据
                        setTextViewName(response);
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
            landSelectAdapter = new LandSelectionAdapter(mContext,resultBean);
            rvSeed.setLayoutManager(new LinearLayoutManager(mContext));
            rvSeed.setAdapter(landSelectAdapter);
        }
        else{
            //没有数据
        }
    }

    private void setTextViewName(String json) {
        try {
            Log.e("responseStr", json);
            JSONObject responseStr = new JSONObject(json);
            String farmName = responseStr.getString("farm");
            String address = responseStr.getString("address");
            String image = myRequest.getUrl()+responseStr.getString("image");
            landName.setText(farmName);
            landAddress.setText(address);
            imageView.setImageBitmap(returnBitMap(image));
            if (responseStr.getInt("status")==0) {
                landStatus.setText("已种植满");
                btSurePlant.setEnabled(false);
                Toast.makeText(mContext, "已经种植满", Toast.LENGTH_SHORT).show();
            } else {
                landStatus.setText("有空闲");
            }
            Log.e("urlsuccess", image);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

                return  bitmap;


    }




}

