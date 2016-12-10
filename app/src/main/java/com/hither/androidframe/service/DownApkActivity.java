package com.hither.androidframe.service;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hither.androidframe.R;
import com.hither.androidframe.service.utils.ExternalStrorageUtils;
import com.hither.androidframe.utils.Constants;
import com.hither.androidframe.widget.CustomProgressDialog;

public class DownApkActivity extends AppCompatActivity implements View.OnClickListener {
    private String down_url = "http://www.apk3.com/uploads/soft/20160511/kukuweather1113_15_3_jifeng_sign.apk";
    private TextView showUpdate;
    Intent intent;
    //    TextView showProgressTextViewId;
//    SeekBar showProgressSeekBarId;
//    Dialog mydialog;
    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downapk);
        IntentFilter broadcast_action = new IntentFilter("BROADCAST_ACTION");
        broadcast_action.addCategory(Intent.CATEGORY_DEFAULT);
        DownloadStateReceiver downloadStateReceiver = new DownloadStateReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(downloadStateReceiver, broadcast_action);
        showUpdate = $(R.id.showUpdate);
        Button downApk = $(R.id.downApk);
        downApk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.downApk:
                Log.i("ACTIVITY", "===>BEGIN");
                intent = new Intent(this, MyService.class);
                intent.putExtra("down_url", down_url);
                startService(intent);
                customProgressDialog = new CustomProgressDialog(this);
                customProgressDialog.show();
//                View dialog = LayoutInflater.from(this).inflate(R.layout.dialog_custom_progress, null);
//                showProgressTextViewId = (TextView) dialog.findViewById(R.id.showProgressTextViewId);
//                showProgressSeekBarId = (SeekBar) dialog.findViewById(R.id.showProgressSeekBarId);
//                mydialog = new Dialog(this, R.style.loading_dialog);
//                mydialog.setCancelable(false);
//                mydialog.setContentView(dialog, new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT));
//                mydialog.show();
                break;
        }
    }

    private <T extends View> T $(int rsID) {
        return (T) super.findViewById(rsID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }


    private class DownloadStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String progress = intent.getStringExtra("progress");
            if ("下载失败".equals(progress)) {
                customProgressDialog.setProgress(progress);
                customProgressDialog.setCancelable(true);
//                showUpdate.setText(progress);
//                showProgressTextViewId.setText(progress);
            } else if ("完成".equals(progress)) {
                String appPath = ExternalStrorageUtils.getAbsolutePath(Constants.context);
                String appName = Constants.applicationName;
                Log.i("Down", "===>appPath:" + appPath + ",appName:" + appName);
                Uri parse = Uri.parse("file:///" + appPath + "/" + appName);
                Intent installApp = new Intent(Intent.ACTION_VIEW);
                installApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                installApp.setDataAndType(parse,
                        "application/vnd.android.package-archive");
                startActivity(installApp);
                customProgressDialog.setProgress(progress);
                customProgressDialog.dismiss();
//                mydialog.dismiss();
            } else {
                customProgressDialog.setProgress(progress);
//                showProgressTextViewId.setText(progress + "%");
            }
        }
    }
}
