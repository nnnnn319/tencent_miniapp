package com.example.upfarm.user.fragment.user;

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
import com.example.upfarm.user.fragment.shangjia.NewAndUpdateGoodsActivity;

import java.util.List;

public class PlantOrderGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Order.ResultBean.FarmBean> data;
    private TransmitData transmitData = new TransmitData();

    public  PlantOrderGridViewAdapter(Context mContext, List<Order.ResultBean.FarmBean> data) {
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
        PlantOrderGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_plant_order_grid_view, null);
            holder = new PlantOrderGridViewAdapter.ViewHolder();
            holder.tv_time= convertView.findViewById(R.id.tv_time);
            holder.tv_farm = convertView.findViewById(R.id.tv_farm);
            holder.check = convertView.findViewById(R.id.check);
            convertView.setTag(holder);
        } else {
            holder = (PlantOrderGridViewAdapter.ViewHolder) convertView.getTag();
        }

        Order.ResultBean.FarmBean hotInfoBean = data.get(position);
//        holder.tv_farm.setBackground(mContext.getResources().getDrawable(R.drawable.select_file));
        holder.tv_farm.setText(String.valueOf(hotInfoBean.getFarm_order_description()));
        holder.tv_time.setText(hotInfoBean.getFarm_order_date());
//        holder.
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 要传参显示原来的在输入框中
                 */
                Intent intent = new Intent(mContext, CheckPlantActivity.class);
                transmitData.farmOrderId = data.get(position).getFarm_order_id();
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView tv_time;
        TextView tv_farm;
        Button check;
    }

}
