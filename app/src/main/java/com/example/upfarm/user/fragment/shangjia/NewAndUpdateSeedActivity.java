package com.example.upfarm.user.fragment.shangjia;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
//传图注解
@RequiresApi(api = Build.VERSION_CODES.M)

public class NewAndUpdateSeedActivity extends Activity implements PickerConfig.OnImagesSelectedFinishedListener{
    public static final int MAX_SELECTED_COUNT = 1;
    public static final int PERMISSION_REQUEST_CODE =1;
    private ImageItem imageItem;//更换后的头像

    private ImageView iv_image;//种子图
    private EditText et_name;//种子名
    private EditText et_price;//价格
    private EditText et_quantity;//库存
    private EditText et_grow;//生长周期
    private Button sure;
    private getBitmap  getBitmap = new getBitmap();
    private MyRequest myRequest= new MyRequest();
    private Product.ResultBean.HotBean hotBean;
    private TransmitData transmitData = new TransmitData();
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("bundle","aa");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_and_update_seed);
        initView();
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
        et_grow= this.findViewById(R.id. et_grow);
        sure = this.findViewById(R.id.sure);
//        Intent intent = getIntent();
//        type = intent.getIntExtra("type",0);
        if (transmitData.updateSeed != null) {
            hotBean = transmitData.updateSeed;
            transmitData.updateSeed = null;
        }

        if (hotBean != null) {
            iv_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl() + hotBean.getProduct_imgX()));
            et_name.setText(hotBean.getProduct_nameX());
            et_price.setText(String.valueOf(hotBean.getProduct_price()));
            et_quantity.setText(String.valueOf(hotBean.getProduct_numberX()));
            et_grow.setText(hotBean.getProduct_descriptionX());
        }

    }

    public void initEvent() {
        //修改头像
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewAndUpdateSeedActivity.this, PickerActivity.class));
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seedName = et_name.getText().toString();
                String price = et_price.getText().toString();
                String quantity = et_quantity.getText().toString();
                String grow = et_grow.getText().toString();
                sendRequest(seedName,price,quantity,grow);
            }
        });
    }
    public void sendRequest(String seedName, String price,String quantity, String grow) {
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/sj/new/seed")
                .addParams("name", seedName)
                .addParams("price", price)
                .addParams("quantity", quantity)
                .addParams("grow",grow)
                .addParams("storeId", "1")
                .addParams("type",String.valueOf(type))
                .build()
                .execute(new StringCallback()
                {
                    //请求失败
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(ContentValues.TAG,"GoodsList首页请求失败=="+e.getMessage());
                    }
                    //请求成功
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(ContentValues.TAG,"GoodsList首页请求成功=="+response);
                        //解析数据
                        finish();
                    }

                });
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
        startActivity(new Intent(NewAndUpdateSeedActivity.this, PickerActivity.class));
    }

    @Override
    public void onSelectedFinished(List<ImageItem> result) {
        imageItem =result.get(0);
        Log.e(TAG,"item -->"+imageItem);
        setImageView(iv_image,imageItem);

        for(ImageItem imageItem : result) {
            Log.e(ContentValues.TAG,"个人中心的数据被初始化了");
            Log.d(ContentValues.TAG,"item -->"+imageItem);
            imageItem.getPath();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            Bitmap myBitmap = BitmapFactory.decodeFile(imageItem.getPath(), options);
            File file = getFile(myBitmap);
            if (file.exists() && file.isFile()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
                            MediaType FROM_DATA = MediaType.parse("multipart/form-data");
                            MultipartBody body = new MultipartBody.Builder()
                                    .setType(FROM_DATA)
                                    .addFormDataPart("file", imageItem.getTitle(), fileBody).build();
                            Request request = new Request.Builder()
                                    .post(body)
                                    .url(myRequest.getUrl()+"/sj/upload/image")
                                    .build();
                            Call call = myRequest.getOkHttpClient().newCall(request);
//                            Response response = myRequest.getOkHttpClient().newCall(request).execute();
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Log.e("onFailure", "onFailure: "+"fail");
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    try {
                                        if (response.isSuccessful()) {
                                            String string = response.body().string();
                                            JSONObject responseStr = new JSONObject(string);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }

            Log.i("image", myBitmap.toString());
        }
    }

    public File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public void setImageView(ImageView imageView,ImageItem imageItem) {
        iv_image.setBackground(getResources().getDrawable(R.drawable.black));
        Glide.with(imageView.getContext()).load(imageItem.getPath()).into(imageView);
    }
}
