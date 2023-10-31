package com.example.upfarm.cart.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upfarm.R;
import com.example.upfarm.data.Cart;
import com.example.upfarm.data.Product;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.data.transmit.TransmitProduct;
import com.example.upfarm.home.fragment.adapter.HomeFragmentAdapter;
import com.example.upfarm.home.fragment.bean.ResultBeanData;
import com.example.upfarm.network.request.MyRequest;
import com.example.upfarm.user.fragment.RegisterCheckActivity;
import com.example.upfarm.user.fragment.manager.MFarmActivityAdapter;
import com.example.upfarm.user.fragment.manager.MFarmGridViewAdapter;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

public class CartFragmentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private Cart.ResultBean resultBean;
    private LayoutInflater mLayoutInflater;//用来初始化布局
    //当前类型
    private int currentType=0;

    /**
     * 列表
     */
    public static final int  LIST=0;

    public CartFragmentAdapter(Context mContext, Cart.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater =LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LIST) {
            return new CartFragmentAdapter.ListViewHolder(mLayoutInflater.inflate(R.layout.list_item,null), mContext);
        }
        return null;
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private GridView gv_list;
        private Context mContext;
        private CartGridViewAdapter adapter;

        public ListViewHolder(View itemView, Context mContext) {
            super(itemView);
            gv_list = itemView.findViewById(R.id.gv_list);
            this.mContext = mContext;

        }

        public void setData(final List<Cart.ResultBean.CartBean> data) {
            adapter = new CartGridViewAdapter(mContext, data);
            gv_list.setAdapter(adapter);
            //点击事件
            gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent(mContext, RegisterCheckActivity.class);
//                    intent.putExtra("position", position);
//                    mContext.startActivity(intent);

                }
            });
            //初始化我的transmitproduct

        }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == LIST) {
            CartFragmentAdapter.ListViewHolder hotViewHolder = (CartFragmentAdapter.ListViewHolder) holder;
            hotViewHolder.setData(resultBean.getCart());
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
