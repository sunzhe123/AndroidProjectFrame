package com.hither.androidframe.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hither.androidframe.R;
import com.hither.androidframe.tools.LogUtils;
import com.hither.androidframe.tools.PermissionUtils;
import com.hither.androidframe.tools.StatusBarUtil;
import com.hither.androidframe.tools.ToastUtils;
import com.hither.androidframe.utils.Constants;
import com.hither.androidframe.widget.CustomDialog;
import com.hither.androidframe.widget.CustomEditText;
import com.hither.androidframe.widget.CustomHintDialog;
import com.hither.androidframe.widget.TitleBar;
import com.hither.androidframe.zxing.CaptureActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends BaseActivity {
    private FrameLayout fragment_layout;
    private SlidingMenu slidingMenu;
    private View main_layout;


    @Override
    public int setContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        LogUtils.i("MainActivity", "==>MainActivity context:" + context);
        main_layout = $(R.id.main_layout);
        fragment_layout = $(R.id.fragment_layout);
        initSlidingMenu();
    }

    /**
     * 定义SlidingMenu抽屉
     */
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
            //登录
            case R.id.loginBtn:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            //扫码
            case R.id.loginBtn2:
                showCamera();
                break;
        }
    }

    /**
     * 检查是否拥有摄像头权限
     */
    public void showCamera() {
        boolean resoult = PermissionUtils.showCamera(this, Manifest.permission.CAMERA);
        //
        if (resoult) {
            requestCameraPermission();
        } else {
            PermissionUtils.startPermissionActivity(this, CaptureActivity.class, Constants.REQUEST_CAMERA);
        }
    }

    /**
     * 弹出权限对话框
     */
    private void requestCameraPermission() {
        String message = getResources().getString(R.string.permission_camera);
        PermissionUtils.requestCameraPermission(this, Manifest.permission.CAMERA, message, Constants.REQUEST_CAMERA);
    }

    /**
     * 系统权限选择的对话框的回调方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.onRequestPermissionsResult(this, CaptureActivity.class, requestCode, grantResults, Constants.REQUEST_CAMERA, PermissionUtils.START_PERMISSION);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            //得到扫码结果
            case Constants.REQUEST_CAMERA:
                String result = data.getExtras().getString("result");
                ToastUtils.showShort(context, result);
                break;
        }
    }
}
