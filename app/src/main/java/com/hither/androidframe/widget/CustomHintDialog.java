package com.hither.androidframe.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hither.androidframe.R;

/**
 * Created by Administrator on 2016/11/8.
 */
public class CustomHintDialog extends Dialog {
    private Context context;
    private String hint;
    private TextView toast_hintText;

    public CustomHintDialog(Context context) {
        super(context);
    }

    protected CustomHintDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CustomHintDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public CustomHintDialog(Context context, String hint) {
        super(context);
        this.context = context;
        this.hint = hint;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_hint_layout);
        toast_hintText = $(R.id.toast_hintText);
        if (hint != null) {
            toast_hintText.setText(hint);
        }
    }

    public <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }
}
