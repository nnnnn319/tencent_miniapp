package com.example.upfarm.base.pay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.upfarm.R;
import com.example.upfarm.alipay.PayDemoActivity;
import com.example.upfarm.market.SelectGoodsNumActivity;
import com.example.upfarm.market.ShowOrderActivity;
import com.example.upfarm.market.ShowPlantAvtivity;
import com.example.upfarm.rent.SurePlantActivity;

public class PayActivity extends Activity {
    private TextView sure;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        mContext = PayActivity.this;
        findViews();
        initListener();
    }

    public void findViews() {
        sure = this.findViewById(R.id.sure);
    }

    public void initListener() {
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 得判断是商品还是种植，并且跳转不是这个是支付
                 */
                //商品
//                Intent intent = new Intent(mContext, ShowOrderActivity.class);
//                startActivity(intent);
                /**
                 * 记得finishv
                 */
                //种植
               Intent intent = new Intent(mContext, ShowOrderActivity.class);
                startActivity(intent);
            }
        });
    }
}
