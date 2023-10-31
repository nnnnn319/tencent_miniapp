package com.example.upfarm.rent;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.upfarm.R;
import com.example.upfarm.data.Product;
import com.example.upfarm.map.MapActivity;
import com.example.upfarm.map.MapRoutingActivity;
import com.example.upfarm.utils.Constants;

import java.util.List;

public class RentGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product.ResultBean.RentBean> data;

    public RentGridViewAdapter(Context mContext, List<Product.ResultBean.RentBean> data) {
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
        RentGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_farm_grid_view, null);
            holder = new RentGridViewAdapter.ViewHolder();
            holder.iv_farm_image = convertView.findViewById(R.id.iv_farm_image);
            holder.tv_farm_name = convertView.findViewById(R.id.tv_farm_name);
            holder.tv_farm_address = convertView.findViewById(R.id.tv_farm_address);
            holder.map = convertView.findViewById(R.id.map);
            convertView.setTag(holder);
        } else {
            holder = (RentGridViewAdapter.ViewHolder) convertView.getTag();
        }

        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city="晋城市";
                String address="城区畅东小区";
                Intent intent = new Intent(mContext, MapRoutingActivity.class);
//                intent.putExtra("city",city);
//                intent.putExtra("address",address);
                mContext.startActivity(intent);
            }
        });
        Product.ResultBean.RentBean rentBean = data.get(position);
        Glide.with(mContext)
                .load(Constants.BASE_URl_IMAGE + rentBean.getFarm_img())
                .into(holder.iv_farm_image);

        holder.iv_farm_image.setBackground(mContext.getResources().getDrawable(R.drawable.select_file));
        holder.tv_farm_name.setText("农场名："+rentBean.getFarm_name());
        holder.tv_farm_address.setText("农场位置："+rentBean.getFarm_x());

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_farm_image;
        TextView tv_farm_name;
        TextView tv_farm_address;
        TextView map;
    }
}
