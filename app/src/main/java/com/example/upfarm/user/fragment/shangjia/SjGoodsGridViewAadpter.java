package com.example.upfarm.user.fragment.shangjia;

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
import com.example.upfarm.data.Product;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;
import com.example.upfarm.user.fragment.ListGridViewAdapter;
import com.example.upfarm.user.fragment.user.CheckPlantActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class SjGoodsGridViewAadpter  extends BaseAdapter {
    private Context mContext;
    private List<Product.ResultBean.HotBean> data;
    private getBitmap getBitmap = new getBitmap();
    private MyRequest myRequest = new MyRequest();

    public SjGoodsGridViewAadpter(Context mContext, List<Product.ResultBean.HotBean> data) {
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
        SjGoodsGridViewAadpter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_sj_goods_grid_view, null);
            holder = new SjGoodsGridViewAadpter.ViewHolder();
            holder.ivImage = convertView.findViewById(R.id.iv_image);
            holder.tvName= convertView.findViewById(R.id.tv_name);
            holder.tvPrice = convertView.findViewById(R.id.tv_price);
            holder.tvQuantity = convertView.findViewById(R.id.tv_quantity);
            holder.update = convertView.findViewById(R.id.update);
            holder.delete = convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            holder = (SjGoodsGridViewAadpter.ViewHolder) convertView.getTag();
        }

        Product.ResultBean.HotBean hotInfoBean = data.get(position);
//        Glide.with(mContext)
//                .load(Constants.BASE_URl_IMAGE +hotInfoBean.getFigure())
//                .into(holder.ivHot);
//        holder.tvName.setText(hotInfoBean.getName());
//        holder.tvPrice.setText("￥" + hotInfoBean.getCover_price());
//        holder.ivImage.setBackground(mContext.getResources().getDrawable(R.drawable.main_cart));
        holder.ivImage.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+data.get(position).getProduct_imgX()));
        holder.tvName.setText(data.get(position).getProduct_nameX());
        holder.tvPrice.setText("￥"+String.valueOf(data.get(position).getProduct_price()));
        holder.tvQuantity.setText(String.valueOf(data.get(position).getProduct_numberX()));
        holder.ivImage.setBackground(mContext.getResources().getDrawable(R.drawable.main_cart));
        TransmitData transmitData = new TransmitData();
        if (transmitData.fix == "") {
            holder.tvQuantity.setText((transmitData.fix));
            transmitData.fix = "";
        }
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 要传参显示原来的在输入框中
                 */
                Intent intent = new Intent(mContext, NewAndUpdateGoodsActivity.class);
                intent.putExtra("position", position);
                TransmitData transmitData = new TransmitData();
                transmitData.updateGood = data.get(position);
                mContext.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                data.remove(position);
                                Toast.makeText(mContext,"删除成功",Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                //解析数据
                            }

                        });
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView ivImage;
        TextView tvName;
        TextView tvPrice;
        TextView tvQuantity;
        Button update;
        Button delete;
    }
}
