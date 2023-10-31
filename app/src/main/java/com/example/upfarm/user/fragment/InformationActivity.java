package com.example.upfarm.user.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.text.IDNA;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.upfarm.R;
import com.example.upfarm.data.User;
import com.example.upfarm.data.UserData;
import com.example.upfarm.domain.ImageItem;
import com.example.upfarm.imagePicket.PickerActivity;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;
import com.example.upfarm.utils.PickerConfig;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import static com.example.upfarm.user.fragment.ShangjiaRegisterActivity.PERMISSION_REQUEST_CODE;

/*
   李梦瑶
   tv_user_image//头像
   tv_user_password//密码
   tv_user_phone//手机号
 */
//传图注解
@RequiresApi(api = Build.VERSION_CODES.M)

public class InformationActivity extends Activity implements PickerConfig.OnImagesSelectedFinishedListener{
    //传图
    public static final int MAX_SELECTED_COUNT = 1;
    public static final int PERMISSION_REQUEST_CODE =1;

    private ImageView tv_user_image;//头像
    private TextView tv_user_password;//密码
    private TextView tv_user_phone;//手机号
    private ImageItem imageItem;//更换后的头像

    private CreateUserDialog createUserDialog;
    private MyRequest myRequest = new MyRequest();
    private TextView back;
    private com.example.upfarm.sometools.getBitmap getBitmap = new getBitmap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initView();
        initEvent();
        //传图
        checkPermission();
        initPickerConfig();
        back = this.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    private void checkPermission(){
        int readExStoragePermissionRest = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(readExStoragePermissionRest != PackageManager.PERMISSION_GRANTED) {
            //没有权限
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }

    //传图
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_REQUEST_CODE) {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                //有权限
            }else {
                //无权限
                //根据交互去处理
            }
        }
    }


    public void initView() {
        tv_user_image = this.findViewById(R.id.tv_user_image);
        tv_user_password = this.findViewById(R.id.tv_user_password);
        tv_user_phone = this.findViewById(R.id.tv_user_phone);
        //显示用户手机号

        UserData userData = new UserData();
        tv_user_phone.setText(userData.user.getUser_phone());
        if (userData.user.getUser_register_time().startsWith("/")) {
            tv_user_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+userData.user.getUser_register_time()));
        }
        //显示用户头像
        else {
            tv_user_image.setBackground(getResources().getDrawable(R.drawable.select));
        }
    }

    public void initEvent() {
        //修改头像
        tv_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InformationActivity.this, PickerActivity.class));
            }
        });
        //修改密码
        tv_user_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"修改密码");
                showDialog();
            }
        });
        //修改手机号
        tv_user_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"修改手机号");
            }
        });
    }

    @Override
    public void onSelectedFinished(List<ImageItem> result) {
        if(result.size()==0) {
            return;
        }
        for(ImageItem imageItem : result) {
            Log.e(TAG,"个人中心的数据被初始化了");
            Log.d(TAG,"item -->"+imageItem);
            imageItem.getPath();

//            tv_user_image.setImageBitmap();
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
                                    .url(myRequest.getUrl() + "/user/upload/image")
                                    .build();
                            Call call = myRequest.getOkHttpClient().newCall(request);
//                            Response response = myRequest.getOkHttpClient().newCall(request).execute();
                            call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Log.e("onFailure", "onFailure: " + "fail");
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    try {
                                        if (response.isSuccessful()) {
                                            Log.e("image","successful");
                                            fixUserImage();
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
            Bitmap bitmap = getLoacalBitmap(imageItem.getPath()); //从本地取图片(在cdcard中获取)  //
            tv_user_image.setImageBitmap(bitmap); //设置Bitmap
        }
        imageItem =result.get(0);
        Log.e(TAG,"item -->"+imageItem);
        setImageView(tv_user_image,imageItem);
    }

    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
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
        tv_user_image.setBackground(getResources().getDrawable(R.drawable.black));
        Glide.with(imageView.getContext()).load(imageItem.getPath()).into(imageView);
    }

    public void showDialog() {

//        AlertDialog alertDialog2 = new AlertDialog.Builder(this)
//                .setTitle("修改密码")
//                .setMessage("your password")
//                .setIcon(R.mipmap.ic_launcher)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        Toast.makeText(InformationActivity.this, "这是确定按钮", Toast.LENGTH_SHORT).show();
//                    }
//                })
//
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(InformationActivity.this, "已经取消", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .create();
//        alertDialog2.show();
        Log.e("a","show");
        this.showEditDialog();

    }

    public void showEditDialog() {
        createUserDialog = new CreateUserDialog(this,R.style.WinDialog,onClickListener);
        createUserDialog.show();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_submit_btn:
                    String phoneNum = createUserDialog.text_name.getText().toString().trim();
                    Log.e("information","修改手机号请求");
                    break;
                case R.id.dialog_code_btn_1:
                    String iCode = createUserDialog.text_mobile.getText().toString().trim();
                    Log.e("information","获取验证码");
                    break;
            }
        }
    };

    public void fixUserImage() {
        Log.e("fix","image");
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/user/fix/image")
                .addParams("userId",String.valueOf(UserData.user.getUser_id()))
                .build()
                .execute(new StringCallback()
                {
                    //请求失败
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        Log.e(TAG,"首页请求失败=="+e.getMessage());
                    }
                    //请求成功
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG,"首页请求成功=="+response);
                        //解析数据
                    }

                });
    }
    @Override

    protected void onResume() {

        super.onResume();

        onCreate(null);

    }

}
