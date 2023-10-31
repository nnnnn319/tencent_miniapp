package com.example.upfarm.user.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.upfarm.LoginNewActivity;
import com.example.upfarm.R;
import com.example.upfarm.base.BaseFragment;
import com.example.upfarm.data.UserData;
import com.example.upfarm.user.fragment.shangjia.FarmActivity;
import com.example.upfarm.user.fragment.shangjia.FarmInfomationActivity;
import com.example.upfarm.user.fragment.shangjia.MessageActivity;
import com.example.upfarm.user.fragment.shangjia.OrderActivity;
import com.example.upfarm.user.fragment.shangjia.ShopActivity;

import org.w3c.dom.Text;

public class ShangjiaFragment extends BaseFragment {

    private RelativeLayout shop;
    private RelativeLayout farm;
    private RelativeLayout order;
    private RelativeLayout message;
    private TextView username;
    private RelativeLayout information;
    private  RelativeLayout exit;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shangjia, null);
        findViews(view);
        initListener();
        return view;
    }

    private void findViews(View view) {
        shop = view.findViewById(R.id.shop);
        farm = view.findViewById(R.id.farm);
        order = view.findViewById(R.id.order);
        message = view.findViewById(R.id.message);
        information = view.findViewById(R.id.information);
        username = view.findViewById(R.id.username);
        UserData userData = new UserData();
        username.setText(userData.user.getUser_name());
        exit = view.findViewById(R.id.exit);
    }

    public void initListener() {
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShopActivity.class);
                startActivity(intent);
            }
        });
        farm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FarmActivity.class);
                startActivity(intent);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderActivity.class);
                startActivity(intent);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                startActivity(intent);
            }
        });
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FarmInfomationActivity.class);
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LoginNewActivity.class);
                startActivity(intent);
                getActivity().onBackPressed();
            }
        });
    }
}
