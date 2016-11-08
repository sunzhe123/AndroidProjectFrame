package com.hither.androidframe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hither.androidframe.R;
import com.hither.androidframe.tools.LayoutInflaters;

/**
 * 自定义标题栏
 * Created by Administrator on 2016/11/8.
 */
public class TitleBar extends RelativeLayout {
    //父布局
    protected RelativeLayout title_layout;
    //左边回退按钮
    protected ImageView backKey;
    //中间标题
    protected TextView titleText;
    //右边ImageView
    protected ImageView rightIcon;
    //右边文本
    protected TextView rightText;

    public TitleBar(Context context) {
        super(context);
        initTitleBarView(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTitleBarView(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initTitleBarView(Context context, AttributeSet attrs) {
        View inflaterView = LayoutInflaters.getLayoutInflaterView(context, R.layout.title_bar_layout, this);
        RelativeLayout relativeLayout = (RelativeLayout) inflaterView;

        title_layout = (RelativeLayout) relativeLayout.findViewById(R.id.title_layout);
        backKey = (ImageView) relativeLayout.findViewById(R.id.backKey);
        titleText = (TextView) relativeLayout.findViewById(R.id.titleText);
        rightIcon = (ImageView) relativeLayout.findViewById(R.id.rightIcon);
        rightText = (TextView) relativeLayout.findViewById(R.id.rightText);
    }

    /**
     * 设置title的背景颜色
     *
     * @param color
     */
    public void setBackgroundColor(int color) {
        title_layout.setBackgroundColor(color);
    }

    /**
     * 设置左边回退按钮的图片
     *
     * @param resId
     */
    public void setBackKeyImageResource(int resId) {
        backKey.setImageResource(resId);
    }

    /**
     * 设置左边回退按钮的监听事件
     *
     * @param listener
     */
    public void setBackKeyClickListener(OnClickListener listener) {
        backKey.setOnClickListener(listener);
    }

    /**
     * 设置左边回退按钮是否显示
     *
     * @param visibility
     */
    public void setBackKeyVisibility(int visibility) {
        backKey.setVisibility(visibility);
    }

    /**
     * 设置之间的标题内容
     *
     * @param text
     */
    public void setTitleText(String text) {
        titleText.setText(text);
    }

    /**
     * 设置标题文字的颜色
     *
     * @param color
     */
    public void setTitleTextColor(int color) {
        titleText.setTextColor(color);
    }

    /**
     * 设置中间标题是否显示
     *
     * @param visibility
     */
    public void setTitleTextVisibility(int visibility) {
        titleText.setVisibility(visibility);
    }

    /**
     * 设置右边要显示的图片
     *
     * @param resId
     */
    public void setRightIconImageResource(int resId) {
        rightIcon.setImageResource(resId);
    }

    /**
     * 设置右边图片的点击事件
     *
     * @param listener
     */
    public void setRightIconClickListener(OnClickListener listener) {
        rightIcon.setOnClickListener(listener);
    }

    /**
     * 设置右边图片的组件是否显示
     *
     * @param visibility
     */
    public void setRightIconVisibility(int visibility) {
        rightIcon.setVisibility(visibility);
    }

    /**
     * 设置右边的文字组件的文字内容
     *
     * @param text
     */
    public void setRightText(String text) {
        rightText.setText(text);
    }

    /**
     * 设置右边文字的颜色
     *
     * @param color
     */
    public void setRightTextColor(int color) {
        rightText.setTextColor(color);
    }

    /**
     * 设置右边TextView是否显示
     *
     * @param visibility
     */
    public void setRightTextVisibility(int visibility) {
        rightText.setVisibility(visibility);
    }

    /**
     * 设置右边TextView的点击事件
     *
     * @param listener
     */
    public void setRightTextClickListener(OnClickListener listener) {
        rightText.setOnClickListener(listener);
    }
}
