package com.hither.androidframe.project;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hither.androidframe.R;
import com.hither.androidframe.tools.StatusBarUtil2;
import com.hither.androidframe.widget.CustomWebView;

public class WebViewActivity extends AppCompatActivity {
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil2.setColor(this, getResources().getColor(R.color.palevioletred2), 0);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {
        final CustomWebView webView = $(R.id.webView);
        fab = $(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回到顶部
                webView.scrollTo(0, 0);
            }
        });
        webView.loadUrl("http://shop.beauty-know.com/mobile.community/diaryBookCommunityH/diaryHomeBound.html");
        webView.setOnScrollChangedCallback(new CustomWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy, int l, int t) {
                //得到当前屏幕的一半
                int heightPixels = getResources().getDisplayMetrics().heightPixels / 2;
                //当滑动的距离超过屏幕的一半
                if (t > heightPixels) {
                    //进行想要的处理工作
                    fab.setVisibility(View.VISIBLE);
                } else if (t <= heightPixels) {
                    //恢复操作
                    fab.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }
}
