package com.example.addsubview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AddSubView extends LinearLayout implements View.OnClickListener {

    private final Context mcontext;
    private ImageView iv_sub;
    private ImageView iv_add;
    private TextView tv_value;


    private int value=1;
    private int minValue =1;
    private int maxValue=5;


    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mcontext=context;
        View.inflate(context,R.layout.add_sub_view,this);

        iv_add = (ImageView) this.findViewById(R.id.iv_add);
        iv_sub = (ImageView) this.findViewById(R.id.iv_sub);
        tv_value = (TextView) this.findViewById(R.id.tv_value);

        iv_add.setOnClickListener(this);
        iv_sub.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_sub:
                subNumber();
                break;
            case R.id.iv_add:
                addNumber();
                break;
        }


//        Toast.makeText(mcontext, "当前商品数=" + value, Toast.LENGTH_SHORT).show();
    }

    private void addNumber() {
        if(value<maxValue){
            value++;
        }
        tv_value.setText(value+"");

        if (onNumberChangeListener!=null){
            onNumberChangeListener.onNumberChange(value);
        }
    }

    private void subNumber() {
        if (value>minValue){
            value--;
        }
        tv_value.setText(value+"");

        if (onNumberChangeListener!=null){
            onNumberChangeListener.onNumberChange(value);
        }

    }


        public interface OnNumberChangeListener{
        public void onNumberChange(int value);

    }

    private OnNumberChangeListener onNumberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

    public ImageView getIv_sub() {
        return iv_sub;
    }

    public void setIv_sub(ImageView iv_sub) {
        this.iv_sub = iv_sub;
    }

    public int getValue() {
        String v = tv_value.getText().toString().trim();
        if (TextUtils.isEmpty(v)){
            value=Integer.parseInt(v);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value+"");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;

    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
