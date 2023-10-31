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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class MUserGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Manager.ResultBean.UserBean> data;
    private MyRequest myRequest = new MyRequest();

    public  MUserGridViewAdapter(Context mContext, List<Manager.ResultBean.UserBean> data) {
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
        MUserGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_m_user_grid_view, null);
            holder = new  MUserGridViewAdapter.ViewHolder();
            holder.ivImage = convertView.findViewById(R.id.iv_image);
            holder.tvName= convertView.findViewById(R.id.tv_name);
            holder.tvPerson = convertView.findViewById(R.id.tv_person);
            holder.tvAddress= convertView.findViewById(R.id.tv_address);
            holder.delete = convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            holder = (MUserGridViewAdapter.ViewHolder) convertView.getTag();
        }

        Manager.ResultBean.UserBean hotInfoBean = data.get(position);
//        Glide.with(mContext)
//                .load(myRequest.getUrl() +hotInfoBean.get())
//                .into(holder.ivHot);
        holder.tvName.setText(hotInfoBean.getUser_phone());
        holder.tvPerson.setText(hotInfoBean.getUser_name());
        holder.tvAddress.setText(hotInfoBean.getUser_birthday());
//        holder.tvPrice.setText(hotInfoBean.getUser_name());
        holder.ivImage.setBackground(mContext.getResources().getDrawable(R.drawable.main_cart));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils
                        .get()
                        .addParams("farmId", String.valueOf(data.get(position).getUser_id()))
                        .url(myRequest.getUrl()+"/user/delete")
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
        ImageView ivImage;
        TextView tvName;
        TextView tvPerson;
        TextView tvAddress;
        Button delete;
    }
}
