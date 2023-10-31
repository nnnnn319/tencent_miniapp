package com.example.upfarm.rent;

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
import com.example.upfarm.market.HotGridViewAdapter;
import com.example.upfarm.utils.Constants;

import java.util.List;

public class SeedGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product.ResultBean.HotBean> data;

    public SeedGridViewAdapter(Context mContext, List<Product.ResultBean.HotBean> data) {
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
        SeedGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_seed_grid_view, null);
            holder = new SeedGridViewAdapter.ViewHolder();
            holder.ivSeed = convertView.findViewById(R.id.iv_seed_image);
            holder.tvName = convertView.findViewById(R.id.tv_seed_name);
            holder.tvQuantity = convertView.findViewById(R.id.tv_quantity);
            convertView.setTag(holder);
        } else {
            holder = (SeedGridViewAdapter.ViewHolder) convertView.getTag();
        }

        Product.ResultBean.HotBean hotInfoBean = data.get(position);
        Glide.with(mContext)
                .load(Constants.BASE_URl_IMAGE +hotInfoBean.getProduct_imgX())
                .into(holder.ivSeed);
        holder.ivSeed.setBackground(mContext.getResources().getDrawable(R.drawable.main_cart));
        holder.tvName.setText(hotInfoBean.getProduct_nameX());
        holder.tvQuantity.setText(String.valueOf(hotInfoBean.getProduct_numberX()));
        return convertView;
    }
    static class ViewHolder {
        ImageView ivSeed;
        TextView tvName;
        TextView tvQuantity;
    }
}
