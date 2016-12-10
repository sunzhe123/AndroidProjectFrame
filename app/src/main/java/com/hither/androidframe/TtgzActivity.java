package com.hither.androidframe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hither.androidframe.widget.CustomWebView;

public class TtgzActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttgz);
        CustomWebView open_ttgz_webview = (CustomWebView) findViewById(R.id.open_ttgz);
        open_ttgz_webview.loadUrl("file:///android_asset/open_ttgz.html");
    }
}
