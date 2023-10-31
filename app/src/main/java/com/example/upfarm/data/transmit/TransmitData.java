package com.example.upfarm.data.transmit;

import com.example.upfarm.data.Product;
import com.example.upfarm.data.UserAddress;

import java.util.ArrayList;
import java.util.List;

public class TransmitData {
    public static int rentGroundpositon;
    public static TransmitProduct transmitProduct;
    public static int marketChnannelType = 0;
    public static  int farmOrderId;
    public static int farmId;
    public static TransmitFarm transmitFarm;
    public static int commentType=1;
    public static int addressPosition=-1;
    //个人全部地址信息
    public static List<UserAddress.ResultBean.AddressBean> addressBean;
    public static List<String> textMessage = new ArrayList<>();
    public static List<Integer> lengths = new ArrayList<>();
    public static  List<String> sendUsername = new ArrayList<>();
    //种子订单
    public static List<Product.ResultBean.HotBean> hotBeans = new ArrayList<>();
    //地图
    public static String map_address = "";
    //地址
    public static int type_address=-1;

    //NewAndUpdate Good
    public static Product.ResultBean.HotBean updateGood;

    public static Product.ResultBean.HotBean updateSeed;
    //判断订单
    public static int buy_or_product = 0;
    //修改的值
    public static String fix = "";


}

