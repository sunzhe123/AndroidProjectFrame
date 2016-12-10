package com.hither.androidframe.utils;

import android.Manifest;
import android.content.Context;

import com.hither.androidframe.application.MyApplication;
import com.hither.androidframe.tools.AppConfig;

/**
 * 一些常量
 * Created by Administrator on 2016/11/8.
 */
public class Constants {
    public static final int START_MAIN_ACTIVITY = 0;
    public static final int START_WELCOME_ACTIVITY = 1;
    public static final int INIT_COUNTDOWNVIEW = 2;


    //android6.0 api>=23 需要动态添加的权限列表,使用时可以在下面定义数组
    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    //请求所有权限的请求码
    public static final int CODE_MULTI_PERMISSION = 100;
    //所有权限的数组
    public static final String[] requestPermissions = {
            PERMISSION_RECORD_AUDIO,
            PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_CALL_PHONE,
            PERMISSION_CAMERA,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE
    };
    //请求照相机权限的请求码
    public static final int CODE_CAMERA_PERMISSION = 101;
    //照相机权限的数组
    public static final String[] requestCamera = {
            PERMISSION_CAMERA
    };

    //高德地图请求权限的请求码
    public static final int CODE_MAP_PERMISSION = 102;
    //高德地图需要的权限
    public static final String[] requestMap = {
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_WRITE_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE,
            PERMISSION_READ_PHONE_STATE};

    public static Context context = MyApplication.getInstanceContext();
    public static String packageName = AppConfig.getAppPackage(context);
    public static String applicationName = AppConfig.getApplicationName(context) + ".apk";
}
