package com.example.upfarm.market;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.upfarm.R;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.utils.Constants;

import java.util.List;

public class TopAdapter extends BaseAdapter {
    private Context mContext;

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TopAdapter.ViewHolder viewholer;
        if(convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_top,null);
            viewholer = new TopAdapter.ViewHolder();
            viewholer.iv_image = convertView.findViewById(R.id.iv_top);
            convertView.setTag(viewholer);
        } else {
            viewholer = (TopAdapter.ViewHolder) convertView.getTag();
        }
        //根据位置得到对应的数据
//        Glide.with(mContext)
//                .load(Constants.BASE_URl_IMAGE +channelInfoBean.getImage())
//                .into(viewholer.iv_icon);

        viewholer.iv_image.setBackground(mContext.getResources().getDrawable(R.drawable.land3));
        return convertView;
    }

    public TopAdapter(Context mContext) {
        this.mContext = mContext;
    }

    class ViewHolder {
        ImageView iv_image;
    }
}
