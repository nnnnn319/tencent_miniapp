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
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.user.fragment.user.CheckPlantActivity;
import com.example.upfarm.user.fragment.user.GotoCommentAvtivity;

import java.util.List;

public class SjPlantOrderGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Order.ResultBean.FarmBean> data;

    public  SjPlantOrderGridViewAdapter(Context mContext, List<Order.ResultBean.FarmBean> data) {
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
        SjPlantOrderGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_sj_plant_order_grid_view, null);
            holder = new  SjPlantOrderGridViewAdapter.ViewHolder();
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_time= convertView.findViewById(R.id.tv_time);
            holder.tv_phone = convertView.findViewById(R.id.tv_phone);
            holder. tv_address = convertView.findViewById(R.id. tv_address);
            holder.check = convertView.findViewById(R.id.check);
            convertView.setTag(holder);
        } else {
            holder = (SjPlantOrderGridViewAdapter.ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(data.get(position).getFarm_name());
        holder.tv_address.setText(data.get(position).getFarm_order_description());
        holder.tv_phone.setText(data.get(position).getFarm_phone());
//        holder.tv_time.setText(String.valueOf(data.get(position).getFarm_order_date()));
        holder.tv_time.setText("2020-07-15");

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SjUserPlantActivity.class);
                intent.putExtra("farmOrderId", data.get(position).getFarm_order_id());
                TransmitData transmitData = new TransmitData();
//                transmitData.updateGood = data.get(position);
                mContext.startActivity(intent);
            }
        });
        return convertView;

//        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = data.get(position);
//        holder.iv_farm_image.setBackground(mContext.getResources().getDrawable(R.drawable.select_file));
//        holder.tv_farm_name.setText("开心农场");
//        holder.tv_farm_address.setText("￥" + hotInfoBean.getCover_price());
    }

    static class ViewHolder {
        TextView tv_time;
        TextView tv_phone;
        TextView tv_name;
        TextView tv_address;
        Button check;
    }
}
