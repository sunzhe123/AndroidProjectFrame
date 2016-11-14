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

/**
 * 自定义Dialog
 * Created by Administrator on 2016/11/8.
 */
public class CustomDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private String index;
    private String title;
    private String details;
    private String positive;
    private String negative;
    private boolean flag = true;
    //自定义布局中的组件的引用
    private LinearLayout dialog_top;
    private TextView dialog_title;
    private TextView dialog_alert_message;
    private Button dialog_positive;
    private Button dialog_negative;

    public CustomDialog(Context context) {
//        super(context, R.style.MyDialog);
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);
        initCancelable();
        initView();
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 设置按空白处能否取消对话框
     * true：能
     * false：不能
     * 默认为true
     */
    private void initCancelable() {
        setCancelable(flag);
    }

    private void initEvent() {
        dialog_positive.setOnClickListener(this);
        dialog_negative.setOnClickListener(this);
    }

    private void initData() {
        //如果用户自定了title和message
        if (title != null) {
            dialog_title.setVisibility(View.VISIBLE);
            dialog_title.setText(title);
        }
        if (details != null) {
            dialog_alert_message.setText(details);
        }
        //如果设置按钮的文字
        if (positive != null) {
            dialog_positive.setVisibility(View.VISIBLE);
            dialog_positive.setText(positive);
        }
        if (negative != null) {
            dialog_negative.setVisibility(View.VISIBLE);
            dialog_negative.setText(negative);
        }
    }

    private void initView() {
        dialog_top = $(R.id.dialog_top);
        dialog_title = (TextView) findViewById(R.id.dialog_title);
        dialog_alert_message = $(R.id.dialog_alert_message);
        dialog_positive = $(R.id.dialog_positive);
        dialog_negative = $(R.id.dialog_negative);

    }


    public CustomDialog setTtitle(String title) {
//        dialog_title.setText(title);
        this.title = title;
        return this;
    }

    public CustomDialog setDialogMessage(String details) {
//        dialog_alert_message.setText(details);
        this.details = details;
        return this;
    }

    public CustomDialog setCancelables(boolean flag) {
        this.flag = flag;
        return this;
    }

    //这里，我们定义一个确认接口
    public interface OnNegativeClickListener {
        public void onNegativeClick();
    }

    //这里，我们定义一个取消接口
    public interface OnPositiveClickListener {
        public void onPositiveClick();
    }

    private OnPositiveClickListener positiveListener;
    private OnNegativeClickListener negativeListener;

    //写一个确认按钮的接口监听的方法
    public CustomDialog setPositiveOnItemClickListener(String positive, OnPositiveClickListener positiveListener) {
//        dialog_positive.setVisibility(View.VISIBLE);
//        dialog_positive.setText(positive);
        this.positive = positive;
        this.positiveListener = positiveListener;
        return this;
    }

    //写一个取消按钮的接口监听的方法
    public CustomDialog setNegativeOnItemClickListener(String negative, OnNegativeClickListener negativeListener) {
//        dialog_negative.setVisibility(View.VISIBLE);
//        dialog_negative.setText(negative);
        this.negative = negative;
        this.negativeListener = negativeListener;
        return this;
    }

    public <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_positive:
                if (positiveListener != null) {
                    positiveListener.onPositiveClick();
                }

                break;
            case R.id.dialog_negative:

                if (negativeListener != null) {
                    negativeListener.onNegativeClick();

                }
                break;
        }
    }
}
