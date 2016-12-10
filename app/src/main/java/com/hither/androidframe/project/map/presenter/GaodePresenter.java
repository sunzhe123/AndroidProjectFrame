package com.hither.androidframe.project.map.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.hither.androidframe.R;
import com.hither.androidframe.project.map.utils.CheckMap;
import com.hither.androidframe.project.map.utils.MapConstants;
import com.hither.androidframe.project.map.view.GaodeMapActivity;
import com.hither.androidframe.project.map.view.IGaodeMapView;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/30.
 */
public class GaodePresenter {
    private IGaodeMapView iGaodeMapView;
    private Bundle savedInstanceState;
    private Context context;
    private GaodeMapActivity activity;

    public GaodePresenter(IGaodeMapView iGaodeMapView, Bundle savedInstanceState, Context context) {
        this.iGaodeMapView = iGaodeMapView;
        this.savedInstanceState = savedInstanceState;
        this.context = context;
        if (context instanceof GaodeMapActivity) {
            activity = (GaodeMapActivity) context;
        }
    }

    public void setStatusBar() {
        iGaodeMapView.initStatusBar();
    }

    public void setTitleBar() {
        iGaodeMapView.initTitleBar();
    }

    public void setMapView() {
        iGaodeMapView.initMapView(savedInstanceState);
    }

    public void setFloatingActionButton() {
        iGaodeMapView.initFloatingActionButton();
    }

    public void setPopupWindow() {
        List<Map<String, Object>> list = new ArrayList<>();
        Resources resources = context.getResources();
        if (CheckMap.isPkgInstalled(activity, "com.autonavi.minimap")) {
            Map<String, Object> gaodeMap = new HashMap<>();
            gaodeMap.put("mapName", resources.getString(R.string.gaodeMap));
            gaodeMap.put("mapImage", MapConstants.mapImage[0]);
            list.add(gaodeMap);
        }
        if (CheckMap.isInstallByread("com.baidu.BaiduMap")) {
            Map<String, Object> baiduMap = new HashMap<>();
            baiduMap.put("mapName", resources.getString(R.string.baiduMap));
            baiduMap.put("mapImage", MapConstants.mapImage[1]);
            list.add(baiduMap);
        }
        if (CheckMap.isPkgInstalled(activity, "com.google.android.apps.maps")) {
            Map<String, Object> gugeMap = new HashMap<>();
            gugeMap.put("mapName", resources.getString(R.string.gugeMap));
            gugeMap.put("mapImage", MapConstants.mapImage[2]);
            list.add(gugeMap);
        }
        iGaodeMapView.initPopupWindow(list);
    }

    public void setFloatingActionButtonVisible() {
        iGaodeMapView.initFloatingActionButtonVisible();
    }

    public void setMyLoncation() {
        iGaodeMapView.initMyLoncation();
    }

    public void setTargetLocation() {
        iGaodeMapView.initTargetLocation();
    }

    /**
     * @param position          代表在列表中选中的地图下标
     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param poiname           非必填 POI 名称
     * @param lat               必填 纬度
     * @param lon               必填 经度
     * @param dev               必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style             必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；
     *                          6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public void setStartIntent(int position, String sourceApplication, String poiname, double lat, double lon, String dev, String style) {
        switch (position) {
            case 0:
                // 调起高德地图客户端
                StringBuffer stringBuffer_gaode = new StringBuffer("androidamap://navi?sourceApplication=")
                        .append(sourceApplication);
                if (!TextUtils.isEmpty(poiname)) {
                    stringBuffer_gaode.append("&poiname=").append(poiname);
                }
                stringBuffer_gaode.append("&lat=").append(lat)
                        .append("&lon=").append(lon)
                        .append("&dev=").append(dev)
                        .append("&style=").append(style);

                Intent intent_gaode = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer_gaode.toString()));
                intent_gaode.setPackage("com.autonavi.minimap");
                context.startActivity(intent_gaode);
                break;
            case 1:
                // 调起百度地图客户端
                String uri_1 = "baidumap://map/navi?";
                String uri_2 = "intent://map/geocoder?";
                StringBuffer stringBuffer_baidu = new StringBuffer(uri_2 + "location=").
                        append(String.valueOf(gdToBd_lat(lat, lon))).append(",").append(String.valueOf(gdToBd_lon(lat, lon)));
                stringBuffer_baidu.append("&src=huizhi|projectFrame#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                Intent intent_baidu = null;
                try {
                    intent_baidu = Intent.getIntent(stringBuffer_baidu.toString());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                context.startActivity(intent_baidu);
                break;
            case 2:
                // 调起谷歌地图客户端
                String url = "http://ditu.google.cn/maps?hl=zh&mrt=loc&q=" + lat + "," + lon + "(" + poiname + ")";
                Intent intent_guge = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                intent_guge.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                intent_guge.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                context.startActivity(intent_guge);
                break;
        }
        iGaodeMapView.initStartMapIntent();
    }

    /**
     * GCJ-02转为BD-09，即把高德地图的坐标转为百度地图的坐标
     */
    private final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    public double gdToBd_lat(double gd_lat, double gd_lon) {
        double x = gd_lat;
        double y = gd_lon;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        return z * Math.cos(theta) + 0.0065;
    }

    public double gdToBd_lon(double gd_lat, double gd_lon) {
        double x = gd_lat;
        double y = gd_lon;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        return z * Math.sin(theta) + 0.006;
    }
}
