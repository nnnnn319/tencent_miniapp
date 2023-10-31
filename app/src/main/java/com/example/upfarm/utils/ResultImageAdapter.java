package com.example.upfarm.utils;

import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.upfarm.R;
import com.example.upfarm.domain.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class ResultImageAdapter extends RecyclerView.Adapter<ResultImageAdapter.InnerHolder> {
    private List<ImageItem> mImageIetms = new ArrayList<>();
    private int mHorizontalCount =1;

    @NonNull
    @Override
    public ResultImageAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        itemView.findViewById(R.id.image_check_box).setVisibility(View.GONE);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultImageAdapter.InnerHolder holder, int position) {
        View itemView = holder.itemView;
        Point point = SizeUtils.getScreenSize(itemView.getContext());
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(point.x/mHorizontalCount,point.x/mHorizontalCount);
        itemView.setLayoutParams(layoutParams);
        ImageView imageView = itemView.findViewById(R.id.image_iv);
        ImageItem imageItem= mImageIetms.get(position);
        Glide.with(imageView.getContext()).load(imageItem.getPath()).into(imageView);

    }

    @Override
    public int getItemCount() {
        return mImageIetms.size();
    }

    public void setData(List<ImageItem> result,int horizontalCount) {
        this.mHorizontalCount = horizontalCount;
        mImageIetms.clear();
        mImageIetms.addAll(result);
        notifyDataSetChanged();
    }


    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}
