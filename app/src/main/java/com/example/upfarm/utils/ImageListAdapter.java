package com.example.upfarm.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.upfarm.R;
import com.example.upfarm.domain.ImageItem;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.InnerHolder> {

    private List<ImageItem> mImageItems = new ArrayList<>();

    public List<ImageItem> getmSelectItem() {
        return mSelectItem;
    }

    public void setmSelectItem(List<ImageItem> mSelectItem) {
        this.mSelectItem = mSelectItem;
    }

    private List<ImageItem> mSelectItem = new ArrayList<>();
    private OnItemSelectedChangeListener mItemSelectChangeListener = null;
    public static final int MAX_SELECTED_COUNT = 9;
    private int maxSelectedCount = MAX_SELECTED_COUNT;

    public int getMaxSelectedCount() {
        return maxSelectedCount;
    }

    public void setMaxSelectedCount(int maxSelectedCount) {
        this.maxSelectedCount = maxSelectedCount;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载itemView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        Point point =SizeUtils.getScreenSize(itemView.getContext());
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(point.x/3,point.x/3);
        itemView.setLayoutParams(layoutParams);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        //绑定数据
        View itemView = holder.itemView;
        ImageView imageView = itemView.findViewById(R.id.image_iv);
        final ImageItem imageItem =  mImageItems.get(position);
        final CheckBox checkBox = itemView.findViewById(R.id.image_check_box);
        final View cover = itemView.findViewById(R.id.image_cover);
        Glide.with(imageView.getContext()).load(imageItem.getPath()).into(imageView);
        //根据数据状态显示内容
        if(mSelectItem.contains(imageItem)) {
            //没有选择上，应该选上
            mSelectItem.add(imageItem);
            //修改UI
            checkBox.setChecked(false);
            cover.setVisibility(View.VISIBLE);
            checkBox.setButtonDrawable(imageView.getContext().getDrawable(R.drawable.select));

        }
        else {
            // 已经选择上，应该取消选择
            mSelectItem.remove(imageItem);
            checkBox.setChecked(true);
            checkBox.setButtonDrawable(imageView.getContext().getDrawable(R.drawable.no_select));
            cover.setVisibility(View.GONE);
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //是否选择上
                //如果选择上就变成取消，如果没选择上就选择上
                if(mSelectItem.contains(imageItem)) {
                    //已经选择上，应该取消选择
                    mSelectItem.remove(imageItem);
                    //修改UI
                    checkBox.setChecked(true);
                    checkBox.setButtonDrawable(imageView.getContext().getDrawable(R.drawable.no_select));
                    cover.setVisibility(View.GONE);
                }
                else {
                    if(mSelectItem.size()>=maxSelectedCount){
                        //给个提示
                        Toast toast = Toast.makeText(checkBox.getContext(), null,Toast.LENGTH_SHORT);
                        toast.setText("做多可以选择"+maxSelectedCount+"张图片");
                        toast.show();
                        return;
                    }
                    //没有选择上，应该选上
                    mSelectItem.add(imageItem);
                    checkBox.setChecked(false);
                    checkBox.setButtonDrawable(imageView.getContext().getDrawable(R.drawable.select));
                    cover.setVisibility(View.VISIBLE);
                }
                if(mItemSelectChangeListener != null) {
                    mItemSelectChangeListener.onItemSelectedChange(mSelectItem);
                }
            }
        });

    }
    public void  setOnItemSelectedChangeListener(OnItemSelectedChangeListener listener) {
        this.mItemSelectChangeListener = listener;
    }
    public interface OnItemSelectedChangeListener{
        void onItemSelectedChange(List<ImageItem> mSelectedItem);

    }

    @Override
    public int getItemCount() {
        return  mImageItems.size();
    }

    public void setData(List<ImageItem> imageItems) {
        mImageItems.clear();
        mImageItems.addAll(imageItems);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}