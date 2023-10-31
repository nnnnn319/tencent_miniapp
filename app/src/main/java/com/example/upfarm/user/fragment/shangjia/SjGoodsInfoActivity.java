package com.example.upfarm.user.fragment.shangjia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.upfarm.R;

public class SjGoodsInfoActivity extends Activity {
    private Button update;
    private Button delete;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sj_goods_info);
        mContext = SjGoodsInfoActivity.this;
        findViews();
        initListener();
    }

    public void findViews() {
        update = this.findViewById(R.id.update);
        delete = this.findViewById(R.id.delete);

    }

    public void initListener() {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 要传参显示原来的在输入框中
                 */
                Intent intent = new Intent(mContext, NewAndUpdateGoodsActivity.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
