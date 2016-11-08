package com.hither.androidframe.activity;

import android.content.Context;
import android.os.Bundle;

import com.hither.androidframe.R;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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

    @Override
    public Context getContext() {
        return this;
    }
}
