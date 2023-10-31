package com.example.upfarm.home.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.upfarm.R;
import com.example.upfarm.data.Product;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.data.transmit.TransmitProduct;
import com.example.upfarm.market.GoodsInfoActivity;

import com.example.upfarm.utils.Constants;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.List;

public class HomeFragmentAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private Product.ResultBean dataBean;
    private LayoutInflater mLayoutInflater;//用来初始化布局
    //当前类型
    private int currentType=0;
    /**
     * 横幅广告
     */
    public static final int ACT = 0;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 1;

    public TransmitData transmitData = new TransmitData();
    public TransmitProduct transmitProduct = new TransmitProduct();



//    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
//        this.mContext = mContext;
//        this.resultBean = resultBean;
//        mLayoutInflater =LayoutInflater.from(mContext);
//    }
    public HomeFragmentAdapter(Context mContext, Product.ResultBean dataBean) {
        this.mContext = mContext;
        this.dataBean = dataBean;
        mLayoutInflater =LayoutInflater.from(mContext);
    }

    /*
      相当于getView
      创建ViewHolder
      parent 父类
      viewType 当前类型
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ACT){
            return new HomeFragmentAdapter.ActViewHolder(mContext,mLayoutInflater.inflate(R.layout.act_item,null));
        }
        else if (viewType == RECOMMEND) {
            return new HomeFragmentAdapter.RecommendViewHolder( mContext,mLayoutInflater.inflate(R.layout.recommend_item, null));
        }
            return null;
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private GridView gv_recommend;
        private Context mContext;
        private RecommendGridViewAdapter adapter;
        public RecommendViewHolder( Context mContext,View itemView) {
            super(itemView);
            gv_recommend = itemView.findViewById(R.id.gv_recommend);
            this.mContext = mContext;
        }

//        public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
//            adapter = new RecommendGridViewAdapter(mContext, recommend_info);
//            gv_recommend.setAdapter(adapter);
//            //点击事件
//            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
////                    String cover_price = recommend_info.get(position).getCover_price();
////                    String name = recommend_info.get(position).getName();
////                    String figure = recommend_info.get(position).getFigure();
////                    String product_id = recommend_info.get(position).getProduct_id();
////                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
//
////                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
////                    intent.putExtra(GOODS_BEAN, goodsBean);
////                    mContext.startActivity(intent);
//                }
//            });
//        }
//    }
        public void setData(final List<Product.ResultBean.TopBean> recommend_info) {
//            Log.e(TAG,"setData1"+recommend_info);
            adapter = new RecommendGridViewAdapter(mContext, recommend_info);
            gv_recommend.setAdapter(adapter);
            //点击事件
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                    transmitProduct.setProdcutId(recommend_info.get(position).getProduct_idX());
                    transmitProduct.setProductImg(recommend_info.get(position).getProduct_imgX());
                    transmitProduct.setProductNumber(recommend_info.get(position).getProduct_numberX());
                    transmitProduct.setProductPrice(recommend_info.get(position).getProduct_price());
                    transmitProduct.setProductSales(1);
                    transmitProduct.setProductName(recommend_info.get(position).getProduct_nameX());
                    transmitProduct.setProductDescription(recommend_info.get(position).getProduct_descriptionX());
                    transmitData.transmitProduct = transmitProduct;
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra("position", position);
                    mContext.startActivity(intent);
//                    String cover_price = recommend_info.get(position).getCover_price();
//                    String name = recommend_info.get(position).getName();
//                    String figure = recommend_info.get(position).getFigure();
//                    String product_id = recommend_info.get(position).getProduct_id();
//                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);

//                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
//                    intent.putExtra(GOODS_BEAN, goodsBean);
//                    mContext.startActivity(intent);
                }
            });
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder{
        public ViewPager actViewPager;
        private Context mContext;

        public ActViewHolder(Context mContext, View itemView){
            super(itemView);
            actViewPager = itemView.findViewById(R.id.act_viewpager);
            this.mContext = mContext;
        }
        public void setData(final List<Product.ResultBean.DataBean> act_info) {
//            Log.e(TAG,"setData"+act_info.size());
            actViewPager.setPageMargin(20);
            actViewPager.setOffscreenPageLimit(3);
            actViewPager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));
            //有数据了，设置适配器
            actViewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
//                    Log.e(TAG,"act_info"+act_info.get(0).getProduct_imgX());
                    return act_info.size();
                }

                /*
                view 页面
                object instantiateItem方法返回的值
                 */
                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                /*
                container ViewPager
                position 对应页面的位置
                 */
                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    ImageView view = new ImageView(mContext);
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    //添加到容器中
                    container.addView(view);
                    //绑定数据
                    Glide.with(mContext)
                            .load(Constants.BASE_URl_IMAGE + act_info.get(position).getProduct_imgX())
                            .into(view);
                    //点击事件
                    actViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });
        }
    }

    //相当于getView中的绑定数据模块
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
//            actViewHolder.setData(resultBean.getAct_info());
            actViewHolder.setData(dataBean.getData());
        }
        else if (getItemViewType(position) == RECOMMEND) {
//            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
//            recommendViewHolder.setData(resultBean.getRecommend_info());
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(dataBean.getTop());
        }
    }

    /*
      得到类型
    */
    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case ACT:
                currentType = ACT;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
//            case HOT:
//                currentType = HOT;
//                break;
        }
        return currentType;
    }

    //总共有多少个Item
    @Override
    public int getItemCount() {
        return 2;
    }
}
