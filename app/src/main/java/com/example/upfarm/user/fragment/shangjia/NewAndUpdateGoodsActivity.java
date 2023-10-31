package com.example.upfarm.user.fragment.shangjia;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.upfarm.R;
import com.example.upfarm.data.Product;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.domain.ImageItem;
import com.example.upfarm.imagePicket.PickerActivity;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;
import com.example.upfarm.utils.PickerConfig;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static androidx.constraintlayout.widget.Constraints.TAG;

//传图注解
@RequiresApi(api = Build.VERSION_CODES.M)

public class NewAndUpdateGoodsActivity extends Activity implements PickerConfig.OnImagesSelectedFinishedListener{

    public static final int MAX_SELECTED_COUNT = 1;
    public static final int PERMISSION_REQUEST_CODE =1;
    private ImageItem imageItem;//更换后的头像

    private ImageView iv_image;//商品图
    private EditText et_name;//商品名
    private EditText et_price;//价格
    private EditText et_quantity;//库存
    private EditText et_send_pay;//配送费
    private Button sure;
    private com.example.upfarm.sometools.getBitmap getBitmap = new getBitmap();
    private Product.ResultBean.HotBean hotBean;
    private MyRequest myRequest= new MyRequest();
    private TransmitData transmitData = new TransmitData();
    private Context mContext;
    private boolean isAdd = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_and_update_goods);
        mContext = NewAndUpdateGoodsActivity.this;
        initView();
        initValue();
        initEvent();
        //传图
        checkPermission();
        initPickerConfig();
    }

    public void initView() {
        iv_image= this.findViewById(R.id.iv_image);
        et_name = this.findViewById(R.id.et_name);
        et_price = this.findViewById(R.id.et_price);
        et_quantity = this.findViewById(R.id.et_quantity);
        et_send_pay = this.findViewById(R.id.et_send_pay);
        sure = this.findViewById(R.id.sure);
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
        Gson gson = new Gson();
//        hotBean = gson.fromJson(bundle.getString("selectSeed"), Product.ResultBean.HotBean.class);
        if (transmitData.updateGood != null) {
            hotBean = transmitData.updateGood;
            isAdd = false;
            transmitData.updateGood = null;
        }
    }

    public void initValue() {
        if (hotBean != null) {
            iv_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl() + hotBean.getProduct_imgX()));
            et_name.setText(hotBean.getProduct_nameX());
            et_price.setText(String.valueOf(hotBean.getProduct_price()));
            et_quantity.setText(String.valueOf(hotBean.getProduct_numberX()));
            et_send_pay.setText(hotBean.getProduct_descriptionX());
        }
    }

    public void initEvent() {
        //修改头像
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewAndUpdateGoodsActivity.this, PickerActivity.class));
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seedName = et_name.getText().toString();
                String price = et_price.getText().toString();
                String quantity = et_quantity.getText().toString();
                String grow = et_send_pay.getText().toString();
                TransmitData transmitData = new TransmitData();
                transmitData.fix = String.valueOf(et_quantity);
                sendRequest(seedName,price,quantity,grow);
                Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void sendRequest(String seedName, String price,String quantity, String grow) {
        if (!isAdd) {
            OkHttpUtils
                    .get()
                    .url(myRequest.getUrl() + "/sj/fix/seed")
                    .addParams("name", seedName)
                    .addParams("price", price)
                    .addParams("quantity", quantity)
                    .addParams("grow", grow)
                    .build()
                    .execute(new StringCallback() {
                        //请求失败
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e(ContentValues.TAG, "GoodsList首页请求失败==" + e.getMessage());
                        }

                        //请求成功
                        @Override
                        public void onResponse(String response, int id) {
                            Log.e(ContentValues.TAG, "GoodsList首页请求成功==" + response);
                            //解析数据
                        }

                    });
        }
        else {
            OkHttpUtils
                    .get()
                    .url(myRequest.getUrl() + "/sj/add/seed")
                    .addParams("name", seedName)
                    .addParams("price", price)
                    .addParams("quantity", quantity)
                    .addParams("grow", grow)
                    .build()
                    .execute(new StringCallback() {
                        //请求失败
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e(ContentValues.TAG, "GoodsList首页请求失败==" + e.getMessage());
                        }

                        //请求成功
                        @Override
                        public void onResponse(String response, int id) {
                            Log.e(ContentValues.TAG, "GoodsList首页请求成功==" + response);
                            //解析数据
                        }

                    });
        }
    }

    //传图
    private void initPickerConfig() {
        PickerConfig pickerConfig =PickerConfig.getInstance();
        pickerConfig.setMaxSelectedCount(MAX_SELECTED_COUNT);
        pickerConfig.setOnImagesSelectedFinishedListener(this);
    }

    //传图
    private void checkPermission() {
        int readExStoragePermissionRest = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (readExStoragePermissionRest != PackageManager.PERMISSION_GRANTED) {
            //没有权限
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    //传图
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //有权限
            } else {
                //无权限
                //根据交互去处理
            }
        }
    }

    public void pickImages(View view) {
        //打开另外一个界面
        startActivity(new Intent(NewAndUpdateGoodsActivity.this, PickerActivity.class));
    }

    @Override
    public void onSelectedFinished(List<ImageItem> result) {
        imageItem =result.get(0);
        Log.e(TAG,"item -->"+imageItem);
        setImageView(iv_image,imageItem);
    }

    public void setImageView(ImageView imageView,ImageItem imageItem) {
        iv_image.setBackground(getResources().getDrawable(R.drawable.black));
        Glide.with(imageView.getContext()).load(imageItem.getPath()).into(imageView);
    }
    @Override

    protected void onResume() {
        super.onResume();
        onCreate(null);

    }

}
