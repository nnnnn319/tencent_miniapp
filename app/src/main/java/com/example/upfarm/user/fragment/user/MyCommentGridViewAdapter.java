package com.example.upfarm.user.fragment.user;

import android.content.ContentValues;
import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.upfarm.R;
import com.example.upfarm.data.Comment;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class MyCommentGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment.ResultBean.CommentBean> data;
    private getBitmap getBitmap = new getBitmap();
    private MyRequest myRequest = new MyRequest();

    public MyCommentGridViewAdapter(Context mContext, List<Comment.ResultBean.CommentBean> data) {
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
        MyCommentGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = android.view.View.inflate(mContext, R.layout.item_goods_comment_grid_view, null);
            holder = new MyCommentGridViewAdapter.ViewHolder();
            holder.name= convertView.findViewById(R.id.tv_name);
            holder.time= convertView.findViewById(R.id.tv_time);
            holder.comment= convertView.findViewById(R.id.tv_comment);
            holder.product_image= convertView.findViewById(R.id.iv_image);
            holder.iv_image= convertView.findViewById(R.id.iv_comment);
            holder.tv_farm = convertView.findViewById(R.id.tv_farm);
            convertView.setTag(holder);
        } else {
            holder = (MyCommentGridViewAdapter.ViewHolder) convertView.getTag();
        }
        /**
         holder.iv_comment.setVisibility(View.GONE);如果用户评价的时候没有图片
         setVisibility(View.VISIBLE);有图片
         */
        Comment.ResultBean.CommentBean hotInfoBean = data.get(position);
        httpGetCommentInformation(hotInfoBean.getProduct_id());
        holder.comment.setText(hotInfoBean.getComment_value());
        holder.time.setText(hotInfoBean.getComment_time());
        holder.name.setText(hotInfoBean.getFarm_name());
        //修改
//        if (hotInfoBean.getComment_imag() == null) {
//            holder.product_image.setVisibility(View.GONE);
//        } else {
//            holder.product_image.setVisibility(View.VISIBLE);
//            holder.product_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+hotInfoBean.getComment_imag()));
//        }
        if (hotInfoBean.getComment_imag() == null) {
            holder.iv_image.setVisibility(View.GONE);
        } else {
            holder.iv_image.setVisibility(View.VISIBLE);
            holder.iv_image.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+hotInfoBean.getComment_imag()));
        }

        holder.name.setText("暂时没有这个值");
        return convertView;
    }

    static class ViewHolder {
//<<<<<<< HEAD
        TextView name;
        TextView time;
        TextView comment;
        ImageView product_image;
        ImageView iv_image;
        TextView tv_farm;
//=======
//        TextView username;
//        TextView tv_time;
//        TextView tv_comment;
//        ImageView iv_comment;
    }
    public void httpGetCommentInformation(int productId) {
        //获取店铺图片 店铺名
        OkHttpUtils
                .get()
                .url(myRequest.getUrl()+"/product/comment/information")
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
                        Log.e("productImg",jsonObject.getString("product_img"));
                        Log.e("productName",jsonObject.getString("product_name"));
                    }

                });
    }
}
