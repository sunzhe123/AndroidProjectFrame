package com.hither.androidframe.project.splash.bean;

/**
 * Created by Administrator on 2016/11/16.
 */
public class HeaderInfoBean {
    //放在header里面 app每激活就更新一次

    //网络类型
    private String net_type;
    //经度
    private String longitude;
    //纬度
    private String latitude;
    //详细地址
    private String address;


    public void setAddress(String address) {
        this.address = address;
    }

    public void setNet_type(String net_type) {
        this.net_type = net_type;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "net_type='" + net_type + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'';
    }
}
