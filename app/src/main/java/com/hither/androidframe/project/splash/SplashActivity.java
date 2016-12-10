package com.hither.androidframe.project.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.hither.androidframe.R;
import com.hither.androidframe.project.splash.bean.HeaderInfoBean;
import com.hither.androidframe.encode.Encrypt;
import com.hither.androidframe.project.BaseActivity;
import com.hither.androidframe.project.MainActivity;
import com.hither.androidframe.project.WelcomeActivity;
import com.hither.androidframe.project.map.AmapLocUtils;
import com.hither.androidframe.project.splash.presenter.SplashPresenter;
import com.hither.androidframe.project.splash.view.ISplashView;
import com.hither.androidframe.project.splash.tools.HeaderInfo;
import com.hither.androidframe.tools.SharedPrefUtils;
import com.hither.androidframe.tools.StatusBarUtil2;
import com.hither.androidframe.utils.Constants;
import com.hither.androidframe.widget.view.Action;
import com.hither.androidframe.widget.view.CountdownView;

/**
 * 流程分析:
 * 1.设置好StatusBar 以及布局
 * 2.获取后台需要的那些头信息,在回调方法中把这些信息存到SharedPreferences里
 * 3.存完之后在主线程中去设置CountdownView
 */
public class SplashActivity extends BaseActivity implements ISplashView {
    private boolean isFirstEnter = true;
    private SplashPresenter splashPresenter;
    private CountdownView mCountdownView;
    /**
     * 跳转目标activity
     * 0:第一次或者刚升级完进入，跳转到欢迎页
     * 1：不是第一次进入，跳转到主程序
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.START_MAIN_ACTIVITY:
                    Intent intent_main = new Intent(context, MainActivity.class);
                    startActivity(intent_main);
                    finish();
                    break;
                case Constants.START_WELCOME_ACTIVITY:
                    Intent intent_welcome = new Intent(context, WelcomeActivity.class);
                    startActivity(intent_welcome);
                    finish();
                    break;
                case Constants.INIT_COUNTDOWNVIEW:
                    mCountdownView = $(R.id.countdown);
                    splashPresenter = new SplashPresenter(SplashActivity.this);
                    splashPresenter.setCountdownView();
                    break;
            }
        }
    };

    @Override
    public void initStatusBar() {
        StatusBarUtil2.setTranslucent(this);
    }

    @Override
    public int setContentViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void initView() {
        initInfo();
    }

    public void initInfo() {
        new AmapLocUtils().getLoncation(this, new AmapLocUtils.LonLatListener() {
            @Override
            public void getLonLat(AMapLocation aMapLocation) {
                HeaderInfoBean headerInfoBean = HeaderInfo.setHeaderInfo(SplashActivity.this);
                headerInfoBean.setLongitude("" + aMapLocation.getLongitude());
                headerInfoBean.setLatitude("" + aMapLocation.getLatitude());
                headerInfoBean.setAddress(aMapLocation.getAddress());
                String headerStr = headerInfoBean.toString();
                try {
                    String encode = Encrypt.encode(headerStr);
                    SharedPrefUtils.putString("headerInfo", encode);
                    handler.sendEmptyMessage(Constants.INIT_COUNTDOWNVIEW);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setCountdownView() {
        mCountdownView.setVisibility(View.VISIBLE);
        mCountdownView.setText("倒计时");
        mCountdownView.setTime(3000);
        mCountdownView.star();
    }

    @Override
    public void startActivity() {
        mCountdownView.setOnFinishAction(new Action() {
            @Override
            public void onAction() {
                startSelectActivity();
            }
        });
        mCountdownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSelectActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * 跳转activity的方法
     */
    public void startSelectActivity() {
        isFirstEnter = SharedPrefUtils.getBoolean("isFirstEnter", true);
        if (isFirstEnter) {
            handler.sendEmptyMessage(Constants.START_WELCOME_ACTIVITY);
        } else {
            handler.sendEmptyMessage(Constants.START_MAIN_ACTIVITY);
        }
    }


}
