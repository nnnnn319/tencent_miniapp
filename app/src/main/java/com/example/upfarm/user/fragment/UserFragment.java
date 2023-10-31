package com.example.upfarm.user.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upfarm.FirstActivity;
import com.example.upfarm.LoginNewActivity;
import com.example.upfarm.MainActivity;
import com.example.upfarm.R;
import com.example.upfarm.base.BaseFragment;
import com.example.upfarm.data.UserData;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.lwhChat.LwhChatActivity;
import com.example.upfarm.market.GoodsCommentActivity;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;
import com.example.upfarm.user.fragment.shangjia.MessageActivity;
import com.example.upfarm.user.fragment.user.AddressActivity;
import com.example.upfarm.user.fragment.user.MyCommentActivity;
import com.example.upfarm.user.fragment.user.PlantOrderActivity;
import com.example.upfarm.user.fragment.user.UserOrderActivity;
import com.hengyi.wheelpicker.listener.OnCityWheelComfirmListener;
import com.hengyi.wheelpicker.ppw.CityWheelPickerPopupWindow;

import static android.content.ContentValues.TAG;

public class UserFragment extends BaseFragment {
    /*
      李梦瑶
      username 用户名
     */
    private TextView username;//用户名

    private  RelativeLayout shangjia_register;
    private  RelativeLayout tv_information;
    private  RelativeLayout order;
    private  RelativeLayout plant;
    private  RelativeLayout address;
    private  RelativeLayout tv_comment;
    private  RelativeLayout tv_message;
    private  RelativeLayout exit;
    private TransmitData transmitData = new TransmitData();
    private UserData userData = new UserData();
    private ImageView main_user;
    private getBitmap getBitmap = new getBitmap();
    private MyRequest myRequest = new MyRequest();

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        findViews(view);
        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"个人中心的数据被初始化了");
        if (userData.user==null) {

        } else {
            username.setText(userData.user.getUser_name());
        }

    }

    private void findViews(View view) {
      username = view.findViewById(R.id.username);
      username.setText("Heather");
      shangjia_register = view.findViewById(R.id.shangjia_register);
      tv_information = view.findViewById(R.id.tv_information);
      order = view.findViewById(R.id.order);
      plant = view.findViewById(R.id.plant);
      address = view.findViewById(R.id.address);
      tv_comment = view.findViewById(R.id.tv_comment);
      tv_message = view.findViewById(R.id.tv_message);
      exit = view.findViewById(R.id.exit);
      main_user = view.findViewById(R.id.main_user_name);
        if (userData.user.getUser_register_time().startsWith("/")) {
            main_user.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+userData.user.getUser_register_time()));
        }
        //显示用户头像
        else {
            main_user.setBackground(getResources().getDrawable(R.drawable.select));
        }
    }
    private void  initListener(){
        shangjia_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ShangjiaRegisterActivity.class);
                startActivity(intent);
            }
        });
        //个人信息
        tv_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,InformationActivity.class);
                startActivity(intent);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserOrderActivity.class);
                startActivity(intent);
            }
        });
        plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlantOrderActivity.class);
                startActivity(intent);
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddressActivity.class);
                intent.putExtra("type",0);
                TransmitData transmitData = new TransmitData();
                transmitData.type_address = 0;
                startActivity(intent);
            }
        });
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyCommentActivity.class);
                intent.putExtra("type",2);
                TransmitData transmitData = new TransmitData();
                transmitData.commentType = 2;
                startActivity(intent);
            }
        });
        tv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LoginNewActivity.class);
                LwhChatActivity lwhChatActivity = new LwhChatActivity();
                lwhChatActivity.logout();
                startActivity(intent);
                getActivity().onBackPressed();
            }
        });

    }
}
