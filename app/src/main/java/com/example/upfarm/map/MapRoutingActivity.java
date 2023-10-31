package com.example.upfarm.map;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import com.baidu.mapapi.search.route.BikingRoutePlanOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.upfarm.R;
import com.example.upfarm.overlayutil.BikingRouteOverlay;
import com.example.upfarm.overlayutil.DrivingRouteOverlay;
import com.example.upfarm.overlayutil.WalkingRouteOverlay;

public class MapRoutingActivity extends Activity {
    private MapView mMapView=null;
    private BaiduMap mBaiduMap = null;

    private RoutePlanSearch mSearch =null;

    LatLng s;
    LatLng e;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routing);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap = mMapView.getMap();
        //步行路线规划
        mSearch = RoutePlanSearch.newInstance();

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        initMap();

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
        mSearch.destroy();
        mSearch=null;
        super.onDestroy();
    }

    //初始化地图
    public void initMap(){
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //路径规划结果返回处理步骤
        mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                mBaiduMap.clear();
                //创建WalkingRouteOverlay实例
                WalkingRouteOverlay overlay = new WalkingRouteOverlay(mBaiduMap);
                if (walkingRouteResult.getRouteLines().size() > 0) {
                    //获取路径规划数据,(以返回的第一条数据为例)
                    //为WalkingRouteOverlay实例设置路径数据
                    overlay.setData(walkingRouteResult.getRouteLines().get(0));
                    //在地图上绘制WalkingRouteOverlay
                    overlay.addToMap();
                }
            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                mBaiduMap.clear();
                //创建DrivingRouteOverlay实例
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                if (drivingRouteResult.getRouteLines().size() > 0) {
                    //获取路径规划数据,(以返回的第一条路线为例）
                    //为DrivingRouteOverlay实例设置数据
                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
                    //在地图上绘制DrivingRouteOverlay
                    overlay.addToMap();
                }
            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
                mBaiduMap.clear();
                //创建BikingRouteOverlay实例
                BikingRouteOverlay overlay = new BikingRouteOverlay(mBaiduMap);
                if (bikingRouteResult.getRouteLines().size() > 0) {
                    //获取路径规划数据,(以返回的第一条路线为例）
                    //为BikingRouteOverlay实例设置数据
                    overlay.setData(bikingRouteResult.getRouteLines().get(0));
                    //在地图上绘制BikingRouteOverlay
                    overlay.addToMap();
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

        });

        Log.d("test","begin");


        //地图加载完成调用
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Log.d("地图加载完成","begin");
                s=new LatLng(28.50,115.80562);
                e=new LatLng(28.915,116.1152);
                setCenter(s);
                walkRouting(s,e);
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

    //驾车路线规划
    public void carRouting(LatLng start,LatLng end){
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(PlanNode.withLocation(start))
                .to(PlanNode.withLocation(end)));
    }

    //骑行路线规划
    public void bikeRouting(LatLng start,LatLng end){
        mSearch.bikingSearch((new BikingRoutePlanOption())
                .from(PlanNode.withLocation(start))
                .to(PlanNode.withLocation(end))
                // ridingType  0 普通骑行，1 电动车骑行
                // 默认普通骑行
                .ridingType(0));
    }

    //步行路线规划
    public void walkRouting(LatLng start,LatLng end){
        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(PlanNode.withLocation(start))
                .to(PlanNode.withLocation(end)));
    }




    public void button1Click(View view) {
        walkRouting(s,e);
    }

    public void button2Click(View view) {
        bikeRouting(s,e);
    }

    public void button3Click(View view) {
        carRouting(s,e);
    }

    public void button4Click(View view) {
        //返回
    }


}

