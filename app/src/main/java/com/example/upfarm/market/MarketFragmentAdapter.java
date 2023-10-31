package com.example.upfarm.market;

import android.content.Context;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
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
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.data.transmit.TransmitProduct;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import java.util.List;


class MarketFragmentAdapter extends RecyclerView.Adapter {
    private TransmitData transmitData = new TransmitData();
    private Context mContext;
//    private ResultBeanData.ResultBean resultBean;
private Product.ResultBean resultBean;
    private LayoutInflater mLayoutInflater;//用来初始化布局
    //当前类型
    private int currentType=0;
    /**
     * 顶部图
     */
     public static final int TOP =0;
    /**
     * 频道(类别)
     */
    public static final int CHANNEL = 1;
        /**
     * 热卖
     */
    public static final int HOT = 2;

//    public MarketFragmentAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
public MarketFragmentAdapter(Context mContext, Product.ResultBean resultBean) {

        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater =LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TOP) {
            return new TopViewHolder(mContext,mLayoutInflater.inflate(R.layout.top_item,null));
        }
        else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mContext,mLayoutInflater.inflate(R.layout.channel_item,null));
        }
        else if (viewType == HOT) {
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.hot_item, null), mContext);
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
        public HotGridViewAdapter getAdapter() {
            return  adapter;
        }

        public void setData(final List<Product.ResultBean.HotBean> data) {
            HotGridViewAdapter adapter = new HotGridViewAdapter(mContext, data);

            gv_hot.setAdapter(adapter);
            //点击事件
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("market click", String.valueOf(position));
                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                    String cover_price = String .valueOf(data.get(position).getProduct_numberX());
                    String name = data.get(position).getProduct_nameX();
                    String figure = data.get(position).getProduct_descriptionX();
                    String product_id = String .valueOf(data.get(position).getProduct_idX());
                    TransmitProduct transmitProduct = new TransmitProduct();
                    transmitProduct.setProductName(data.get(position).getProduct_nameX());
                    transmitProduct.setProductSales(1);
                    transmitProduct.setProductPrice(data.get(position).getProduct_price());
                    transmitProduct.setProductNumber(data.get(position).getProduct_numberX());
                    transmitProduct.setProductImg(data.get(position).getProduct_imgX());
                    transmitProduct.setProdcutId(data.get(position).getProduct_idX());
                    transmitProduct.setProductDescription(data.get(position).getProduct_descriptionX());
                    transmitData.transmitProduct = transmitProduct;

                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);

                    mContext.startActivity(intent);

//                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
//
//                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
//                    intent.putExtra(GOODS_BEAN, goodsBean);
//                    mContext.startActivity(intent);
                }
            });
        }

    }

    class TopViewHolder extends RecyclerView.ViewHolder{
        private GridView gvTop;
        private Context mContext;
        private TopAdapter adapter;

        public TopViewHolder(Context mContext, View itemView){
            super(itemView);
            this.mContext = mContext;
            this.gvTop = itemView.findViewById(R.id.gv_top);
        }
        public void setData() {
            adapter = new TopAdapter(mContext);
            gvTop.setAdapter(adapter);
        }
        }

    class ChannelViewHolder extends RecyclerView.ViewHolder {

        public GridView gvChannel;
        private Context mContext;
        private ChannelAdapter adapter;

        public ChannelViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.gvChannel = itemView.findViewById(R.id.gv_channel);
        }

        public void setData(final List<Product.ResultBean.ChannelBean> channel_info) {


            //得到数据了，设置GridView的适配器
            adapter = new ChannelAdapter(mContext,channel_info);
            gvChannel.setAdapter(adapter);

            //点击事件
            gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
//                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
//                    if (position ==0 ) {
                        transmitData.marketChnannelType = position;
                        Intent intent = new Intent(mContext, GoodsListActivity.class);
                        intent.putExtra("position", position);
                        mContext.startActivity(intent);
                    //}

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case TOP:
                currentType = TOP;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel());
        }
        else if (getItemViewType(position) == TOP) {
            TopViewHolder topViewHolder = (TopViewHolder) holder;
            topViewHolder.setData();
        }
        else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot());
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
