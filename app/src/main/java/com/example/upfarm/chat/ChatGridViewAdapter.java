package com.example.upfarm.chat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.upfarm.R;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.rent.SeedGridViewAdapter;

import java.util.List;

public class ChatGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ResultBeanData.ResultBean.HotInfoBean> data;

    public  ChatGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.HotInfoBean> data) {
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
        ChatGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            /**
             * 判断是自己还是别人
             */
            holder = new ChatGridViewAdapter.ViewHolder();
            if(position%2 == 0) {
                convertView = View.inflate(mContext, R.layout.item_cart_right_grid_view, null);
            }
            else {
                convertView = View.inflate(mContext, R.layout.item_cart_left_grid_view, null);
            }
            holder.image= convertView.findViewById(R.id.image);
            holder.time = convertView.findViewById(R.id.time);
            holder.content = convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        } else {
            holder = (ChatGridViewAdapter.ViewHolder) convertView.getTag();
        }

        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = data.get(position);
//        Glide.with(mContext)
//                .load(Constants.BASE_URl_IMAGE +hotInfoBean.getFigure())
//                .into(holder.ivHot);

        return convertView;
    }

    static class ViewHolder {
        ImageView image;
        TextView time;
        TextView content;
    }

}
