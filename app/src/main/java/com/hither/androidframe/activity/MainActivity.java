package com.hither.androidframe.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.hither.androidframe.R;
import com.hither.androidframe.tools.LogUtils;
import com.hither.androidframe.tools.StatusBarUtil;
import com.hither.androidframe.widget.CustomEditText;
import com.hither.androidframe.widget.CustomHintDialog;
import com.hither.androidframe.widget.TitleBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends BaseActivity {
    private FrameLayout fragment_layout;
    private SlidingMenu slidingMenu;

    @Override
    public int setContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        LogUtils.i("MainActivity", "==>MainActivity context:" + context);
        fragment_layout = $(R.id.fragment_layout);
        initSlidingMenu();
    }

    private void initSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setMenu(R.layout.menu_left);
        slidingMenu.setBehindWidth(getResources().getDisplayMetrics().widthPixels * 2 / 3);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void startOnclick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
