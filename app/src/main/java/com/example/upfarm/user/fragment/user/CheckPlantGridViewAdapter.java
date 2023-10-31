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
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;

import java.util.List;

public class CheckPlantGridViewAdapter  extends BaseAdapter {
        private Context mContext;
        private List<Order.ResultBean.FarmBean> data;
        private getBitmap getBitmap = new getBitmap();
        private MyRequest myRequest = new MyRequest();

    public  CheckPlantGridViewAdapter(Context mContext, List<Order.ResultBean.FarmBean> data) {
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
            CheckPlantGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = android.view.View.inflate(mContext, R.layout.item_check_plant_grid_view, null);
            holder = new CheckPlantGridViewAdapter.ViewHolder();
            holder.tv_plant_time= convertView.findViewById(R.id.tv_plant_time);
            holder.iv_seed_image = convertView.findViewById(R.id.iv_seed_image);
            holder.tv_isplanted= convertView.findViewById(R.id.tv_isplanted);
            holder.tv_ispicked= convertView.findViewById(R.id.tv_ispicked);
            holder.tv_isplanted= convertView.findViewById(R.id.tv_isplanted);
            holder.tv_send_time= convertView.findViewById(R.id.tv_send_time);
            holder.tv_issended= convertView.findViewById(R.id.tv_issended);
            holder.tv_quantity = convertView.findViewById(R.id.tv_quantity);

            convertView.setTag(holder);
        } else {
            holder = (CheckPlantGridViewAdapter.ViewHolder) convertView.getTag();
        }
            /**
             * 是否种植，是否收获都用图片
             */

        Order.ResultBean.FarmBean hotInfoBean = data.get(position);
//        holder.iv_farm_image.setBackground(mContext.getResources().getDrawable(R.drawable.select_file));
//        holder.tv_farm_name.setText("开心农场");
//        holder.tv_farm_address.setText("￥" + hotInfoBean.getCover_price());
            holder.tv_plant_time.setText(data.get(position).getFarm_order_plant_time());
            holder.tv_quantity.setText(data.get(position).getFarm_order_number()+"㎡");
            if (data.get(position).getFarm_order_plant()==0) {
                holder.tv_isplanted.setText("是否种植  未种植");
            } else {
                holder.tv_isplanted.setText("是否种植  已种植");
            }
            if (data.get(position).getFarm_order_results()==0) {
                holder.tv_ispicked.setText("是否收获  未收获");
            } else {
                holder.tv_ispicked.setText("是否收获  已收获");
            }
            holder.tv_send_time.setText(String.valueOf(data.get(position).getFarm_order_plant_time()));
            holder.iv_seed_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+data.get(position).getFarm_order_image()));
        return convertView;
    }

        static class ViewHolder {
            ImageView iv_seed_image;
            TextView tv_plant_time;
            TextView tv_isplanted;
            TextView tv_ispicked;
            TextView tv_send_time;
            TextView tv_issended;
            TextView tv_quantity;


        }
}
