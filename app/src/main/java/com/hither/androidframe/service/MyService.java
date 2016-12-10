package com.hither.androidframe.service;

import android.app.IntentService;
import android.content.Intent;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.hither.androidframe.service.utils.ExternalStrorageUtils;
import com.hither.androidframe.utils.Constants;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

public class MyService extends IntentService {
    private int data;
    private String flags;


    public MyService(String name) {
        super(name);
    }

    public MyService() {
        super("");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String stringExtra = intent.getStringExtra("down_url");
        Log.i("SERVICE", "===>downloadAPK:" + stringExtra);
        final LocalBroadcastManager localBroadcast = LocalBroadcastManager.getInstance(this);
        final Intent localIntent = new Intent("BROADCAST_ACTION");

        //下载apk
        /*
         *1.先判断是否有网络，网络是否可用
         *2.开始下载
         *3.发送广播更新下载进度
         */
        String appPath = ExternalStrorageUtils.getAbsolutePath(Constants.context);
        String appName = Constants.applicationName;
        Log.i("SERVICE", "===>appPath:" + appPath);
        OkHttpUtils.get().url(stringExtra).build().execute(new FileCallBack(appPath, appName) {
            @Override
            public void inProgress(float progress) {
                data = (int) (progress * 100);
                localIntent.putExtra("progress", String.valueOf(data));
                localBroadcast.sendBroadcast(localIntent);
            }

            @Override
            public void onError(Request request, Exception e) {
                Log.i("ERROR", "===>Exception:" + e);
                flags = "下载失败";
                localIntent.putExtra("progress", flags);
                localBroadcast.sendBroadcast(localIntent);
            }

            @Override
            public void onResponse(File response) {
                flags = "完成";
                localIntent.putExtra("progress", flags);
                localBroadcast.sendBroadcast(localIntent);
            }
        });
    }
}
