package com.example.upfarm.rent;

import android.content.Context;
import android.util.Log;
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
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.market.GoodsListAdapter;
import com.example.upfarm.market.HotGridViewAdapter;

import java.util.List;

public class LandSelectionAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private Product.ResultBean resultBean;
    private LayoutInflater mLayoutInflater;//用来初始化布局

    //当前类型
    private int currentType=0;
    /**
     * 顶部图
     */
    public static final int SEED =0;


    public LandSelectionAdapter(Context mContext, Product.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater =LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SEED) {
            return new LandSelectionAdapter.SeedViewHolder(mLayoutInflater.inflate(R.layout.seed_item, null), mContext);
        }
        return null;
    }

    class SeedViewHolder extends RecyclerView.ViewHolder {
        private GridView gv_seed;
        private Context mContext;
        private SeedGridViewAdapter adapter;

        public SeedViewHolder(View itemView, Context mContext) {
            super(itemView);
            gv_seed = itemView.findViewById(R.id.gv_seed);
            this.mContext = mContext;

        }

        public void setData(final List<Product.ResultBean.HotBean> data) {
//            Log.e("value",data.get(0).getProduct_descriptionX());
            adapter = new SeedGridViewAdapter(mContext,data);
            gv_seed.setAdapter(adapter);
            //点击事件
            gv_seed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                    String cover_price = String.valueOf(data.get(position).getProduct_numberX());
                    String name = data.get(position).getProduct_nameX();
                    String figure = data.get(position).getProduct_descriptionX();
                    String product_id = String.valueOf(data.get(position).getProduct_idX());
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
    public int getItemViewType(int position) {
        switch (position) {
            case SEED:
                currentType = SEED;
                break;
        }
        return currentType;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == SEED) {
            LandSelectionAdapter.SeedViewHolder seedViewHolder = ( LandSelectionAdapter.SeedViewHolder) holder;
            seedViewHolder.setData(resultBean.getHot());
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
