package com.example.upfarm.market;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.upfarm.R;
import com.example.upfarm.data.Product;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ChannelAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product.ResultBean.ChannelBean> datas = new ArrayList<>();

    public ChannelAdapter(Context mContext, List<Product.ResultBean.ChannelBean> channel_info) {
        this.mContext = mContext;
        this.datas =channel_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholer;
        if(convertView == null) {
            convertView = View.inflate(mContext,R.layout.item_channel,null);
            viewholer = new ViewHolder();
           viewholer.iv_icon = convertView.findViewById(R.id.iv_channel);
           viewholer.tv_title = convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewholer);
        } else {
            viewholer = (ViewHolder) convertView.getTag();
        }
        //根据位置得到对应的数据
        Product.ResultBean.ChannelBean channelInfoBean = datas.get(position);
        Glide.with(mContext)
                .load(Constants.BASE_URl_IMAGE +channelInfoBean.getImage())
                .into(viewholer.iv_icon);
        viewholer.tv_title.setText(channelInfoBean.getName());
        return convertView;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_title;
    }
}
