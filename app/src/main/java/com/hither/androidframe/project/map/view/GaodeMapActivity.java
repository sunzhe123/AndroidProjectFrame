package com.hither.androidframe.project.map.view;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.hither.androidframe.R;
import com.hither.androidframe.project.map.AmapLocUtils;
import com.hither.androidframe.project.map.presenter.GaodePresenter;
import com.hither.androidframe.project.map.utils.CheckMap;
import com.hither.androidframe.project.map.utils.MapConstants;
import com.hither.androidframe.tools.LogUtils;
import com.hither.androidframe.tools.StatusBarUtil2;
import com.hither.androidframe.widget.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GaodeMapActivity extends AppCompatActivity implements IGaodeMapView, LocationSource, GeocodeSearch.OnGeocodeSearchListener {
    private TitleBar map_title;
    private MapView mMapView;
    private AMap aMap;
    private double latitude;//定位的纬度
    private double longitude;//定位的经度
    private double target_latitude;//目标的纬度
    private double target_longitude;//目标的经度
    private String target_name;//目标名
    private AmapLocUtils amapLocUtils;
    private Marker myMarker;
    private Marker targetMarker;
    private GeocodeSearch geocoderSearch;
    private String addressName;
    private FloatingActionButton mapMenu;
    private PopupWindow popupWindow;
    private GaodePresenter gaodePresenter;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        target_latitude = extras.getDouble("latitude", 0.0);
        target_longitude = extras.getDouble("longitude", 0.0);
        target_name = extras.getString("targetName", "到这里");
        latLng = getToLatLng(target_latitude, target_longitude);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        getAddress(latLng);
        setContentView(R.layout.activity_gaode_map);
        gaodePresenter = new GaodePresenter(this, savedInstanceState, this);
        gaodePresenter.setStatusBar();
        gaodePresenter.setTitleBar();
        gaodePresenter.setMapView();
        gaodePresenter.setFloatingActionButton();
    }

    @Override
    public void initStatusBar() {
        StatusBarUtil2.setColor(this, getResources().getColor(R.color.palevioletred2), 0);
    }

    @Override
    public void initTitleBar() {
        map_title = $(R.id.map_title);
        map_title.setBackgroundColor(getResources().getColor(R.color.palevioletred2));
        map_title.setBackKeyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        map_title.setRightIconVisibility(View.GONE);
        map_title.setTitleText("地图");
    }

    @Override
    public void initMapView(Bundle savedInstanceState) {
        //获取地图控件引用
        mMapView = $(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        //初始化地图变量
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        UiSettings mUiSettings = aMap.getUiSettings();
        mUiSettings.setScaleControlsEnabled(false); // 设置地图默认的比例尺是否显示
        mUiSettings.setCompassEnabled(true); // 设置地图默认的指南针是否显示
        mUiSettings.setRotateGesturesEnabled(true);// 设置地图是否可以旋转
        mUiSettings.setZoomControlsEnabled(false);
        aMap.setLocationSource(this);// 设置定位监听
        mUiSettings.setMyLocationButtonEnabled(true); // 显示默认的定位按钮
        aMap.setMyLocationEnabled(true);// 可触发定位并显示定位层
        gaodePresenter.setMyLoncation();
    }

    @Override
    public void initMyLoncation() {
        //        AmapLocUtils ampLocUtils =  new AmapLocUtils();
//        aMap.setLocationSource(ampLocUtils);// 设置定位监听
//        UiSettings uiSettings = aMap.getUiSettings();
//        uiSettings.setMyLocationButtonEnabled(true); // 显示默认的定位按钮
//        aMap.setMyLocationEnabled(true);// 可触发定位并显示定位层
        amapLocUtils = new AmapLocUtils();
        amapLocUtils.getLoncation(this, new AmapLocUtils.LonLatListener() {
            @Override
            public void getLonLat(AMapLocation aMapLocation) {
                //得到经纬度以及地址信息
                longitude = aMapLocation.getLongitude();//获取经度
                latitude = aMapLocation.getLatitude();//获取纬度
                String address = aMapLocation.getAddress();
                LatLng latLng = getToLatLng(latitude, longitude);
                //添加标记点
                myMarker = addMarker(latLng, "【我的位置】", address, R.drawable.map_mymarker);

                //aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));//设置中心点,默认缩放级别
                // aMap.moveCamera(CameraUpdateFactory.zoomTo(12));//设置默认缩放级别
            }
        });
    }

    @Override
    public void initTargetLocation() {
        targetMarker = addMarker(latLng, "目标位置", addressName, R.drawable.map_marka);
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12), 1000, null);
    }

    @Override
    public void initFloatingActionButton() {
        mapMenu = $(R.id.mapMenu);
        mapMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gaodePresenter.setPopupWindow();
            }
        });
    }

    @Override
    public void initPopupWindow(List<Map<String, Object>> list) {
        View inflaterView = LayoutInflater.from(this).inflate(R.layout.select_map_layout, null);
        popupWindow = new PopupWindow(inflaterView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        ListView map_list = (ListView) inflaterView.findViewById(R.id.map_list);
        TextView map_cancel = (TextView) inflaterView.findViewById(R.id.map_cancel);
        TextView map_empty = (TextView) inflaterView.findViewById(R.id.map_empty);
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.map_select_item, new String[]{"mapName", "mapImage"},
                new int[]{R.id.text_map_item, R.id.image_map_item,});
        map_list.setAdapter(adapter);
        map_list.setEmptyView(map_empty);
        map_cancel.setOnClickListener(new PopuWindowOnClickListener());
        map_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                gaodePresenter.setStartIntent(i, "projectFrame", target_name, target_latitude, target_longitude, "1", "2");
            }
        });
        mapMenu.setVisibility(View.GONE);
        // 在底部显示
        popupWindow.showAtLocation(inflaterView, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                gaodePresenter.setFloatingActionButtonVisible();
            }
        });
    }

    @Override
    public void initFloatingActionButtonVisible() {
        mapMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void initStartMapIntent() {
        popupWindow.dismiss();
    }

    /**
     * 往地图上添加marker
     *
     * @param latLng
     */
    private Marker addMarker(LatLng latLng, String location, String desc, int drawable) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(location);
        markerOptions.snippet(desc);
        markerOptions.draggable(true);
        markerOptions.visible(true);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), drawable)));
        Marker marker = aMap.addMarker(markerOptions);
        return marker;
    }

    public int setContentViewResId() {
        return R.layout.activity_gaode_map;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }

    /**
     * 把LatLng对象转化为LatLonPoint对象
     */
    public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude, latlon.longitude);
    }

    /**
     * 根据经纬度得到LatLng对象
     *
     * @param latitude:纬度
     * @param longitude：经度
     * @return:LatLng对象
     */
    public LatLng getToLatLng(double latitude, double longitude) {
        return new LatLng(latitude, longitude);
    }


    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        amapLocUtils.getLoncation(this, new AmapLocUtils.LonLatListener() {
            @Override
            public void getLonLat(AMapLocation aMapLocation) {
                if (myMarker != null) {
                    myMarker.remove();
                }
                //得到经纬度以及地址信息
                longitude = aMapLocation.getLongitude();//获取经度
                latitude = aMapLocation.getLatitude();//获取纬度
                String address = aMapLocation.getAddress();
                LatLng latLng = getToLatLng(latitude, longitude);
                //添加标记点
                myMarker = addMarker(latLng, "【我的位置】", address, R.drawable.map_mymarker);
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12), 1000, null);
            }
        });
    }

    @Override
    public void deactivate() {

    }

    /**
     * 根据经纬度得到地址
     */
    public void getAddress(final LatLng latLonPoint) {
        LogUtils.i("===>latLonPoint:" + latLonPoint);
        LatLonPoint latLonPoint1 = convertToLatLonPoint(latLonPoint);
        LogUtils.i("===>latLonPoint1:" + latLonPoint1);
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint1, 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        LogUtils.i("===>rCode:" + rCode);
        LogUtils.i("===>result:" + result);
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                addressName = result.getRegeocodeAddress().getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi
                gaodePresenter.setTargetLocation();
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    public <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    private class PopuWindowOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.map_cancel:
                    popupWindow.dismiss();
                    break;
            }
        }
    }
}
