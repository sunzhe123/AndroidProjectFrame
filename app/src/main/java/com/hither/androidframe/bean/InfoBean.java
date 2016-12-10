package com.hither.androidframe.bean;

/**
 * Created by Administrator on 2016/11/16.
 */
public class InfoBean {
    //手机类型
    private String phone_type = "android";
    //IMEI
    private String IMEI;
    //屏幕高度
    private int screen_height;
    //屏幕宽度
    private int screen_width;
    //手机型号
    private String model;
    //厂商
    private String manufacture;
    //软件版本
    private String soft_version;
    //手机品牌
    private String brand;
    //运营商名称
    private String net_oper;
    //系统版本
    private String os_version;

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public void setScreen_height(int screen_height) {
        this.screen_height = screen_height;
    }

    public void setScreen_width(int screen_width) {
        this.screen_width = screen_width;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public void setSoft_version(String soft_version) {
        this.soft_version = soft_version;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setNet_oper(String net_oper) {
        this.net_oper = net_oper;
    }

    @Override
    public String toString() {
        return
                "phone_type='" + phone_type + '\'' +
                        ", IMEI='" + IMEI + '\'' +
                        ", screen_height=" + screen_height +
                        ", screen_width=" + screen_width +
                        ", model='" + model + '\'' +
                        ", manufacture='" + manufacture + '\'' +
                        ", soft_version='" + soft_version + '\'' +
                        ", brand='" + brand + '\'' +
                        ", net_oper='" + net_oper + '\'' +
                        ", os_version='" + os_version + '\'';
    }
}
