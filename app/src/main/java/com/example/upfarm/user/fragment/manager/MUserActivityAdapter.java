package com.example.upfarm.user.fragment.manager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upfarm.R;
import com.example.upfarm.data.Manager;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.user.fragment.RegisterCheckActivity;

import java.util.List;

public class MUserActivityAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private Manager.ResultBean resultBean;
    private LayoutInflater mLayoutInflater;//用来初始化布局
    //当前类型
    private int currentType=0;
    /**
     * 列表
     */
    public static final int  LIST=0;

    public MUserActivityAdapter(Context mContext, Manager.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater =LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LIST) {
            return new MUserActivityAdapter.ListViewHolder(mLayoutInflater.inflate(R.layout.list_item,null), mContext);
        }
        return null;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private GridView gv_list;
        private Context mContext;
        private MUserGridViewAdapter adapter;

        public ListViewHolder(View itemView, Context mContext) {
            super(itemView);
            gv_list = itemView.findViewById(R.id.gv_list);
            this.mContext = mContext;

        }

        public void setData(final List<Manager.ResultBean.UserBean> data) {
            adapter = new MUserGridViewAdapter(mContext, data);
            gv_list.setAdapter(adapter);
            //点击事件
            gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mContext, RegisterCheckActivity.class);
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);
//                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
//                    String cover_price = data.get(position).getCover_price();
//                    String name = data.get(position).getName();
//                    String figure = data.get(position).getFigure();
//                    String product_id = data.get(position).getProduct_id();
//                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
//
//                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
//                    intent.putExtra(GOODS_BEAN, goodsBean);
//                    mContext.startActivity(intent);
                }
            });
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == LIST) {
            MUserActivityAdapter.ListViewHolder hotViewHolder = (MUserActivityAdapter.ListViewHolder) holder;
            hotViewHolder.setData(resultBean.getUser());
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case LIST:
                currentType =  LIST;
                break;
        }
        return currentType;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
