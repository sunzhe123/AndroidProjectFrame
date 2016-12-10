package com.hither.androidframe.project.splash.presenter;

import com.hither.androidframe.project.splash.view.ISplashView;


/**
 * Created by Administrator on 2016/11/28.
 */
public class SplashPresenter {
    private ISplashView iSplashView;
    public SplashPresenter(ISplashView iSplashView) {
        this.iSplashView = iSplashView;
    }
    public void setCountdownView() {
        iSplashView.setCountdownView();
        iSplashView.startActivity();
    }
}
