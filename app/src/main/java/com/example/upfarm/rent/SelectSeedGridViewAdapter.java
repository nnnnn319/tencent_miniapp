package com.example.upfarm.rent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.upfarm.R;
import com.example.upfarm.cart.fragment.view.AddSubView;
import com.example.upfarm.data.Product;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.user.fragment.manager.MFarmGridViewAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

public class SelectSeedGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product.ResultBean.HotBean> data;
    private MyRequest myRequest = new MyRequest();
    private TransmitData transmitData = new TransmitData();
    private TextView total;
    int totalPrice=0;
    public  SelectSeedGridViewAdapter(Context mContext, List<Product.ResultBean.HotBean> data,TextView total) {
        this.mContext = mContext;
        this.data = data;
        this.total = total;
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
        SelectSeedGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_select_seed_grid_view, null);
            holder = new  SelectSeedGridViewAdapter.ViewHolder();
            holder.image = convertView.findViewById(R.id.image);
            holder.name= convertView.findViewById(R.id.name);
            holder.price= convertView.findViewById(R.id.price);
            holder.total = convertView.findViewById(R.id.total);
            holder.quantity = convertView.findViewById(R.id.quantity);
            holder.number = convertView.findViewById(R.id.number);
            convertView.setTag(holder);
        } else {
            holder = (SelectSeedGridViewAdapter.ViewHolder) convertView.getTag();
        }
        holder.quantity.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                //单价*数量
                int quantity = holder.quantity.getValue();
                int pre_total;
                pre_total = quantity*data.get(position).getProduct_price();
//                int total_pre = Integer.valueOf(holder.total.getText().toString());
//                holder.total.setText(total_pre + quantity*data.get(position).getProduct_price());
                holder.total.setText(""+pre_total);
                boolean isExist = false;
                if (quantity >= 1) {
                    for (int i=0; i<transmitData.hotBeans.size();i++) {
                        if (transmitData.hotBeans.get(i).getProduct_idX() == data.get(position).getProduct_idX()) {
                            transmitData.hotBeans.get(i).setProduct_numberX(quantity);
                            isExist = true;
                            break;
                        }
                    }
                }
                if (!isExist) {
                    data.get(position).setProduct_numberX(quantity);
                    transmitData.hotBeans.add(data.get(position));
                }
                for (int i=0;i<transmitData.hotBeans.size();i++) {
                    totalPrice += transmitData.hotBeans.get(i).getProduct_numberX()*transmitData.hotBeans.get(i).getProduct_price();
                }
//                totalPrice += data.get(position).getProduct_numberX()*data.get(position).getProduct_price();
                total.setText(String.valueOf(totalPrice));
                totalPrice=0;

            }
        });
//        total.setText("10");
        /**
         * 思路：设置一个数组，里面存quantity>0的Item的信息
         */
        holder.quantity.setValue(0);
        Product.ResultBean.HotBean hotInfoBean = data.get(position);
        holder.image.setImageBitmap(returnBitMap(myRequest.getUrl()+data.get(position).getProduct_imgX()));
        holder.name.setText(data.get(position).getProduct_nameX());
        holder.price.setText("单价： "+String.valueOf(data.get(position).getProduct_price())+" /㎡");
        holder.number.setText("数量："+ data.get(position).getProduct_numberX()+" ㎡");

        return convertView;
    }
//    public void addTotal() {
//
//    }
    static class ViewHolder {
        ImageView image;
        TextView  name;
        TextView price;
        TextView total;
        AddSubView quantity;
        TextView number;
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
}
