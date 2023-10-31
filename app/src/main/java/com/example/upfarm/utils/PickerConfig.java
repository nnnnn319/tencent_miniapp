package com.example.upfarm.utils;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.upfarm.domain.ImageItem;
import com.example.upfarm.user.fragment.ShangjiaRegisterActivity;

import java.util.List;

public class PickerConfig {

    private PickerConfig() {

    }

    private static PickerConfig sPickerConfig;

    public static PickerConfig getInstance() {
        if(sPickerConfig == null) {
            sPickerConfig = new PickerConfig();
        }
       return  sPickerConfig;
    }

    private int maxSelectedCount = 1;
    private OnImagesSelectedFinishedListener mImageSelectedFinishedListener =null;

    public int getMaxSelectedCount() {
        return maxSelectedCount;
    }

    public void setMaxSelectedCount(int maxSelectedCount) {
        this.maxSelectedCount = maxSelectedCount;
    }

    public void setOnImagesSelectedFinishedListener(OnImagesSelectedFinishedListener listener){
        this.mImageSelectedFinishedListener = listener;
    }

    public OnImagesSelectedFinishedListener getOnImagesSelectedFinishedListener(){
        return mImageSelectedFinishedListener;
    }



    public interface OnImagesSelectedFinishedListener{
        void onSelectedFinished(List<ImageItem> result);
    }
}
