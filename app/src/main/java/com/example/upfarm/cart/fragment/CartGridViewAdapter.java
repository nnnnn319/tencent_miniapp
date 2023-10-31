package com.example.upfarm.cart.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upfarm.R;
import com.example.upfarm.data.Cart;
import com.example.upfarm.data.Product;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.data.transmit.TransmitProduct;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.market.SureBuyActivity;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;
import com.example.upfarm.user.fragment.manager.MFarmGridViewAdapter;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class CartGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Cart.ResultBean.CartBean> data;
    private com.example.upfarm.sometools.getBitmap getBitmap = new getBitmap();
    private MyRequest myRequest = new MyRequest();
    private TransmitData transmitData = new TransmitData();
    private Product.ResultBean.HotBean hotBean = new Product.ResultBean.HotBean();

    public  CartGridViewAdapter(Context mContext, List<Cart.ResultBean.CartBean> data) {
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
        CartGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_cart_grid_view, null);
            holder = new  CartGridViewAdapter.ViewHolder();
            holder.iv_image = convertView.findViewById(R.id.iv_image);
            holder.tv_price= convertView.findViewById(R.id.tv_price);
            holder.tv_quantity = convertView.findViewById(R.id.tv_quantity);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_total = convertView.findViewById(R.id.tv_total);
            holder.delete = convertView.findViewById(R.id.delete);
            holder.pay = convertView.findViewById(R.id.pay);
            convertView.setTag(holder);
            Log.e("Cart",String.valueOf(data.get(position).getCart_product_id()));
            initTransmit(data.get(position).getCart_product_id());

        } else {
            holder = (CartGridViewAdapter.ViewHolder) convertView.getTag();
        }
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SureBuyActivity.class);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils
                        .get()
                        .url(myRequest.getUrl()+"/cart/product/delete")
                        .addParams("id",String.valueOf(data.get(position).getId()))
                        .build()
                        .execute(new StringCallback()
                        {
                            //请求失败
                            @Override
                            public void onError(Call call, Exception e, int id) {
//                        Log.e(TAG,"首页请求失败=="+e.getMessage());
                            }
                            //请求成功
                            @Override
                            public void onResponse(String response, int id) {
                                data.remove(position);
                                Log.e("talk","talk");
                                Log.e(TAG,"首页请求成功=="+response);
                                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }

                        });
            }
        });
        Cart.ResultBean.CartBean hotInfoBean = data.get(position);
        holder.tv_name.setText(hotInfoBean.getCart_product_name());
        holder.tv_price.setText(String.valueOf(hotInfoBean.getCart_price()));
        holder.tv_quantity.setText(String.valueOf(hotInfoBean.getCart_number()));
        holder.tv_total.setText(String.valueOf(hotInfoBean.getCart_total()));
        holder.iv_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+hotInfoBean.getCart_img()));
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_image;
        TextView tv_name;
        TextView tv_price;
        TextView tv_quantity;
        TextView tv_total;
        Button delete;
        Button pay;
    }
    private void initTransmit(int productId) {
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/cart/product")
                .addParams("productId",String.valueOf(productId))
                .build()
                .execute(new StringCallback()
                {
                    //请求失败
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        Log.e(TAG,"首页请求失败=="+e.getMessage());
                    }
                    //请求成功
                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            Gson gson = new Gson();
                            Product product = gson.fromJson(response,Product.class);
                            hotBean = product.getResult().getHot().get(0);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.e(TAG,"首页请求成功=="+response);
                        //解析数据
//                        TransmitProduct transmitProduct = new TransmitProduct();
//                        transmitProduct.setProductName(hotBean.getProduct_nameX());
//                        transmitProduct.setProductSales(hotBean.getProduct_numberX());
//                        transmitProduct.setProductPrice(hotBean.getProduct_price());
//                        transmitProduct.setProductNumber(hotBean.getProduct_numberX());
//                        transmitProduct.setProductImg(hotBean.getProduct_imgX());
//                        transmitProduct.setProdcutId(hotBean.getProduct_idX());
//                        transmitData.transmitProduct = transmitProduct;
                    }

                });

    }
}
