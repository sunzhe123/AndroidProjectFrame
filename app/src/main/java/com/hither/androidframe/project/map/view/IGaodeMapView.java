package com.hither.androidframe.project.map.view;

import android.os.Bundle;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/30.
 */
public interface IGaodeMapView {
    /**
     * 设置状态栏颜色
     */
    void initStatusBar();

    /**
     * 设置标题栏
     */
    void initTitleBar();

    /**
     * 设置map
     */
    void initMapView(Bundle savedInstanceState);

    /**
     * 在地图上显示自己的定位图层
     */
    void initMyLoncation();

    /**
     * 显示目的地的图层
     */
    void initTargetLocation();

    /**
     * 设置圆球菜单
     */
    void initFloatingActionButton();

    /**
     * 弹出PopupWindow
     */
    void initPopupWindow(List<Map<String, Object>> list);

    /**
     * 圆球菜单从隐藏状态变成显示状态
     */
    void initFloatingActionButtonVisible();

    /**
     * 启动外部地图
     */
    void initStartMapIntent();
}
