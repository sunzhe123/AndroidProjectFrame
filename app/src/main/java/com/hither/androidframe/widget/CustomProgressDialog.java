package com.hither.androidframe.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hither.androidframe.R;

/**
 * Created by Administrator on 2016/12/9.
 */
public class CustomProgressDialog extends Dialog {
    private SeekBar showProgressSeekBarId;
    private TextView showProgressTextViewId;
    private Context context;
    private String progress;


    public CustomProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected CustomProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom_progress);
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        setCancelable(false);
        showProgressSeekBarId = $(R.id.showProgressSeekBarId);
        showProgressTextViewId = $(R.id.showProgressTextViewId);
    }


    public void setProgress(String progress) {
        if (progress != null) {
            boolean flags = progress.matches("[0-9]+");
            if (flags) {
                int i = Integer.parseInt(progress);
                showProgressTextViewId.setText(progress + "%");
                showProgressSeekBarId.setProgress(i);
            } else {
                showProgressTextViewId.setText(progress);
            }
        }
    }

    private <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }
}
