package com.example.upfarm.user.fragment.shangjia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.upfarm.LoginNewActivity;
import com.example.upfarm.R;
import com.example.upfarm.data.Manager;
import com.example.upfarm.data.Message;
import com.example.upfarm.home.fragment.bean.ResultBeanData;

import java.util.List;

public class MessageGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Message.ResultBean.MessageBean> data;

    public  MessageGridViewAdapter(Context mContext, List<Message.ResultBean.MessageBean> data) {
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
        Log.e("print value","f");
        MessageGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_message_grid_view, null);
            holder = new  MessageGridViewAdapter.ViewHolder();
            holder.name = convertView.findViewById(R.id.name);
//            holder.point= convertView.findViewById(R.id.point);
            holder.image = convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = ( MessageGridViewAdapter.ViewHolder) convertView.getTag();
        }
//        holder.point.setVisibility(View.VISIBLE);
        /**
         * point是红点如果有信息来就显示 holder.point.setVisibility(View.VISIBLE);
         * 没有会不显示 holder.point.setVisibility(View.GONE);
         */

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LoginNewActivity.class);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
                //退出登录 成为管理员
                Activity activity = (Activity) mContext;
                activity.finish();
            }
        });
//        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = data.get(position);
//        holder.iv_farm_image.setBackground(mContext.getResources().getDrawable(R.drawable.select_file));
//        holder.tv_farm_name.setText("开心农场");
//        holder.tv_farm_address.setText("￥" + hotInfoBean.getCover_price());
        return convertView;
    }

    static class ViewHolder {
        TextView name;
//        ImageView point;
        ImageView image;

    }


}
