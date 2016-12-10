package com.hither.androidframe.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

import android.net.Uri;
import android.util.AttributeSet;

import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hither.androidframe.R;


/**
 * Created by Administrator on 2016/11/23.
 */
public class CustomWebView extends WebView {
    private ProgressBar progressBar;
    private Context context;
    private WebSettings settings;
    private OnScrollChangedCallback mOnScrollChangedCallback;

    public CustomWebView(Context context) {
        super(context);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setBackgroundColor(getResources().getColor(R.color.honeydew));
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 3, 0, 0));
        addView(this.progressBar);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setWebChromeClient(new MyWebChromeClient());
        //只要设置了WebViewClient，则就不会调用系统浏览器。
        setWebViewClient(new MyWebViewClient());
        //设置支持下载
        setDownloadListener(new MyDownloadListenter());
        settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(true);// 设置图片阻塞
        // settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);//自适应屏幕
        // 设置编码
        settings.setDefaultTextEncodingName("utf-8");
        settings.setTextZoom(70);
        // 设置背景颜色 透明
        setBackgroundColor(Color.argb(0, 0, 0, 0));
    }


    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt, l, t);
        }
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    //dx和dy分别是滚动的时候，x和y方向上的偏移量。
    public interface OnScrollChangedCallback {
        void onScroll(int dx, int dy,int l, int t);
    }


    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, final int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(GONE);
            } else {
                if (progressBar.getVisibility() == GONE)
                    progressBar.setVisibility(VISIBLE);
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

    }

    public class MyWebViewClient extends WebViewClient {
        //  shouldOverrideUrlLoading的返回值到底代表什么呢？
        // return true，则在打开新的url时WebView就不会再加载这个url了，所有处理都需要在WebView中操作,包含加载；（点击无反应，如果希望能够跳转，则需要我们自己进行处理）
        // return false，则系统就认为上层没有做处理，接下来还是会继续加载这个url的；默认return false。(点击后正常跳转)

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // 开始加载网页时处理 如：显示"加载提示" 的加载对话框
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            settings.setBlockNetworkImage(false);//放开阻塞
            // 网页加载完成时处理  如：让 加载对话框 消失
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            // 用javascript隐藏系统定义的404页面信息
            String errorHtml = "<!DOCTYPE html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
                    + "<meta name=\"description\" content=\"\">"
                    + "<meta name=\"apple-touch-fullscreen\" content=\"yes\" />"
                    + "<meta content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\" name=\"viewport\" />"
                    + "<meta content=\"no\" name=\"apple-mobile-web-app-capable\" />"
                    + "<meta content=\"black\" name=\"apple-mobile-web-app-status-bar-style\" />"
                    + "<meta content=\"telephone=no\" name=\"format-detection\" />"
                    + "<title>404 Not found</title>"
                    + "<style type=\"text/css\">"
                    + "* {-webkit-box-sizing: border-box;-moz-box-sizing: border-box;box-sizing: border-box;font-family: \"微软雅黑\";padding: 0;margin: 0;-webkit-tap-highlight-color: rgba(0,0,0,0);}"
                    + "html {-ms-touch-action: none;}"
                    + "body,html{width: 100%;height: 100%;font-size: 12px;font-family: \"微软雅黑\",ubuntu, helvetica, arial;background:#edf0f3;overflow: hidden;}"
                    + "a{ text-decoration:none;}body { -webkit-user-select: none;-webkit-touch-callout: none;}img{border:0px;}"
                    + "#image {width: 30%;}"
                    + "#wrap {width: 100%;height: 100%;overflow-x: hidden!important;overflow-y: auto!important;}#wrap{background: #fff;}"
                    + ".box{margin: 45% auto;width: 100%;text-align: center;font-size: 16px;font-weight: lighter;line-height: 30px;}#fresh{color: #00AAEE;}"
                    + "</style></head>"
                    + "<body><div id=\"wrap\"><div class=\"box\">"
                    + "<img id =\"image\"src=\"empty.png\" style=\"vertical-align:middle;\" />"
                    + "<p style=\"color:#C8C9CB;\">网络信号弱、不稳定或路由器连接故障</p><a id=\"fresh\" href=\""
                    + failingUrl + "\">点击刷新</a></div></div></body></html>";
            // view.loadData(errorHtml, "text/html; charset=UTF-8", null);
            view.loadDataWithBaseURL("file:///android_asset/", errorHtml,
                    "text/html; charset=UTF-8", null, null);
        }

    }

    class MyDownloadListenter implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            //下载任务...，主要有两种方式
            //（1）自定义下载任务
            //（2）调用系统的download的模块
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }
}
