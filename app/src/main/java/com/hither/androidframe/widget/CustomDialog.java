package com.hither.androidframe.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hither.androidframe.R;
import com.hither.androidframe.tools.ToastUtils;

/**
 * 自定义Dialog
 * Created by Administrator on 2016/11/8.
 */
public class CustomDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private String title;
    private String details;
    private String positive;
    private String negative;
    private int tag;
    //自定义布局中的组件的引用
    private LinearLayout dialog_top;
    private TextView dialog_title;
    private TextView dialog_alert_message;
    private Button dialog_positive;
    private Button dialog_negative;
    //定义用于自定义Dialog判断显示按钮状态的的常量
    public static final int DIALOG_BTN_TWO = 2;//两个按钮都显示
    public static final int DIALOG_BTN_POSITIVE = 1;//只显示positive
    public static final int DIALOG_BTN_NEGATIVE = 3;//只显示negative
    public static final int DIALOG_BTN_NULL = 0;//两个按钮都不显示

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CustomDialog(Context context, String title, String details, String positive, String negative, int tag) {
        super(context);
        this.title = title;
        this.details = details;
        this.positive = positive;
        this.negative = negative;
        this.tag = tag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);
        dialog_top = $(R.id.dialog_top);
        dialog_title = $(R.id.dialog_title);
        dialog_alert_message = $(R.id.dialog_alert_message);
        dialog_positive = $(R.id.dialog_positive);
        dialog_negative = $(R.id.dialog_negative);
        switch (tag) {
            case DIALOG_BTN_TWO:
                dialog_positive.setText(positive);
                dialog_negative.setText(negative);
                break;
            case DIALOG_BTN_POSITIVE:
                dialog_negative.setVisibility(View.GONE);
                dialog_positive.setText(positive);
                break;
            case DIALOG_BTN_NEGATIVE:
                dialog_positive.setVisibility(View.GONE);
                dialog_negative.setText(negative);
                break;
            case DIALOG_BTN_NULL:
                break;
        }
        dialog_positive.setOnClickListener(this);
        dialog_negative.setOnClickListener(this);
        if (title != null) {
            dialog_title.setText(title);
        }
        if (details != null) {
            dialog_alert_message.setText(details);
        }

    }

    public <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_positive:
                ToastUtils.showShort(context, "positive被点击");
                break;
            case R.id.dialog_negative:
                ToastUtils.showShort(context, "negative被点击");
                break;
        }
    }
}
