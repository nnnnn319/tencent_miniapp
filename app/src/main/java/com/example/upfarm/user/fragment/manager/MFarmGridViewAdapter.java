package com.example.upfarm.user.fragment.manager;

import android.content.Context;
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
import com.example.upfarm.data.Manager;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.user.fragment.shangjia.SjSeedGridViewAdapter;
import com.example.upfarm.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class MFarmGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Manager.ResultBean.FarmBean> data;
    private MyRequest myRequest = new MyRequest();

    public  MFarmGridViewAdapter(Context mContext, List<Manager.ResultBean.FarmBean> data) {
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
        MFarmGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_m_farm_grid_view, null);
            holder = new  MFarmGridViewAdapter.ViewHolder();
            holder.iv_farm_image = convertView.findViewById(R.id.iv_farm_image);
            holder.tv_farm_name= convertView.findViewById(R.id.tv_farm_name);
            holder.tv_farm_address = convertView.findViewById(R.id.tv_farm_address);
            holder.delete = convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            holder = (MFarmGridViewAdapter.ViewHolder) convertView.getTag();
        }

        Manager.ResultBean.FarmBean hotInfoBean = data.get(position);
//        holder.iv_farm_image.setBackground(mContext.getResources().getDrawable(R.drawable.select_file));
        Glide.with(mContext)
                .load( myRequest.getUrl()+hotInfoBean.getFarm_img())
                .into(holder.iv_farm_image);
        holder.tv_farm_name.setText(hotInfoBean.getFarm_name());
        holder.tv_farm_address.setText(hotInfoBean.getFarm_x()+" 经纬度："+hotInfoBean.getFarm_y());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送删除消息
                OkHttpUtils
                        .get()
                        .addParams("farmId", String.valueOf(data.get(position).getFarm_id()))
                        .url(myRequest.getUrl()+"/farm/delete")
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
                                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                            }

                        });
            }
        });
        return convertView;

    }

    static class ViewHolder {
        ImageView iv_farm_image;
        TextView tv_farm_name;
        TextView tv_farm_address;
        Button delete;
    }
}
