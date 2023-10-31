package com.example.upfarm.user.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.upfarm.LoginNewActivity;
import com.example.upfarm.R;
import com.example.upfarm.base.BaseFragment;

import com.example.upfarm.user.fragment.manager.MFarmActivity;
import com.example.upfarm.user.fragment.manager.MUserActivity;

public class ManagerFragment extends BaseFragment {
    private RelativeLayout register_list;
    private RelativeLayout user_list;
    private RelativeLayout farm_list;
    private TextView exit;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_manager, null);
        findViews(view);
        initListener();
        return view;
    }

    private void findViews(View view) {
        register_list = view.findViewById(R.id.register_list);
        user_list = view.findViewById(R.id.user_list);
        farm_list = view.findViewById(R.id.farm_list);
//        exit = view.findViewById(R.id.exit);

    }

    private void  initListener() {
        register_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegisterListActivity.class);
                startActivity(intent);
            }
        });
        user_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MUserActivity.class);
                startActivity(intent);
            }
        });
        farm_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MFarmActivity.class);
                startActivity(intent);
            }
        });
//        exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, LoginNewActivity.class);
//                startActivity(intent);
//                getActivity().onBackPressed();
//            }
//        });
    }
}
