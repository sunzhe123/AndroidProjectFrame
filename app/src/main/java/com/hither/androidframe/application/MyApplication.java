package com.hither.androidframe.application;

import android.app.Application;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2016/11/8.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        ShareSDK.initSDK(this);
    }

    /**
     * 单例模式获取application的对象
     *
     * @return:application对象
     */
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    public static Context getInstanceContext() {
        return context;
    }
}
