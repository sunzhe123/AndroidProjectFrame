package com.hither.androidframe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hither.androidframe.R;
import com.hither.androidframe.tools.LogUtils;
import com.hither.androidframe.utils.Constants;

public class WelcomeActivity extends BaseActivity {
    @Override
    public int setContentViewResId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        LogUtils.i("WelcomeActivity", "==>WelcomeActivity context:" + context);
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void startActivityBtn2(View view) {
        Intent intent_main = new Intent(context, MainActivity.class);
        startActivity(intent_main);
    }
}
