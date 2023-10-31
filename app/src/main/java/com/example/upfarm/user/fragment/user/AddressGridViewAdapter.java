package com.example.upfarm.user.fragment.user;

import android.content.Context;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.upfarm.R;
import com.example.upfarm.data.UserAddress;
import com.example.upfarm.data.transmit.TransmitData;
import com.example.upfarm.home.fragment.bean.ResultBeanData;

import java.util.ArrayList;
import java.util.List;

import static com.hxb.hxbaddressselectionviewlibrary.handler.CrashHandler.TAG;

public class AddressGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<UserAddress.ResultBean.AddressBean> data;
    private int number=0;
    private int index=-1;//选中的Item的位置
    private List<CheckBox>  checkBoxes= new ArrayList<CheckBox>();
    private TransmitData transmitData = new TransmitData();

    //地址值

    public AddressGridViewAdapter(Context mContext, List<UserAddress.ResultBean.AddressBean> data) {
        this.mContext = mContext;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddressGridViewAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = android.view.View.inflate(mContext, R.layout.item_address_grid_view, null);
            holder = new AddressGridViewAdapter.ViewHolder();
            holder.tv_name= convertView.findViewById(R.id.tv_name);
            holder.tv_phone= convertView.findViewById(R.id.tv_phone);
            holder.tv_address= convertView.findViewById(R.id.tv_address);
            holder.checkbox= convertView.findViewById(R.id.checkbox);
            checkBoxes.add(holder.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (AddressGridViewAdapter.ViewHolder) convertView.getTag();
        }
        holder.checkbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(number == 0) {
                    holder.checkbox.setButtonDrawable(mContext.getResources().getDrawable(R.drawable.select));
                    number++;
                    index =position;
                }
                else {
                    Log.e(TAG,"position"+position+"  "+number);
                    if(index == -1) {
                        holder.checkbox.setButtonDrawable(mContext.getResources().getDrawable(R.drawable.select));
                        index =position;
                    }else {
                        if (index == position) {
                            holder.checkbox.setButtonDrawable(mContext.getResources().getDrawable(R.drawable.no_select));
                            index = -1;
                        } else {
                            holder.checkbox.setButtonDrawable(mContext.getResources().getDrawable(R.drawable.select));
                            checkBoxes.get(index).setButtonDrawable(mContext.getResources().getDrawable(R.drawable.no_select));
                            index =position;
                        }
                    }
                }
                TransmitData transmitData = new TransmitData();
                transmitData.addressPosition = index;
                /**注index为
                 *  选中的Item的位置*/

            }

        });
        UserAddress.ResultBean.AddressBean hotInfoBean = data.get(position);
        holder.tv_name.setText(hotInfoBean.getUser_address_name());
        holder.tv_address.setText(hotInfoBean.getUser_address_choose()+hotInfoBean.getUser_address_specific());
        holder.tv_phone.setText(hotInfoBean.getUser_address_phone());
        Log.e("type",String.valueOf(transmitData.type_address));
        if (transmitData.type_address==0) {
            holder.checkbox.setVisibility(View.GONE);
        }

//        TransmitData transmitData = new TransmitData();
//        transmitData.transmitProduct.setAddress(transmitData.addressBean.get(transmitData.addressPosition).getUser_address_choose()
//                        +transmitData.addressBean.get(transmitData.addressPosition).getUser_address_specific());
//                +transmitData.addressBean.get(transmitData.addressPosition).getUser_address_specific()ff);
//        holder.tv_address.setText(transmitData.addressBean.get(transmitData.addressPosition).getUser_address_choose()
//                +transmitData.addressBean.get(transmitData.addressPosition).getUser_address_specific());

        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_phone;
        TextView tv_address;
        CheckBox checkbox;
    }
}
