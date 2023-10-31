package com.example.upfarm.imagePicket;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.upfarm.R;
import com.example.upfarm.domain.ImageItem;
import com.example.upfarm.utils.ImageListAdapter;
import com.example.upfarm.utils.PickerConfig;

import java.util.ArrayList;
import java.util.List;

//图片
public class PickerActivity extends AppCompatActivity implements ImageListAdapter.OnItemSelectedChangeListener {
    private static final String TAG = "PickerActivity";
    private static final int LOADER_ID = 1;
    private List<ImageItem> mImageItems = new ArrayList<>();
    private ImageListAdapter mImageListAdapter;
    private TextView mFinishView;
    private PickerConfig mPickerConfig;
    private TextView top_bar_left_back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        initLoaderManager();
        initView();
        initEvent();
        initConfig();
        top_bar_left_back = this.findViewById(R.id.back);
        top_bar_left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void initConfig() {
        mPickerConfig = PickerConfig.getInstance();
        int maxSelectedCount = mPickerConfig.getMaxSelectedCount();
        mImageListAdapter.setMaxSelectedCount(maxSelectedCount);
    }
    private void initEvent() {
        mImageListAdapter.setOnItemSelectedChangeListener(this);
        mFinishView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取所选择的数据
                List<ImageItem> result = mImageListAdapter.getmSelectItem();
                //通知其他地方
                PickerConfig.OnImagesSelectedFinishedListener imagesSelectedFinishedListener = mPickerConfig.getOnImagesSelectedFinishedListener();
                if(imagesSelectedFinishedListener != null) {
                    imagesSelectedFinishedListener.onSelectedFinished(result);
                }
                //结束界面
                finish();
            }
        });
    }

    private void initLoaderManager() {
        mImageItems.clear();
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_ID,null,new LoaderManager.LoaderCallbacks<Cursor>() {

            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int id,@Nullable Bundle args) {
                if(id == LOADER_ID) {
                    return new CursorLoader(PickerActivity.this,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,new String[]{"_data","_display_name","date_added"},
                            null,null,"date_added DESC");
                }
                return null;
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader,Cursor cursor) {
                if(cursor != null) {
                    while(cursor.moveToNext()) {
                        String path = cursor.getString(0);
                        String title = cursor.getString(1);
                        long date = cursor.getLong(2);
                        ImageItem imageItem = new ImageItem(path,title,date);
                        mImageItems.add(imageItem);
                    }

                    cursor.close();
                    mImageListAdapter.setData(mImageItems);
                    }
                }
            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            }
        });
    }
    private void initView() {
        mFinishView = this.findViewById(R.id.finish_tv);
        RecyclerView listView = this.findViewById(R.id.image_list_view);
        listView.setLayoutManager(new GridLayoutManager(this,3));
        //设置适配器
        mImageListAdapter = new ImageListAdapter();
        listView.setAdapter(mImageListAdapter);
    }


    @Override
    public void onItemSelectedChange(List<ImageItem> selectedItem) {
        //所选择的数据发生变化
        mFinishView.setText("("+selectedItem.size()+"/"+mImageListAdapter.getMaxSelectedCount()+")完成");
    }
}
