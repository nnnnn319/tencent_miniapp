package com.example.upfarm.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.upfarm.R;
import com.example.upfarm.data.transmit.TransmitData;


public class MapActivity extends Activity {
    private MapView mMapView=null;
    private BaiduMap mBaiduMap = null;
    private GeoCoder mCoder = null;
    private Button button=null;
    private OverlayOptions centerOption =null;
    private double x;
    private double y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        button = findViewById(R.id.button);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap = mMapView.getMap();
        //地理编码
        mCoder = GeoCoder.newInstance();
        //地图中央点
        centerOption=addMarkerPoint(28.806651,115.21225);

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        initMap();
        initListener();

    }

    public void initListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reserveGeoCoder(new LatLng(x,y));
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        mCoder.destroy();
        mCoder=null;
        super.onDestroy();
    }

    //初始化地图
    public void initMap(){
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

//        setCenter(new LatLng(28.806651,115.21225));



        //地理编码接受结果
        mCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                Log.d("地理编码", "begin");
                if (null != geoCodeResult && null != geoCodeResult.getLocation()) {
                    Log.d("地理编码", "ok");
                    if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                        //没有检索到结果
                        Log.v("地理编码", "没有结果");
                        return;
                    } else {
                        double latitude = geoCodeResult.getLocation().latitude;
                        double longitude = geoCodeResult.getLocation().longitude;

                        setCenter(new LatLng(latitude,longitude));
                        addMarkerPoint(latitude,longitude);
                        Log.d("地理编码", String.valueOf(latitude));
                        Log.d("地理编码", String.valueOf(longitude));
                    }
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    Log.e("逆地理编码","失败");
                    return;
                } else {
                    //详细地址
                    String address = reverseGeoCodeResult.getAddress();
                    Log.e("逆地理编码",address);
                    //行政区号
                    int adCode = reverseGeoCodeResult. getCityCode();
                    Log.e("逆地理编码", String.valueOf(adCode));
                    //跳转
                    TransmitData transmitData = new TransmitData();
                    transmitData.map_address = address;
                    finish();

                }

            }
        });
        Log.d("test","begin");


        //地图加载完成调用
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Log.d("地图加载完成","begin");
                Intent intent = getIntent();
                geoCode(intent.getStringExtra("city"),intent.getStringExtra("address"));
            }
        });
        //地图状态改变监听接口
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                mBaiduMap.clear();
                x=mapStatus.target.latitude;
                y=mapStatus.target.longitude;
                addMarkerPoint(mapStatus.target.latitude,mapStatus.target.longitude);
            }
        });
    }


    //改变地图中心点
    public void setCenter(LatLng centerPoint){
        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                .target(centerPoint)
                .zoom(14)
                .build();  //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态
    }


    //经纬度转城市
    public void reserveGeoCoder(LatLng latLng){
        mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                .location(latLng)
                .newVersion(1)
                .radius(500));
    }
    //城市装经纬度
    public void geoCode(String city,String address){
        mCoder.geocode(new GeoCodeOption()
                .city(city)
                .address(address));
    }

    //获取地图中心经纬度
    public LatLng getCenter(){
        int[] location = new int[2];
        mMapView.getLocationOnScreen(location);
        Point p=new Point(location[0]+mMapView.getWidth()/2, location[1]+mMapView.getHeight()/2);
        //TODO 已经获取到屏幕中心经纬度，可上传或者地理转码
//        LatLng latLng=mBaiduMap.getProjection().fromScreenLocation(p);
        LatLng latLng=new LatLng(112.1,101.1);
        Log.i("location",latLng.toString());
        return latLng;
    }

    //添加一个Marker
    public OverlayOptions addMarkerPoint(double y,double x){
        Log.e("place","place");
        //定义Maker坐标点
        LatLng point = new LatLng(y, x);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point) //必传参数
                .icon(bitmap) //必传参数
                .draggable(true)
                .animateType(MarkerOptions.MarkerAnimateType.jump )
                //设置平贴地图，在地图中双指下拉查看效果
                .flat(true)
                .title("UpFarm")
                .alpha(0.5f);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        return option;
    }

    public void buttonClick(View view) {
        reserveGeoCoder(new LatLng(x,y));
    }
}
