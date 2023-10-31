package com.example.upfarm.user.fragment.shangjia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.upfarm.R;
import com.example.upfarm.user.fragment.InformationActivity;

public class OrderActivity extends Activity {
    private Context mContext;
    private RelativeLayout goods;
    private  RelativeLayout plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mContext = OrderActivity.this;
        findViews();
        initListener();
    }

    public void findViews() {
        goods = this.findViewById(R.id.goods);
        plant = this.findViewById(R.id.plant);
    }


    public void initListener() {
        //类型1和2
        goods.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, SjGoodsOrderActivity.class);
            startActivity(intent);
        }
    });
        //类型3
        plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SjPlantOrderActivity.class);
                startActivity(intent);
            }
        });

    }
}
