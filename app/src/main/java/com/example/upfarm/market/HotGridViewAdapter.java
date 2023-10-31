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

import java.util.List;

public class HotGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product.ResultBean.HotBean> data;

    public HotGridViewAdapter(Context mContext, List<Product.ResultBean.HotBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_hot_grid_view, null);
            holder = new ViewHolder();
            holder.ivHot = convertView.findViewById(R.id.iv_hot);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvPrice = convertView.findViewById(R.id.tv_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product.ResultBean.HotBean hotInfoBean = data.get(position);
        Glide.with(mContext)
                .load(Constants.BASE_URl_IMAGE +hotInfoBean.getProduct_imgX())
                .into(holder.ivHot);
        holder.tvName.setText(hotInfoBean.getProduct_nameX());
        holder.tvPrice.setText("￥" + hotInfoBean.getProduct_numberX());
        return convertView;
    }

    static class ViewHolder {
        ImageView ivHot;
        TextView tvName;
        TextView tvPrice;
    }
}
