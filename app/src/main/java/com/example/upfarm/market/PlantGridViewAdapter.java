package com.example.upfarm.market;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.upfarm.R;
import com.example.upfarm.data.Product;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;

import java.util.List;

public class PlantGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product.ResultBean.HotBean> data;
    private getBitmap getBitmap = new getBitmap();
    private MyRequest myRequest = new MyRequest();

    public  PlantGridViewAdapter(Context mContext, List<Product.ResultBean.HotBean> data) {
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
        PlantGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = android.view.View.inflate(mContext, R.layout.item_plant_grid_view, null);
            holder = new PlantGridViewAdapter.ViewHolder();
            holder.name= convertView.findViewById(R.id.name);
            holder.price= convertView.findViewById(R.id.price);
            holder.quantity= convertView.findViewById(R.id.quantity);
            holder.image= convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (PlantGridViewAdapter.ViewHolder) convertView.getTag();
        }

        Product.ResultBean.HotBean hotInfoBean = data.get(position);
        holder.name.setText(hotInfoBean.getProduct_nameX());
        holder.image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+hotInfoBean.getProduct_imgX()));
        holder.price.setText(String.valueOf(hotInfoBean.getProduct_price()));
        holder.quantity.setText(String.valueOf(hotInfoBean.getProduct_numberX()));
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView price;
        TextView quantity;
        ImageView image;
    }
}
