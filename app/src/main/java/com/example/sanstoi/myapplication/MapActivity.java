package com.example.sanstoi.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.sanstoi.bean.ChildScenic;
import com.example.sanstoi.bean.ParentScenic;
import com.example.sanstoi.connet.ConnectToWebService;
import com.example.sanstoi.util.Base64Util;
import com.example.sanstoi.util.PicassoUtil;
import com.example.sanstoi.util.Speak;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MapActivity extends AppCompatActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Context context;
    private ConnectToWebService connectToService;
    private Speak speak;
    private Base64Util base64Util;
    private PicassoUtil picassoUtil = new PicassoUtil();

    //location
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private boolean isFirstIn = true;
    private double mLatitude;
    private double mLongtitude;
    private BitmapDescriptor mIconLocation;
    private MyOrientationListener myOrientationListener;
    private float mCurrentX;
    private MyLocationConfiguration.LocationMode mLocationModel;

    //覆盖物图标
    private BitmapDescriptor mMarker;
    private RelativeLayout mMarkerLy;
    private Switch sw ;
    private Polyline mPolyline;

    //子景点列表
    List<ChildScenic> list = new ArrayList<ChildScenic>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        init();
    }

    public void init() {
        base64Util = new Base64Util();
        getData();
        context = this;
        speak = new Speak(getApplication());
        mMapView = (MapView) findViewById(R.id.id_bmapView);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        //初始化定位
        initLocation();

        initMarker();

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle info = marker.getExtraInfo();
                final ChildScenic childScenic = (ChildScenic) info.get("childScenic");
                ImageView iv = (ImageView) mMarkerLy.findViewById(R.id.id_info_img);
                picassoUtil.loadImageView(context, childScenic.getImage(), iv);
                sw = (Switch) mMarkerLy.findViewById(R.id.sw_speak_detail);
                Button btn_guide = (Button) findViewById(R.id.btn_guide);
                final String str = childScenic.getChildScenicBrief();
                sw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sw.setSelected(!sw.isSelected());
                        if(sw.isSelected()){
                            speak.startSpeak(str);
                        } else{
                            speak.endSpeak();
                        }
                    }
                });

                btn_guide.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        DrawLines(childScenic.getmLatitude(), childScenic.getmLongtitude());
                    }
                });
                InfoWindow infoWindow;
                TextView tv = new TextView(context);
                tv.setBackgroundResource(R.drawable.button_down);
                tv.setPadding(30,20,30,50);
                tv.setText(childScenic.getChildScenicName());
                tv.setTextColor(Color.parseColor("#000000"));
                final LatLng latLng = marker.getPosition();
                Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
                p.y -=47;
                LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);

                infoWindow = new InfoWindow(tv, ll, 1);
                mBaiduMap.showInfoWindow(infoWindow);
                mMarkerLy.setVisibility(View.VISIBLE);
                return true;
            }
        });
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMarkerLy.setVisibility(View.GONE);
                mBaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

    }

    public void  DrawLines(double lon, double lat ){
        if(mPolyline != null ){
            mPolyline.remove();
        }
        double startlats=mLatitude;
        double startlongs=mLongtitude;
        LatLng p1 = new LatLng(startlats, startlongs);
        LatLng p2 = new LatLng(lat, lon);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(p1);
        points.add(p2);
        OverlayOptions ooPolyline = new PolylineOptions().width(10)
                .points(points);
        mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);

    /*
     * 调用Distance方法获取两点间x,y轴之间的距离
     */
        double cc= Distance(startlats, startlongs, lat, lon);

        int length=(int)cc;

        Toast.makeText(this, "您与终端距离"+length+"米", Toast.LENGTH_SHORT).show();


    }

    public Double Distance(double lat1, double lng1,double lat2, double lng2) {


        Double R=6370996.81;  //地球的半径

    /*
     * 获取两点间x,y轴之间的距离
     */
        Double x = (lng2 - lng1)*Math.PI*R*Math.cos(((lat1+lat2)/2)*Math.PI/180)/180;
        Double y = (lat2 - lat1)*Math.PI*R/180;


        Double distance = Math.hypot(x, y);   //得到两点之间的直线距离

        return  distance;

    }

    private void initMarker() {
        mMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark);
        mMarkerLy = (RelativeLayout)findViewById(R.id.id_marker_layout);
    }

    public void getData(){
        try {
            Bundle bundle = (Bundle) getIntent().getExtras().get("bundle");
            ParentScenic parentScenic = (ParentScenic) bundle.getParcelable("parentScenic");
            connectToService = new ConnectToWebService();
            JSONArray obj = connectToService.queryChildScenicByParentScenicId(parentScenic.getParentScenicID());
            for(int i = 0; i < obj.length(); i++){
                    JSONObject jsonObject = (JSONObject) obj.get(i);
                    ChildScenic childScenic = new ChildScenic();
                    childScenic.setChildScenicID(jsonObject.getInt("childScenicID"));
                    childScenic.setParentScenicID(jsonObject.getInt("parentScenicID"));
                    childScenic.setChildScenicName(jsonObject.getString("childScenicName"));
                    childScenic.setChildScenicOpenDate(jsonObject.getString("childScenicOpenDate"));
                    childScenic.setChildScenicStartTime(jsonObject.getString("childScenicStartTime"));
                    childScenic.setChildScenicEndTime(jsonObject.getString("childScenicEndTime"));
                    childScenic.setChildScenicPrice(jsonObject.getDouble("childScenicPrice"));
                    childScenic.setChildScenicBrief(jsonObject.getString("childScenicBrief"));
                    childScenic.setmLatitude(jsonObject.getDouble("mLatitude"));
                    childScenic.setmLongtitude(jsonObject.getDouble("mLongtitude"));
                    childScenic.setImage(jsonObject.getString("image"));
                    list.add(childScenic);
                }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.analyze_information_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void initLocation() {
        mLocationModel = MyLocationConfiguration.LocationMode.NORMAL;
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        //初始化图标
        mIconLocation = BitmapDescriptorFactory.fromResource(R.mipmap.arrow);
        myOrientationListener = new MyOrientationListener(this);
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void OnOrientationChanged(float x) {
                mCurrentX = x;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始定位
        mBaiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted()) {
            mLocationClient.start();
            //开启传感器
            myOrientationListener.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        //停止方向传感器
        myOrientationListener.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_map_common:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.id_map_site:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.id_map_traffic:
                if (mBaiduMap.isTrafficEnabled()) {
                    mBaiduMap.setTrafficEnabled(false);
                    item.setTitle("实时交通(off)");
                } else {
                    mBaiduMap.setTrafficEnabled(true);
                    item.setTitle("实时交通(on)");
                }
                break;
            case R.id.id_map_location:
                centerToMyLocation(mLatitude, mLongtitude);
                break;
            case R.id.id_map_model_common:
                mLocationModel = MyLocationConfiguration.LocationMode.NORMAL;
                break;
            case R.id.id_map_model_follow:
                mLocationModel = MyLocationConfiguration.LocationMode.FOLLOWING;
                break;
            case R.id.id_map_model_compass:
                mLocationModel = MyLocationConfiguration.LocationMode.COMPASS;
                break;
            case R.id.id_add_overlay:
                addOverLays();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addOverLays() {
        mBaiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        OverlayOptions options;
        for(ChildScenic childScenic : list){
            //经纬度
            latLng = new LatLng(childScenic.getmLongtitude(), childScenic.getmLatitude());
            options = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
            marker = (Marker) mBaiduMap.addOverlay(options);
            Bundle bundle = new Bundle();
            bundle.putParcelable("childScenic", childScenic);
            marker.setExtraInfo(bundle);
        }

        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);
    }

    private void centerToMyLocation(double mLatitude, double mLongtitude) {
        LatLng latLng = new LatLng(mLatitude, mLongtitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(msu);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData data = new MyLocationData.Builder()//
                    .direction(mCurrentX)//
                    .accuracy(bdLocation.getRadius())//
                    .latitude(bdLocation.getLatitude())//
                    .longitude(bdLocation.getLongitude())//
                    .build();
            mBaiduMap.setMyLocationData(data);
            mLatitude = bdLocation.getLatitude();
            mLongtitude = bdLocation.getLongitude();
            //设置自定义图标
            MyLocationConfiguration config = new MyLocationConfiguration(mLocationModel,true,mIconLocation);
            mBaiduMap.setMyLocationConfigeration(config);
            if (isFirstIn) {
                LatLng latLng = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                Toast.makeText(context,bdLocation.getAddrStr(),Toast.LENGTH_SHORT).show();
                isFirstIn = false;
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
}
