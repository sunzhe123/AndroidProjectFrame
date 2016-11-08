package com.hither.androidframe.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.hither.androidframe.R;
import com.hither.androidframe.tools.StatusBarUtil;
import com.hither.androidframe.widget.CustomHintDialog;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends BaseActivity {
    private FrameLayout fragment_layout;
    private SlidingMenu slidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initStatusBar() {

        StatusBarUtil.setColor(this, R.color.alpha_gray);
    }

    @Override
    public void initConfigs() {
        context = getContext();
    }

    @Override
    public void initView() {
        fragment_layout = $(R.id.fragment_layout);
        initSlidingMenu();
        CustomHintDialog dialog = new CustomHintDialog(context, "请输入帐号");
        dialog.show();
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
}
