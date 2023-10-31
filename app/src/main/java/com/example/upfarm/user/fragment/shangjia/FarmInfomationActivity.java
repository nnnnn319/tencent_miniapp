package com.example.upfarm.user.fragment.shangjia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.upfarm.R;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.data.transmit.TransmitFarm;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class FarmInfomationActivity extends Activity {
    private Context mContext;
    private Button update;
    private ImageView iv_image;
    private TextView tv_name;
    private TextView tv_address;
    private TextView tv_status;
    private MyRequest myRequest = new MyRequest();
    private com.example.upfarm.sometools.getBitmap getBitmap = new getBitmap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_infomation);
        mContext = FarmInfomationActivity.this;
        initView();
        initData();
        findViews();
        initListener();
    }
    public void initView() {
        iv_image = findViewById(R.id.iv_image);
        tv_name = findViewById(R.id.tv_name);
        tv_address = findViewById(R.id.tv_address);
        tv_status = findViewById(R.id.tv_status);
    }
    public void initData() {
        httpRequest();
    }

    public void findViews() {
        update = this.findViewById(R.id.update);
    }

    public void initListener() {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateFarmActivity.class);
                startActivity(intent);
            }
        });
    }
    public void httpRequest() {
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/sj/farm/information")
                .addParams("userId", "2")
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
    public void processData(String json) {
        Gson gson = new Gson();
        TransmitFarm transmitFarm = new TransmitFarm();
        transmitFarm = gson.fromJson(json,TransmitFarm.class);
//        Log.e("transmitFarm", transmitFarm.getFarmInformation().getFarm_name());
        if (transmitFarm.getFarmInformation() == null) {
            tv_name.setText("没有农场");
        } else {
            TransmitData transmitData = new TransmitData();
            transmitData.transmitFarm = transmitFarm;
            iv_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+transmitFarm.getFarmInformation().getFarm_img()));
            tv_name.setText(transmitFarm.getFarmInformation().getFarm_name());
            tv_address.setText(transmitFarm.getFarmInformation().getFarm_x());
            if (transmitFarm.getFarmInformation().getFarm_type()==0) {
                tv_status.setText("没有空余场地");
            } else {
                tv_status.setText("有空闲场地");
            }
        }
    }
    @Override

    protected void onResume() {

        super.onResume();

        onCreate(null);

    }
}
