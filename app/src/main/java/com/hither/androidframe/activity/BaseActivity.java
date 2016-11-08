package com.hither.androidframe.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hither.androidframe.tools.StatusBarUtil;

public abstract class BaseActivity extends AppCompatActivity {
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //屏幕方向：肖像照(竖着的方向)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initStatusBar();
        initConfigs();
        initView();
    }

    public abstract void initStatusBar();

    /**
     * 初始化Context上下文,一般在子类中调用getContext()方法
     */
    public abstract void initConfigs();

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }
}
