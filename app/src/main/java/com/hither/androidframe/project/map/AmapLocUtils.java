package com.hither.androidframe.project.map;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by Administrator on 2016/11/14.
 */
public class AmapLocUtils implements AMapLocationListener {
    private AMapLocationClient locationClient = null;//定位
    private AMapLocationClientOption locationOption = null;//定位设置
    private LonLatListener lonLatListener;
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        lonLatListener.getLonLat(aMapLocation);
        locationClient.stopLocation();
        locationClient.onDestroy();
        locationClient = null;
        locationOption = null;
    }

    /**
     * 被调用去定位
     *
     * @param context
     * @param lonLatListener
     */
    public void getLoncation(Context context, LonLatListener lonLatListener) {
        locationClient = new AMapLocationClient(context);
        locationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationClient.setLocationListener(this);//设置定位监听
        locationOption.setOnceLocation(false);//单次定位
        locationOption.setNeedAddress(true);//逆地理编码
        this.lonLatListener = lonLatListener;//接口
        locationClient.setLocationOption(locationOption);//设置定位参数
        locationClient.startLocation();//启动定位
    }

    public interface LonLatListener {
        void getLonLat(AMapLocation aMapLocation);
    }
}
