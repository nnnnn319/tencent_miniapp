package com.example.upfarm.market;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.upfarm.R;

public class ShowOrderActivity extends Activity {
    private TextView close;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        mContext = ShowOrderActivity.this;
        findViews();
        initListener();
    }

    public void findViews() {
        close = this.findViewById(R.id.close);
    }

    public void initListener() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
