package com.example.upfarm.user.fragment.user;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.upfarm.R;
import com.example.upfarm.data.Order;
import com.example.upfarm.data.User;
import com.example.upfarm.data.UserData;
import com.example.upfarm.domain.ImageItem;
import com.example.upfarm.imagePicket.PickerActivity;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;
import com.example.upfarm.user.fragment.shangjia.NewAndUpdateGoodsActivity;
import com.example.upfarm.utils.PickerConfig;
import com.google.gson.Gson;

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

import static androidx.constraintlayout.widget.Constraints.TAG;

//传图注解
@RequiresApi(api = Build.VERSION_CODES.M)


public class GotoCommentAvtivity extends Activity implements PickerConfig.OnImagesSelectedFinishedListener{
    public static final int MAX_SELECTED_COUNT = 1;
    public static final int PERMISSION_REQUEST_CODE =1;
    private ImageItem imageItem;//更换后的头像
    private ImageView product_image;//保存
    private TextView name;//商品名
    private EditText comment;//评论
    private ImageView show;//买家秀
    private TextView sure;//发布
    private Context mContext;
    private MyRequest myRequest = new MyRequest();
    private Order.ResultBean.ProductBean productBean;
    private com.example.upfarm.sometools.getBitmap getBitmap = new getBitmap();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Gson gson = new Gson();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goto_comment_avtivity);
        mContext = GotoCommentAvtivity.this;
        findViews();
        initListener();
        //传图
        checkPermission();
        initPickerConfig();
        Intent intent = getIntent();
        productBean = gson.fromJson(intent.getStringExtra("product"),Order.ResultBean.ProductBean.class);

    }

    public void findViews() {
        sure = this.findViewById(R.id.sure);
        product_image = this.findViewById(R.id.product_image);
        name = this.findViewById(R.id.name);
        comment = this.findViewById(R.id.comment);
        show = this.findViewById(R.id.show);
        name.setText(productBean.getProduct_username());
        product_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+productBean.getProduct_order_description()));
    }

    public void initListener() {
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布评论
                RequestBody requestBody = new FormBody.Builder().add("productId",productBean.getProduct_id())
                        .add("value",comment.getText().toString()).add("userId", String.valueOf(UserData.user.getUser_id())).build();
                Request request = new Request.Builder()
                        .url(myRequest.getUrl()+"/comment/get")
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
                                //评论成功 要展示
//                                Toast.makeText("以评论成功");
                                Looper.prepare();
                                Toast.makeText(mContext, "您已成功评价", Toast.LENGTH_SHORT).show();
                                finish();
                                Looper.loop();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GotoCommentAvtivity.this, PickerActivity.class));
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
        startActivity(new Intent(GotoCommentAvtivity.this, PickerActivity.class));
    }

    @Override
    public void onSelectedFinished(List<ImageItem> result) {
        imageItem =result.get(0);
        Log.e(TAG,"item -->"+imageItem);
        setImageView(show,imageItem);
    }

    public void setImageView(ImageView imageView,ImageItem imageItem) {
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
                                .url(myRequest.getUrl()+"/comment/upload/image")
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
        show.setBackground(getResources().getDrawable(R.drawable.black));
        Glide.with(imageView.getContext()).load(imageItem.getPath()).into(imageView);
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
}
