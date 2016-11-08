package com.hither.androidframe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hither.androidframe.R;
import com.hither.androidframe.tools.SharedPrefUtils;
import com.hither.androidframe.utils.Constants;

public class SplashActivity extends BaseActivity {
    private boolean isFirstEnter = true;
    /**
     * 跳转目标activity
     * 0:第一次或者刚升级完进入，跳转到欢迎页
     * 1：不是第一次进入，跳转到主程序
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Intent intent_welcome = new Intent(context, WelcomeActivity.class);
                    startActivity(intent_welcome);
                    break;
                case 1:
                    Intent intent_main = new Intent(context, MainActivity.class);
                    startActivity(intent_main);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void initStatusBar() {

    }

    @Override
    public void initConfigs() {
        context = getContext();
    }

    @Override
    public void initView() {

    }

    /**
     * 跳转activity的方法
     */
    public void startActivity() {
        isFirstEnter = SharedPrefUtils.getBoolean("isFirstEnter", true);
        if (isFirstEnter) {
            handler.sendEmptyMessageDelayed(Constants.START_WELCOME_ACTIVITY, 3000);
        } else {
            handler.sendEmptyMessageDelayed(Constants.START_MAIN_ACTIVITY, 3000);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

}
