package com.example.upfarm.user.fragment.shangjia;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upfarm.R;
import com.example.upfarm.data.Order;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;
import com.example.upfarm.user.fragment.manager.MFarmGridViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class SjGoodsOrderGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Order.ResultBean.ProductBean> data;
    private com.example.upfarm.sometools.getBitmap getBitmap = new getBitmap();
    private MyRequest myRequest = new MyRequest();

    public  SjGoodsOrderGridViewAdapter(Context mContext, List<Order.ResultBean.ProductBean> data) {
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
        SjGoodsOrderGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_sj_goods_order_grid_view, null);
            holder = new  SjGoodsOrderGridViewAdapter.ViewHolder();
            holder.iv_image = convertView.findViewById(R.id.iv_image);
            holder.tv_time= convertView.findViewById(R.id.tv_time);
            holder.tv_person = convertView.findViewById(R.id.tv_person);
            holder.tv_quantity = convertView.findViewById(R.id.tv_quantity);
            holder.tv_total= convertView.findViewById(R.id.tv_total);
            holder.tv_phone = convertView.findViewById(R.id.tv_phone);
            holder.tv_address = convertView.findViewById(R.id. tv_address);
            holder.tv_status = convertView.findViewById(R.id.  tv_status);
            holder.send = convertView.findViewById(R.id.send);
            convertView.setTag(holder);
        } else {
            holder = (SjGoodsOrderGridViewAdapter.ViewHolder) convertView.getTag();
        }
        /**
         * 状态有三种 待发货、待收货、已收货
         * 用户付款后 用户：待发货 商家 待发货
         * 商家点击待发货状态 用户：待收货  商家 待收货
         * 用户点击确认收货 用户：去评价  商家 已收货
         */
        /**
         * 如果是待发货的
         */

//        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = data.get(position);
//        holder.iv_farm_image.setBackground(mContext.getResources().getDrawable(R.drawable.select_file));
//        holder.tv_farm_name.setText("开心农场");
//        holder.tv_farm_address.setText("￥" + hotInfoBean.getCover_price());
        holder.iv_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+data.get(position).getProduct_order_description()));
        holder.tv_address.setText(data.get(position).getProduct_address());
        holder.tv_person.setText(data.get(position).getProduct_username());
        holder.tv_phone.setText(data.get(position).getProduct_phone());
        holder.tv_quantity.setText(String.valueOf(data.get(position).getProduct_items_num()));
        holder.tv_total.setText(String.valueOf(data.get(position).getProduct_total()));
        holder.tv_time.setText(String.valueOf(data.get(position).getProduct_order_date()));
        if (data.get(position).getProduct_order_type() == 0) {
            holder.tv_status.setText("未发货");
            holder.send.setVisibility(View.VISIBLE);
        } else if(data.get(position).getProduct_order_type() == 1) {
            holder.tv_status.setText("未收货");
            holder.send.setVisibility(View.GONE);
        } else if(data.get(position).getProduct_order_type() == 2||data.get(position).getProduct_order_type() == 3) {
            holder.tv_status.setText("已确认收货");
            holder.send.setVisibility(View.GONE);
        }
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tv_status.setText("已发货");
                holder.send.setVisibility(View.GONE);
                //后台修改订单状态
                httpFixOrderStatus(data.get(position).getProduct_order_id());
            }
        });


        return convertView;
    }

    static class ViewHolder {
        ImageView iv_image;
        TextView tv_time;
        TextView tv_person;
        TextView tv_quantity;
        TextView tv_total;
        TextView tv_phone;
        TextView tv_address;
        TextView tv_status;
        Button send;
    }
    public void httpFixOrderStatus(int productOrderId) {
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/sj/order/send")
                .addParams("productOrderId", String.valueOf(productOrderId))
                .build()
                .execute(new StringCallback()
                {
                    //请求失败
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,"GoodsList首页请求失败=="+e.getMessage());
                    }
                    //请求成功
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG,"GoodsList首页请求成功=="+response);
                        //解析数据
                        Toast.makeText(mContext, "发货成功", Toast.LENGTH_SHORT).show();

                    }

                });
    }
}
