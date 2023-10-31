package com.example.upfarm.market;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.upfarm.R;
import com.example.upfarm.data.Comment;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.sometools.getBitmap;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class GoodsCommentGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment.ResultBean.CommentBean> data;
    private getBitmap getBitmap = new getBitmap();
    private MyRequest myRequest = new MyRequest();

    public GoodsCommentGridViewAdapter(Context mContext, List<Comment.ResultBean.CommentBean> data) {
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
        GoodsCommentGridViewAdapter.ViewHolder holder;
        httpGetUserName(data.get(position).getUser_id());

        if (convertView == null) {
            convertView = android.view.View.inflate(mContext, R.layout.item_my_comment_grid_view, null);
            holder = new GoodsCommentGridViewAdapter.ViewHolder();
            holder.tv_name= convertView.findViewById(R.id.tv_name);
            holder.tv_time= convertView.findViewById(R.id.tv_time);
            holder.tv_comment= convertView.findViewById(R.id.tv_comment);
            holder.iv_comment= convertView.findViewById(R.id.iv_comment);
            OkHttpUtils
                    .get()
                    .url(myRequest.getUrl()+"/comment/username/get")
                    .addParams("userId", String.valueOf(String.valueOf(data.get(position).getUser_id())))
                    .build()
                    .execute(new StringCallback()
                    {
                        //请求失败
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e(TAG,"商品评价请求失败=="+e.getMessage());
                        }
                        //请求成功
                        @Override
                        public void onResponse(String response, int id) {
                            Log.e(TAG,"GoodsList首页请求成功=="+response);
                            //解析数据
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                holder.tv_name.setText(jsonObject.getString("username"));
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    });
            convertView.setTag(holder);
        } else {
            holder = (GoodsCommentGridViewAdapter.ViewHolder) convertView.getTag();
        }
        /**
         holder.iv_comment.setVisibility(View.GONE);如果用户评价的时候没有图片
         setVisibility(View.VISIBLE);有图片
         */
//        holder.iv_comment.setVisibility(View.GONE);
        Comment.ResultBean.CommentBean hotInfoBean = data.get(position);
        holder.tv_comment.setText(hotInfoBean.getComment_value());
        holder.tv_time.setText(hotInfoBean.getComment_time());
        if (hotInfoBean.getComment_imag() == null) {
            holder.iv_comment.setVisibility(View.GONE);
        } else {
            holder.iv_comment.setVisibility(View.VISIBLE);
            holder.iv_comment.setImageBitmap(getBitmap.returnBitmap(myRequest.getUrl()+hotInfoBean.getComment_imag()));
        }

        holder.tv_name.setText("暂时没有这个值");
        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_time;
        TextView tv_comment;
        ImageView iv_comment;
    }
    void httpGetUserName(int userId) {

    }

}
