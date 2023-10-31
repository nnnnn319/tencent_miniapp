package com.example.upfarm.market;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upfarm.R;
import com.example.upfarm.data.Product;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.data.transmit.TransmitProduct;
import com.example.upfarm.home.fragment.bean.ResultBeanData;

import java.util.List;

public class GoodsListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private Product.ResultBean resultBean;
    private LayoutInflater mLayoutInflater;//用来初始化布局
    TransmitData  transmitData = new TransmitData();
    TransmitProduct transmitProduct = new TransmitProduct();

    //当前类型
    private int currentType=0;
    /**
     * 顶部图
     */
    public static final int HOT =0;


    public GoodsListAdapter(Context mContext, Product.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater =LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HOT) {
            return new GoodsListAdapter.HotViewHolder(mLayoutInflater.inflate(R.layout.hot_item, null), mContext);
        }
        return null;
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        private GridView gv_hot;
        private Context mContext;
        private HotGridViewAdapter adapter;

        public HotViewHolder(View itemView, Context mContext) {
            super(itemView);
            gv_hot = itemView.findViewById(R.id.gv_hot);
            this.mContext = mContext;

        }

        public void setData(final List<Product.ResultBean.HotBean> data) {
            adapter = new HotGridViewAdapter(mContext, data);
            gv_hot.setAdapter(adapter);
            //点击事件
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                    String cover_price = String.valueOf(data.get(position).getProduct_numberX());
                    String name = data.get(position).getProduct_nameX();
                    String figure = data.get(position).getProduct_descriptionX();
                    String product_id = String.valueOf(data.get(position).getProduct_idX());
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra("position", position);

                    transmitProduct.setProdcutId(data.get(position).getProduct_idX());
                    transmitProduct.setProductImg(data.get(position).getProduct_imgX());
                    transmitProduct.setProductNumber(data.get(position).getProduct_numberX());
                    transmitProduct.setProductPrice(data.get(position).getProduct_price());
                    transmitProduct.setProductSales(1);
                    transmitProduct.setProductName(data.get(position).getProduct_nameX());
                    transmitProduct.setProductDescription(data.get(position).getProduct_descriptionX());
                    transmitData.transmitProduct = transmitProduct;
                    mContext.startActivity(intent);
//                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                }
            });
        }
    }
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOT) {
            GoodsListAdapter.HotViewHolder hotViewHolder = (GoodsListAdapter.HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot());
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
