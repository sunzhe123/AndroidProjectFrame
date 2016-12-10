package com.hither.androidframe.tools;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.hither.androidframe.bean.InfoBean;

/**
 * 获取手机的一些信息
 * Created by Administrator on 2016/11/16.
 */
public class Info {
    //    IMEI
    //    screen 屏幕大小
    //    model手机型号
    //    manufacture厂商
    //    soft_version 软件版本
    //    brand 手机品牌
    //    net_oper 运营商名称
    public static TelephonyManager telephonyManager;

    public static InfoBean getInfo(Activity activity) {
        telephonyManager = getTelephonyManager(activity);
        InfoBean infoBean = new InfoBean();
        infoBean.setIMEI(getIMEI());
        infoBean.setScreen_width(getMetricsWidth(activity));
        infoBean.setScreen_height(getMetricsHeight(activity));
        infoBean.setModel(getModel());
        infoBean.setManufacture(getManufacture());
        infoBean.setSoft_version(getSoftVersion(activity));
        infoBean.setBrand(getBrand());
        infoBean.setNet_oper(getNet_oper());
        infoBean.setOs_version(getOS_Version());
        return infoBean;
    }

    /**
     * 设备唯一标识
     */
    public static String getIMEI() {
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    /**
     * 屏幕宽
     */
    public static int getMetricsWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;

    }

    /**
     * 屏幕高
     */
    public static int getMetricsHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    /**
     * 设备型号
     *
     * @return
     */
    public static String getModel() {
        String model = Build.MODEL;
        return model;
    }

    /**
     * 获取手机厂商
     */
    public static String getManufacture() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取软件版本
     */
    public static String getSoftVersion(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "" + pi.versionCode;

    }

    /**
     * 获取手机品牌
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机运营商
     */
    public static String getNet_oper() {
        String operator = telephonyManager.getSimOperator();
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002")) {
                return "中国移动";
            } else if (operator.equals("46001")) {
                return "中国联通";
            } else if (operator.equals("46003")) {
                return "中国电信";
            }
        }
        return "无SIM卡";
    }

    /**
     * 获取系统版本
     */
    public static String getOS_Version() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取网络信号类型
     */
    public static String getNetType() {
        int networkType = telephonyManager.getNetworkType();
        if (networkType == TelephonyManager.NETWORK_TYPE_UMTS || networkType == TelephonyManager.NETWORK_TYPE_HSDPA
                || networkType == TelephonyManager.NETWORK_TYPE_EVDO_0 || networkType == TelephonyManager.NETWORK_TYPE_EVDO_A) {
            return "3G";
        } else if (networkType == TelephonyManager.NETWORK_TYPE_GPRS || networkType == TelephonyManager.NETWORK_TYPE_EDGE || networkType == TelephonyManager.NETWORK_TYPE_CDMA) {
            return "2G";
        }
        return "";
    }

    /**
     * 得到TelephonyManager对象
     *
     * @param activity
     * @return
     */
    public static TelephonyManager getTelephonyManager(Activity activity) {
        return (TelephonyManager) activity.getSystemService(activity.TELEPHONY_SERVICE);
    }
}
