package com.hither.androidframe.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.hither.androidframe.R;

/**
 * Created by Administrator on 2016/11/23.
 */
public class MyWebView extends RelativeLayout {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 浏览器
     */
    private WebView webView;
    /**
     * 加载进度
     */
    private ProgressBar progressBar;
    // private CircleProgressBar circleProgressBar;
    private WebSettings settings;
    private FloatingActionButton floatingActionButton;

    public MyWebView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        // TODO Auto-generated method stub
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_webview, this);
        webView = (WebView) view.findViewById(R.id.view_webView);
        progressBar = (ProgressBar) view.findViewById(R.id.view_webview_progress);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(GONE);
        setWebview();
    }

    private void setWebview() {
        webView.setWebChromeClient(new MyWebChromeClient());
        //只要设置了WebViewClient，则就不会调用系统浏览器。
        webView.setWebViewClient(new MyWebViewClient());
        //设置支持下载
        webView.setDownloadListener(new MyDownloadListenter());
        webView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
//        webView.setOnScrollChangedCallback(new CustomWebView.OnScrollChangedCallback() {
//            @Override
//            public void onScroll(int dx, int dy) {
//                int heightPixels = getResources().getDisplayMetrics().heightPixels / 2;
//                //当滑动的距离超过屏幕的一半
//                if (dy > heightPixels) {
//                    //进行想要的处理工作
//                    floatingActionButton.setVisibility(VISIBLE);
//                } else if (dy <= heightPixels) {
//                    //恢复操作
//                    floatingActionButton.setVisibility(GONE);
//                }
//            }
//        });
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(true);// 设置图片阻塞
        settings.setDefaultTextEncodingName("utf-8");
        settings.setTextZoom(70);
    }

    /**
     * 获取WebView
     *
     * @return
     */
    public WebView getWebView() {
        return webView;
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
            mContext.startActivity(intent);
        }
    }
}
