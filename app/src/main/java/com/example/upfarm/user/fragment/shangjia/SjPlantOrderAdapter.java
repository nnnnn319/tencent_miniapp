package com.example.upfarm.user.fragment.shangjia;

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
import com.example.upfarm.data.Order;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.user.fragment.RegisterCheckActivity;

import java.util.List;

public class SjPlantOrderAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private Order.ResultBean resultBean;
    private LayoutInflater mLayoutInflater;//用来初始化布局
    //当前类型
    private int currentType=0;
    /**
     * 列表
     */
    public static final int  LIST=0;

    public SjPlantOrderAdapter(Context mContext, Order.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater =LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LIST) {
            return new SjPlantOrderAdapter.ListViewHolder(mLayoutInflater.inflate(R.layout.list_item,null), mContext);
        }
        return null;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private GridView gv_list;
        private Context mContext;
        private SjPlantOrderGridViewAdapter adapter;

        public ListViewHolder(View itemView, Context mContext) {
            super(itemView);
            gv_list = itemView.findViewById(R.id.gv_list);
            this.mContext = mContext;

        }

        public void setData(final List<Order.ResultBean.FarmBean> data) {
            adapter = new SjPlantOrderGridViewAdapter(mContext, data);
            gv_list.setAdapter(adapter);
    }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == LIST) {
            SjPlantOrderAdapter.ListViewHolder hotViewHolder = (SjPlantOrderAdapter.ListViewHolder) holder;
            hotViewHolder.setData(resultBean.getFarm());
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
