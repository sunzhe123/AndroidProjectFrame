package com.hither.androidframe.project;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.hither.androidframe.R;
import com.hither.androidframe.bean.InfoBean;
import com.hither.androidframe.encode.Encrypt;
import com.hither.androidframe.jsinterface.JavaScriptInterface;
import com.hither.androidframe.project.map.view.GaodeMapActivity;
import com.hither.androidframe.project.login.view.LoginActivity;
import com.hither.androidframe.project.tablayout.TabLayoutActivity;
import com.hither.androidframe.project.zxing.CaptureActivity;
import com.hither.androidframe.tools.Info;
import com.hither.androidframe.tools.LogUtils;
import com.hither.androidframe.tools.SharedPrefUtils;
import com.hither.androidframe.tools.ToastUtils;
import com.hither.androidframe.utils.Constants;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends BaseActivity {
    private FrameLayout fragment_layout;
    private SlidingMenu slidingMenu;
    private View main_layout;
    private ActivityOptionsCompat compat;

    @Override
    public int setContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        main_layout = $(R.id.main_layout);
        fragment_layout = $(R.id.fragment_layout);
        //让新的Activity从一个小的范围扩大到全屏
        //1.The View that the new activity is animating from
        //2-3.拉伸开始的坐标
        //4-5.拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        compat = ActivityOptionsCompat.makeScaleUpAnimation(main_layout, 0, 0, main_layout.getWidth(), main_layout.getHeight());
        initSlidingMenu();
        InfoBean info = Info.getInfo(this);
        LogUtils.i("TAG", "===>info:" + info.toString());
        Map<String, String> map = new HashMap<>();
        String string = SharedPrefUtils.getString("headerInfo", "暂无数据");
        try {
            String decode = Encrypt.decode(string, "UTF-8");
            map.put("ttgz", decode);
            LogUtils.i("TAG", "===>map:" + map);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                ActivityCompat.startActivity(this,
                        new Intent(this, LoginActivity.class), compat.toBundle());
                break;
            //扫码
            case R.id.loginBtn2:
                Intent intent = new Intent(context, CaptureActivity.class);
                startActivityForResult(intent, Constants.CODE_CAMERA_PERMISSION);
                break;
            case R.id.goMap:
                Intent intentMap = new Intent(MainActivity.this, GaodeMapActivity.class);
                Bundle bundle = new Bundle();
                double latitude = 39.915168;
                double longitude = 116.403875;
                bundle.putDouble("latitude", latitude);
                bundle.putDouble("longitude", longitude);
                intentMap.putExtras(bundle);
                startActivity(intentMap);
                break;
            case R.id.shared:
                showShare();
                break;
            case R.id.webView:
                ActivityCompat.startActivity(this,
                        new Intent(this, WebViewActivity.class), compat.toBundle());
//                startActivity(new Intent(this, WebViewActivity.class));
                break;
            case R.id.goTabLayout:
                Intent intent1 = new Intent(this, TabLayoutActivity.class);
                startActivity(intent1);
                break;

        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            //得到扫码结果
            case Constants.CODE_CAMERA_PERMISSION:
                String result = data.getExtras().getString("result");
                ToastUtils.showShort(context, result);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /**
     * 按回退后不退出程序，在后台运行
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
