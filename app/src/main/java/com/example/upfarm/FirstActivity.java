package com.example.upfarm;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.annotation.BinderThread;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.upfarm.base.BaseFragment;
import com.example.upfarm.cart.fragment.CartFragment;
import com.example.upfarm.data.UserData;
import com.example.upfarm.home.fragment.HomeFragment;
import com.example.upfarm.market.MarketFragment;
import com.example.upfarm.rent.RentFragment;
import com.example.upfarm.user.fragment.ManagerFragment;
import com.example.upfarm.user.fragment.ShangjiaFragment;
import com.example.upfarm.user.fragment.UserFragment;

import java.util.ArrayList;

/*
  李梦瑶
  type 用户类型 0-普通用户 1-商家 2-管理员
* */

//登录后的第一个页面
public class FirstActivity extends FragmentActivity {
    private int type=2;//用户类型
    private UserData userData;

    private ArrayList<BaseFragment> fragments;
    private RadioGroup rg_main;
    //上次的Fragment
    private Fragment tempFragment;
    private int position =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        rg_main = findViewById(R.id.rg_main);
//        rg_main.check(R.id.rb_home);
        //初始化fragment
        initFragment();
        //设置底部导航栏的监听
        initListener();
    }

    private void initFragment() {

        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new RentFragment());
        fragments.add(new MarketFragment());
        fragments.add(new CartFragment());
        type = userData.user.getUser_type();
//        type=3;
//        fragments.add(new UserFragment(type));
        switch (type) {
            case 0:
                fragments.add(new UserFragment());
                break;
            case 1:
                fragments.add(new ShangjiaFragment());
                break;
            case 2:
                fragments.add(new ManagerFragment());
        }
    }

    private void  initListener(){
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home://主页
                        position=0;
                        break;
                    case R.id.rb_rent://租地
                        position=1;
                        break;
                    case R.id.rb_market://集市
                        position=2;
                        break;
                    case R.id.rb_cart://购物车
                        position=3;
                        break;
                    case R.id.rb_user://用户
                        position=4;
                        break;
                        default:
                            position=0;
                            break;

                }
                //根据位置取不同Fragment
                BaseFragment baseFragment = getFragment(position);
                //tempFragment上次显示的，baseFragment当前正要显示的
                switchFragment(tempFragment,baseFragment);
            }
        });
        rg_main.check(R.id.rb_home);
    }

    private BaseFragment getFragment(int position) {
        if(fragments != null && fragments.size()>0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if(tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if(nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if(!nextFragment.isAdded())  {
                    if(fromFragment !=null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout,nextFragment).commit();
                }
                else {
                    if(fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }
}
