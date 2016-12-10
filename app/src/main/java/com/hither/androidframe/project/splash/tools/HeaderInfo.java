package com.hither.androidframe.project.splash.tools;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.hither.androidframe.project.splash.bean.HeaderInfoBean;
import com.hither.androidframe.tools.NetWorkUtils;


/**
 * Created by Administrator on 2016/11/16.
 */
public class HeaderInfo {
    //   放在header里面 app每激活就更新一次
    //   net_type 网络类型|wifi:wifi,2G:2G,3G:3G,4G:4G
    //   longitude 经度
    //   latitude 纬度
    //   appname 软件名
    public static TelephonyManager telephonyManager;

    public static HeaderInfoBean setHeaderInfo(Activity activity) {
        final HeaderInfoBean headerInfoBean = new HeaderInfoBean();
        headerInfoBean.setNet_type(getNetType(activity));
        telephonyManager = getTelephonyManager(activity);
        return headerInfoBean;
    }

    /**
     * 获取网络信号类型
     */
    public static String getNetType(Activity activity) {
        String netType = "没有连接";
        int networkState = NetWorkUtils.getNetworkState(activity);
        switch (networkState) {
            case NetWorkUtils.NETWORN_NONE:
                netType = "没有连接";
                break;
            case NetWorkUtils.NETWORN_WIFI:
                netType = "WIFI";
                break;
            case NetWorkUtils.NETWORN_2G:
                netType = "2G";
                break;
            case NetWorkUtils.NETWORN_3G:
                netType = "3G";
                break;
            case NetWorkUtils.NETWORN_4G:
                netType = "4G";
                break;
            case NetWorkUtils.NETWORN_MOBILE:
                netType = "MOBILE";
                break;
        }
        return netType;
    }

    /**
     * 获取应用程序名
     */
    public static String getAppName(Activity activity) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = activity.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
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
