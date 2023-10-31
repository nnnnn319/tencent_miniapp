package com.example.upfarm.user.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.upfarm.R;
import com.example.upfarm.data.Manager;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.utils.Constants;

import java.util.List;

public class ListGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Manager.ResultBean.RequestBean> data;

    public ListGridViewAdapter(Context mContext, List<Manager.ResultBean.RequestBean> data) {
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
            convertView = View.inflate(mContext, R.layout.item_list_grid_view, null);
            holder = new ViewHolder();
            holder.ivUser = convertView.findViewById(R.id.iv_user);
            holder.tvTime= convertView.findViewById(R.id.tv_time);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvFarmName = convertView.findViewById(R.id.tv_farm_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Manager.ResultBean.RequestBean hotInfoBean = data.get(position);
//        Glide.with(mContext)
//                .load(Constants.BASE_URl_IMAGE +hotInfoBean.get())
//                .into(holder.ivHot);
        holder.tvName.setText(hotInfoBean.getFarm_name());
//        holder.tvPrice.setText("￥" + hotInfoBean.getCover_price());
        holder.ivUser.setBackground(mContext.getResources().getDrawable(R.drawable.main_cart));
        holder.tvTime.setText(hotInfoBean.getRequest_time());
        holder.tvName.setText(hotInfoBean.getUser_name());
        holder.tvFarmName.setText(hotInfoBean.getFarm_name());
        return convertView;
    }
    static class ViewHolder {
        ImageView ivUser;
        TextView tvTime;
        TextView tvName;
        TextView tvFarmName;
    }
}
