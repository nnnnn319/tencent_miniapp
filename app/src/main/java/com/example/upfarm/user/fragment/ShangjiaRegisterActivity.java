package com.example.upfarm.user.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
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
import android.widget.TextView;

import com.example.upfarm.LoginNewActivity;
import com.example.upfarm.R;
import com.example.upfarm.data.User;
import com.example.upfarm.data.UserData;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.domain.ImageItem;
import com.example.upfarm.imagePicket.PickerActivity;
import com.example.upfarm.map.MapActivity;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.utils.PickerConfig;
import com.example.upfarm.utils.ResultImageAdapter;

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
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

//传图注解
@RequiresApi(api = Build.VERSION_CODES.M)

public class ShangjiaRegisterActivity extends Activity  implements PickerConfig.OnImagesSelectedFinishedListener {
    //传图
    public static final int PERMISSION_REQUEST_CODE =1;
    public static final int MAX_SELECTED_COUNT = 9;
    private RecyclerView mResultListView;
    private ResultImageAdapter mResultImageAdapter;
    private MyRequest myRequest = new MyRequest();
    private Button submit_sj_re;
    private EditText et_farm_name;
    private EditText et_farm_address;
    private EditText et_real_name;
    private EditText et_identify_id;
    private TextView tv_all_order;

    private String farm_name;
    private String fram_address;
    private String farmer_real_name;
    private String farmer_identify_id;

    private boolean isStoreRegister = false;
    int count=0;

    private TransmitData transmitData = new TransmitData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangjia_register);
        initView();
        //传图
        checkPermission();
        initPickerConfig();
        initListener();
    }

    private void initView() {
        mResultListView = this.findViewById(R.id.result_list);
        mResultImageAdapter = new ResultImageAdapter();
        mResultListView.setAdapter(mResultImageAdapter);
        submit_sj_re = findViewById(R.id.submit_sj_re);
        et_farm_name = findViewById(R.id.et_farm_name);
        et_farm_address = findViewById(R.id.et_farm_address);
        et_real_name = findViewById(R.id.et_real_name);
        et_identify_id = findViewById(R.id.et_identify_id);
        farm_name = et_farm_name.getText().toString();
        fram_address = et_farm_address.getText().toString();
        farmer_identify_id = et_identify_id.getText().toString();
        farmer_real_name = et_real_name.getText().toString();
        tv_all_order = findViewById(R.id.tv_all_order);
        if (transmitData.map_address=="") {

        } else {
            et_farm_address.setText(transmitData.map_address);
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
        int readExStoragePermissionRest = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
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
        startActivity(new Intent(ShangjiaRegisterActivity.this, PickerActivity.class));
    }

    @Override
    public void onSelectedFinished(List<ImageItem> result) {
        //所选择的图片列表回来了
        for(ImageItem imageItem : result) {
            Log.e(TAG,"个人中心的数据被初始化了");
            Log.d(TAG,"item -->"+imageItem);
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
                                    .url(myRequest.getUrl()+"/upload/image")
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
        int horizontalCount;
        if(result.size()<3) {
            horizontalCount=result.size();
        }else {
            horizontalCount=3;
        }
        mResultListView.setLayoutManager(new GridLayoutManager(this,horizontalCount));
        mResultImageAdapter.setData(result,horizontalCount);
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
    public void initListener() {
        submit_sj_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //发送商家入驻请求信息
                        RequestBody requestBody = new FormBody.Builder().add("farm_name",farm_name)
                                .add("farm_address", "湖南").add("i_code","ss")
                                .add("farmer_real_name",farmer_real_name).add("userId",String.valueOf(UserData.user.getUser_id())).build();
                        Request request = new Request.Builder().url(myRequest.getUrl()+"/store/register").post(requestBody).build();
                        Call call = myRequest.getOkHttpClient().newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    if (response.isSuccessful()) {
                                        String string = response.body().string();
                                        JSONObject responseStr = new JSONObject(string);
                                        isStoreRegister = true;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                finish();
                            }
                        });
                    }
                }).start();
                if (isStoreRegister) {
                    Log.e("Store", "注册成功");
                } else {
                    Log.e("Store", "注册失败");
                }
            }
        });

        tv_all_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String address = et_farm_address.getText().toString();
                Intent intent = new Intent(ShangjiaRegisterActivity.this, MapActivity.class);
                intent.putExtra("city","晋城");
                intent.putExtra("address","城区畅东小区");
                startActivity(intent);

            }
        });
    }

    @Override

    protected void onResume() {
        if (count ==0 || count==1) {
            super.onResume();
            onCreate(null);
            count++;
        } else {
            super.onResume();
        }

    }
}
