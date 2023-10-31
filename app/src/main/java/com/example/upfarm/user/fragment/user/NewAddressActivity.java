package com.example.upfarm.user.fragment.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upfarm.R;
import com.example.upfarm.data.UserData;
import com.example.upfarm.market.GoodsInfoActivity;
import com.example.upfarm.market.SelectGoodsNumActivity;
import com.example.upfarm.network.request.MyRequest;
import com.hxb.hxbaddressselectionviewlibrary.view.AddressPickerPopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class NewAddressActivity extends Activity {
    private TextView sure;//保存
    private TextView tv_address;//选择地区
    private EditText et_name;//收件人
    private EditText et_phone;//手机号
    private EditText et_detail;//详细地址
    private TextView tv_address_content;//点击所选地区后出来的内容
    private Context mContext;
    private AddressPickerPopupWindow popupWindow;
    String chooseAddress;
    private MyRequest myRequest = new MyRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        mContext = NewAddressActivity.this;
        findViews();
        initListener();
        popupWindow = new AddressPickerPopupWindow(NewAddressActivity.this);
        popupWindow.setOnAddressPickerSelectListener(new AddressPickerPopupWindow.OnAddressPickerSelectListener() {
            @Override
            public void onSelected(String Province, String City, String District, String PostCode) {
                Toast.makeText(NewAddressActivity.this, Province + City + District, Toast.LENGTH_LONG).show();
                tv_address_content.setText(Province + City + District);
                chooseAddress = Province + City + District;
            }
        });
    }

    public void findViews() {
        sure = this.findViewById(R.id.sure);
        tv_address = this.findViewById(R.id.tv_address);
        et_name = this.findViewById(R.id.et_name);
        et_phone = this.findViewById(R.id.et_phone);
        et_detail = this.findViewById(R.id.et_detail);
        tv_address_content = this.findViewById(R.id.tv_address_content);
    }

    public void initListener() {
        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.show();
            }
        });
        //保存
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String phone = et_phone.getText().toString();
                String detail = et_detail.getText().toString();
                OkHttpUtils
                        .get()
                        .url(myRequest.getUrl()+"/user/address/add")
                        .addParams("userId", String.valueOf(UserData.user.getUser_id()))
                        .addParams("specificAddress", detail)
                        .addParams("chooseAddress", chooseAddress)
                        .addParams("phone", phone)
                        .addParams("name", name)
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
                                Log.e(TAG,"NewAddress请求成功=="+response);
                                //加入原来的列表
                            }

                        });
                //它新增的地址变为
               finish();
            }
        });
    }
    @Override

    protected void onResume() {

        super.onResume();

        onCreate(null);

    }
}
