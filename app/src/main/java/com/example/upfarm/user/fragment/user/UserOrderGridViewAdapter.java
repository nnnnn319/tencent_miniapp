package com.example.upfarm.user.fragment.user;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.upfarm.R;
import com.example.upfarm.data.Order;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.market.ShowOrderActivity;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.user.fragment.manager.MFarmGridViewAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.internal.ListenerClass;
import okhttp3.Call;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

public class UserOrderGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Order.ResultBean.ProductBean> data;
    private MyRequest myRequest = new MyRequest();
    private String farmName;

    public  UserOrderGridViewAdapter(Context mContext, List<Order.ResultBean.ProductBean> data) {
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
        UserOrderGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_user_order_grid_view, null);
            holder = new UserOrderGridViewAdapter.ViewHolder();
            holder.iv_image = convertView.findViewById(R.id.iv_image);
            holder.tv_time= convertView.findViewById(R.id.tv_time);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_quantity = convertView.findViewById(R.id.tv_quantity);
            holder.tv_total = convertView.findViewById(R.id.tv_total);
            holder.tv_address = convertView.findViewById(R.id.tv_address);
            holder.tv_farm = convertView.findViewById(R.id.tv_farm);
            holder.tv_status = convertView.findViewById(R.id.tv_status);
            holder.send = convertView.findViewById(R.id.send);
            convertView.setTag(holder);
        } else {
            holder = (UserOrderGridViewAdapter.ViewHolder) convertView.getTag();
        }
        /***
         * status 待发货 确认收货 去评价 已评价
         * 商家那边确认发货后 变为确认收货
         * 用户点击确认收获后 变为去评价
         * 用户点击去评价后 跳转到评价界面
         */
         /**
         跳转评价界面
          */
         holder.tv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                Log.e(TAG,"12345678997");
                Intent intent = new Intent(mContext, GotoCommentAvtivity.class);
                intent.putExtra("product", gson.toJson(data.get(position)));
                mContext.startActivity(intent);
            }
        });

        Order.ResultBean.ProductBean hotInfoBean = data.get(position);
//        holder.iv_farm_image.setBackground(mContext.getResources().getDrawable(R.drawable.select_file));
//        holder.tv_farm_name.setText("开心农场");
//        holder.tv_farm_address.setText("￥" + hotInfoBean.getCover_price());
        httpGetFarmInformation(Integer.valueOf(data.get(position).getProduct_id()),holder.tv_name);
        holder.tv_name.setText(String.valueOf(data.get(position).getProduct_id()));
        holder.tv_time.setText(String.valueOf(data.get(position).getProduct_order_date()));
        holder.tv_quantity.setText(String.valueOf(data.get(position).getProduct_items_num()));
        holder.tv_total.setText(String.valueOf(data.get(position).getProduct_total()));
        holder.tv_address.setText(String.valueOf(data.get(position).getAddress()));
        holder.tv_farm.setText(farmName);

        if (data.get(position).getProduct_order_type()==0) {
            holder.tv_status.setText("未发货");
        } else if (data.get(position).getProduct_order_type()==1) {
            holder.tv_status.setText("未收货");
            holder.send.setVisibility(View.VISIBLE);
            holder.send.setText("确认收货");
        } else if (data.get(position).getProduct_order_type()==1){
            holder.tv_status.setText("未评价");
            holder.send.setText("去评价");
        } else {
            holder.tv_status.setText("已评价");
            holder.send.setVisibility(View.GONE);
        }

        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.send.getText().toString()=="确认收货") {
                    data.get(position).setProduct_order_type(2);
                    holder.tv_status.setText("未评价");
                    holder.send.setText("去评价");
                    //发送后端请求
                    OkHttpUtils
                            .get()
                            .url(myRequest.getUrl()+"/goods/comment")
                            .addParams("productId", String.valueOf(data.get(position).getProduct_id()))
                            .addParams("type","2")
                            .build()
                            .execute(new StringCallback()
                            {
                                //请求失败
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.e(ContentValues.TAG,"GoodsList首页请求失败=="+e.getMessage());
                                }
                                //请求成功
                                @Override
                                public void onResponse(String response, int id) {
                                    //解析数据
                                    notifyDataSetChanged();
                                }

                            });
                } else if(holder.send.getText().toString()=="去评价") {
//                    holder.send.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    Log.e(TAG,"12345678997");
                    Intent intent = new Intent(mContext, GotoCommentAvtivity.class);
                    intent.putExtra("product", gson.toJson(data.get(position)));
                    holder.tv_status.setText("已评价");
                    data.get(position).setProduct_order_type(3);
                    holder.send.setVisibility(View.GONE);
                    mContext.startActivity(intent);
                    OkHttpUtils
                            .get()
                            .url(myRequest.getUrl()+"/goods/comment")
                            .addParams("productId", String.valueOf(data.get(position).getProduct_id()))
                            . addParams("type","3")
                            .build()
                            .execute(new StringCallback()
                            {
                                //请求失败
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    Log.e(ContentValues.TAG,"GoodsList首页请求失败=="+e.getMessage());
                                }
                                //请求成功
                                @Override
                                public void onResponse(String response, int id) {
                                    //解析数据
                                    notifyDataSetChanged();
                                }

                            });
                    notifyDataSetChanged();
                }
            }
        });

        holder.iv_image.setImageBitmap(returnBitMap(myRequest.getUrl()+data.get(position).getProduct_order_description()));
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_image;
        TextView tv_time;
        TextView tv_name;
        TextView tv_quantity;
        TextView tv_total;
        TextView tv_farm;
        TextView tv_address;
        TextView tv_status;
        Button send;
    }

    public Bitmap returnBitMap(String url) {

        URL myFileUrl = null;

        Bitmap bitmap = null;

        try {

            myFileUrl = new URL(url);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

        try {

            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();

            conn.setDoInput(true);

            conn.connect();

            InputStream is = conn.getInputStream();

            bitmap = BitmapFactory.decodeStream(is);

            is.close();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return  bitmap;
    }
    public void httpGetFarmInformation(int productId,TextView textView) {
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/product/order/farm")
                .addParams("productId", String.valueOf(productId))
                .build()
                .execute(new StringCallback()
                {
                    //请求失败
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(ContentValues.TAG,"GoodsList首页请求失败=="+e.getMessage());
                    }
                    //请求成功
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(ContentValues.TAG,"GoodsList首页请求成功=="+response);
                        //解析数据
                        JSONObject jsonObject = JSONObject.parseObject(response);
                        Log.e("farmName",jsonObject.getString("farmName"));
                        textView.setText(jsonObject.getString("farmName"));
                    }

                });
    }

}
