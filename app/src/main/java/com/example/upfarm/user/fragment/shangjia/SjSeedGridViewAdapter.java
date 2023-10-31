package com.example.upfarm.user.fragment.shangjia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.upfarm.R;
import com.example.upfarm.data.Product;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class SjSeedGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product.ResultBean.HotBean> data;
    private MyRequest myRequest = new MyRequest();

    public SjSeedGridViewAdapter(Context mContext, List<Product.ResultBean.HotBean> data) {
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
        SjSeedGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_sj_seed_grid_view, null);
            holder = new SjSeedGridViewAdapter.ViewHolder();
            holder.ivImage = convertView.findViewById(R.id.iv_image);
            holder.tvName= convertView.findViewById(R.id.tv_name);
            holder.tvPrice = convertView.findViewById(R.id.tv_price);
            holder.tvQuantity = convertView.findViewById(R.id.tv_quantity);
            holder.tvSell = convertView.findViewById(R.id.tv_sell);
            holder.tvGrow = convertView.findViewById(R.id.tv_grow);
            holder.update = convertView.findViewById(R.id.update);
            holder.delete = convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            holder = (SjSeedGridViewAdapter.ViewHolder) convertView.getTag();
        }
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 要传参显示原来的在输入框中
                 */
                Intent intent = new Intent(mContext, NewAndUpdateSeedActivity.class);
                Gson gson = new Gson();
//                Bundle bundle = new Bundle();
//                bundle.putString("selectSeed",gson.toJson(data.get(position)));
                TransmitData transmitData = new TransmitData();
                transmitData.updateSeed = data.get(position);
                Log.e("transmitData",transmitData.updateSeed.getProduct_nameX());
                mContext.startActivity(intent);
//                Toast.makeText(mContext,"修改成功", Toast.LENGTH_SHORT);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //请求服务器 删除该资源
                OkHttpUtils
                        .get()
                        .url(myRequest.getUrl()+"/sj/seed/delete")
                        .addParams("productId", String.valueOf(data.get(position).getProduct_idX()))
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
                                data.remove(position);
                                Toast.makeText(mContext,"删除成功",Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }

                        });
            }
        });
        Product.ResultBean.HotBean hotInfoBean = data.get(position);
        Glide.with(mContext)
                .load(myRequest.getUrl()+hotInfoBean.getProduct_imgX())
                .into(holder.ivImage);
        holder.tvName.setText(data.get(position).getProduct_nameX());
        holder.tvPrice.setText(String.valueOf(data.get(position).getProduct_price()));
        holder.tvQuantity.setText(String.valueOf(data.get(position).getProduct_numberX()));
        holder.tvSell.setText("销售量 : 13");
        holder.tvGrow.setText(data.get(position).getProduct_descriptionX());
        return convertView;
    }
    static class ViewHolder {
        ImageView ivImage;
        TextView tvName;
        TextView tvPrice;
        TextView tvQuantity;
        TextView tvSell;
        TextView tvGrow;
        Button update;
        Button delete;
    }
}
