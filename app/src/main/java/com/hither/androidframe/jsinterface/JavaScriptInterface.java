package com.hither.androidframe.jsinterface;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.hither.androidframe.bean.InfoBean;
import com.hither.androidframe.tools.Info;

/**
 * Created by Administrator on 2016/11/8.
 */
public class JavaScriptInterface extends Object {
    private Context context;
    private WebView webView;
    private Activity activity;

    public JavaScriptInterface(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    /**
     * 获取硬件信息,留给后台调用
     *
     * @return
     */
    @JavascriptInterface
    public String getInfoJS() {
        InfoBean info = Info.getInfo(activity);
        return info.toString();
    }
}
