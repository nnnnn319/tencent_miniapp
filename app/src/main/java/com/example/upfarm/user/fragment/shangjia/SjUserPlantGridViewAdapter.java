package com.example.upfarm.user.fragment.shangjia;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.upfarm.R;
import com.example.upfarm.data.Order;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;

import java.util.List;

public class SjUserPlantGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Order.ResultBean.FarmBean> data;
    private com.example.upfarm.sometools.getBitmap getBitmap = new getBitmap();
    private MyRequest myRequest = new MyRequest();

    public  SjUserPlantGridViewAdapter(Context mContext, List<Order.ResultBean.FarmBean> data) {
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
        SjUserPlantGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_sj_user_plant_grid_view, null);
            holder = new  SjUserPlantGridViewAdapter.ViewHolder();
            holder.iv_seed_image = convertView.findViewById(R.id.iv_seed_image);
            holder.tv_plant_time= convertView.findViewById(R.id.tv_plant_time);
            holder.tv_isplanted = convertView.findViewById(R.id.tv_isplanted);
            holder.tv_ispicked= convertView.findViewById(R.id.tv_ispicked);
            holder.tv_send_time = convertView.findViewById(R.id.tv_send_time);
            holder.tv_issended = convertView.findViewById(R.id. tv_issended);
            convertView.setTag(holder);
        } else {
            holder = (SjUserPlantGridViewAdapter.ViewHolder) convertView.getTag();
        }
        holder.iv_seed_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+data.get(position).getFarm_order_image()));
        holder.tv_plant_time.setText(data.get(position).getFarm_order_plant_time());
        if (data.get(position).getFarm_order_plant()==0) {
            holder.tv_isplanted.setText("未种植");
        } else {
            holder.tv_isplanted.setText("已种植");
        }
        if (data.get(position).getFarm_order_results()==0) {
            holder.tv_ispicked.setText("未收获");
        } else {
            holder.tv_ispicked.setText("已收获");
        }
        if (data.get(position).getFarm_order_type() == 0) {
            holder.tv_issended.setText("未配送");
        } else {
            holder.tv_issended.setText("已配送");
        }
        holder.tv_send_time.setText("");
        return convertView;

        //把product_id分成三个
    }

    static class ViewHolder {
        ImageView iv_seed_image;
        TextView tv_plant_time;
        TextView tv_isplanted;
        TextView tv_ispicked;
        TextView tv_send_time;
        TextView tv_issended;

    }
}
