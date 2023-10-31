package com.example.upfarm.home.fragment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.upfarm.R;
import com.example.upfarm.data.Product;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.utils.Constants;

import java.util.List;

public class RecommendGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product.ResultBean.TopBean> data;
//    public RecommendGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
//        this.mContext = mContext;
//        this.data = recommend_info;
//    }
public RecommendGridViewAdapter(Context mContext, List<Product.ResultBean.TopBean> recommend_info) {
    this.mContext = mContext;
    this.data = recommend_info;
}

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_recommend_grid_view, null);
            holder = new ViewHolder();
            holder.ivRecommend = convertView.findViewById(R.id.iv_recommend);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvPrice = convertView.findViewById(R.id.tv_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //根据位置得到对应数据
//        ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = data.get(position);

        Product.ResultBean.TopBean recommendInfoBean = data.get(position);
        Glide.with(mContext)
                .load(Constants.BASE_URl_IMAGE +recommendInfoBean.getProduct_imgX())
                .into(holder.ivRecommend);
        holder.tvName.setText(recommendInfoBean.getProduct_nameX());
        holder.tvPrice.setText("￥" + recommendInfoBean.getProduct_price());
        return convertView;
    }

    static class ViewHolder {
        ImageView ivRecommend;
        TextView tvName;
        TextView tvPrice;
    }
}
