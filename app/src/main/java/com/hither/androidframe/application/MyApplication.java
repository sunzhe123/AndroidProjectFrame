package com.hither.androidframe.application;

import android.app.Application;

/**
 * Created by Administrator on 2016/11/8.
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
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
}
