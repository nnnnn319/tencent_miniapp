package com.example.upfarm.rent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.upfarm.data.transmit.TransmitData;

import java.util.List;

public class RentFragmentAdapter extends RecyclerView.Adapter<RentFragmentAdapter.RentViewHolder> {
    private Context mContext;
    private Product.ResultBean resultBean;
    private LayoutInflater mLayoutInflater;//用来初始化布局
    private TransmitData transmitData = new TransmitData();


    private TextView landAddress;
    //当前类型
    private int currentType=0;
    /**
     * 顶部图
     */
    public static final int FARM =0;

    public RentFragmentAdapter(Context mContext, Product.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater =LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public RentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == FARM) {
            return new RentFragmentAdapter.RentViewHolder(mContext,mLayoutInflater.inflate(R.layout.list_item,null));
        }
        return null;
    }

    class RentViewHolder extends RecyclerView.ViewHolder {
        private GridView gv_list;
        private Context mContext;
        private RentGridViewAdapter adapter;


        public RentViewHolder(Context mContext, View itemView) {
            super(itemView);
            gv_list = itemView.findViewById(R.id.gv_list);
            this.mContext = mContext;
        }

        public RentGridViewAdapter getAdapter() {
            return  adapter;
        }

        public void setData(final List<Product.ResultBean.RentBean> data) {

            adapter = new RentGridViewAdapter(mContext, data);
            gv_list.setAdapter(adapter);
            //点击事件
            gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mContext,LandSelectionActivity.class);
                    intent.putExtra("position", position);
                    transmitData.rentGroundpositon = resultBean.getRent().get(position).getFarm_id();
                    Log.e("choose land",String.valueOf(position)+" "+String.valueOf(transmitData.rentGroundpositon));
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case FARM:
                currentType = FARM;
                break;
        }
        return currentType;
    }

    @Override
    public void onBindViewHolder(@NonNull RentViewHolder holder, int position) {
        if (getItemViewType(position) == FARM) {
            RentFragmentAdapter.RentViewHolder rentViewHolder = (RentFragmentAdapter.RentViewHolder) holder;
            rentViewHolder.setData(resultBean.getRent());

        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
