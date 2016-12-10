package com.hither.androidframe.project;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hither.androidframe.R;
import com.hither.androidframe.tools.StatusBarUtil;
import com.hither.androidframe.tools.StatusBarUtil2;

import cn.jpush.android.api.JPushInterface;

public abstract class BaseActivity extends AppCompatActivity {
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initStatusBar();
        setContentView(setContentViewResId());
        initConfigs();
        initView();
    }

    public void initStatusBar() {
//        StatusBarUtil.setColor(this, R.color.alpha_gray);
        StatusBarUtil2.setColor(this, getResources().getColor(R.color.palevioletred2), 0);
    }

    public abstract int setContentViewResId();

    /**
     * 初始化Context上下文,一般在子类中调用getContext()方法
     */
    public void initConfigs() {
        context = getContext();
    }

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 获取上下文的方法
     *
     * @return
     */
    public abstract Context getContext();

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }
}
