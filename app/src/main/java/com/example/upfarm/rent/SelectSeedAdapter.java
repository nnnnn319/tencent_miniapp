package com.example.upfarm.rent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upfarm.R;
import com.example.upfarm.data.Product;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.user.fragment.RegisterCheckActivity;
import com.example.upfarm.user.fragment.manager.MFarmActivityAdapter;
import com.example.upfarm.user.fragment.manager.MFarmGridViewAdapter;

import java.util.List;

public class SelectSeedAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private Product.ResultBean resultBean;
    private LayoutInflater mLayoutInflater;//用来初始化布局
    private TextView total;
    //当前类型
    private int currentType=0;
    /**
     * 列表
     */
    public static final int  LIST=0;

    public SelectSeedAdapter(Context mContext, Product.ResultBean resultBean, TextView total) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater =LayoutInflater.from(mContext);
        this.total = total;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LIST) {
            return new SelectSeedAdapter.ListViewHolder(mLayoutInflater.inflate(R.layout.list_item,null), mContext);
        }
        return null;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private GridView gv_list;
        private Context mContext;
        private SelectSeedGridViewAdapter adapter;

        public ListViewHolder(View itemView, Context mContext) {
            super(itemView);
            gv_list = itemView.findViewById(R.id.gv_list);
            this.mContext = mContext;

        }

        public void setData(final List<Product.ResultBean.HotBean> data) {
            adapter = new SelectSeedGridViewAdapter(mContext, data,total);
            gv_list.setAdapter(adapter);
            //点击事件
            gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mContext, RegisterCheckActivity.class);
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);
                }
            });
        }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == LIST) {
           SelectSeedAdapter.ListViewHolder hotViewHolder = (SelectSeedAdapter.ListViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot());
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
