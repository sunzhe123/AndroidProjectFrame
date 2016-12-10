package com.hither.androidframe.project.download;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hither.androidframe.R;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

public class DownLoadActivity extends AppCompatActivity implements View.OnClickListener {
    private Button downloadBtn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        downloadBtn = (Button) findViewById(R.id.downloadBtn);
        downloadBtn.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.downloadBtn:
                Toast.makeText(DownLoadActivity.this, "开始下载", Toast.LENGTH_SHORT).show();
                OkHttpUtils//
                        .get()//
                        .url("http://video.xinpianchang.com/5833eb712b7eb.mp4")//
                        .build()//
                        .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "vdianying.mp4")//
                        {
                            @Override
                            public void inProgress(float progress) {
                                textView.setText("" + (int) (100 * progress) + "%");
                                //   mProgressBar.setProgress((int) (100 * progress));
                            }

                            @Override
                            public void onError(Request request, Exception e) {
                                Toast.makeText(DownLoadActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(File file) {
                                Toast.makeText(DownLoadActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                                //   Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                            }
                        });
                break;
        }
    }
}
