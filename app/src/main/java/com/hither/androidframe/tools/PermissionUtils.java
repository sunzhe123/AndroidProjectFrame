package com.hither.androidframe.tools;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.hither.androidframe.utils.Constants;
import com.hither.androidframe.widget.CustomDialog;

/**
 * 权限工具类
 * Created by Administrator on 2016/11/14.
 */
public class PermissionUtils {
    public static int START_PERMISSION = 1;//用来判断在权限成功开启之后是否要跳转到其他页面(为1时跳转)

    /**
     * 检查是否拥有权限
     *
     * @param activity:代表当前的activity
     * @param permission:需要判断的权限
     * @return
     */
    public static boolean checkPermisson(Activity activity, String permission) {
        boolean resoult = ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED;
        return resoult;
    }

    /**
     * 弹出权限对话框
     *
     * @param activity:代表当前的activity
     * @param permission:权限
     * @param message:对话框消息
     */
    public static void requestPermission(final Activity activity, final String permission, String message, final int REQUEST_CAMERA) {
        //
        boolean result = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        if (result) {
            final CustomDialog customDialog = new CustomDialog(activity);
            customDialog.setTtitle("提示").setDialogMessage(message).setPositiveOnItemClickListener("确定", new CustomDialog.OnPositiveClickListener() {
                @Override
                public void onPositiveClick() {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{permission},
                            REQUEST_CAMERA);
                    customDialog.dismiss();
                }
            }).setCancelables(false).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission},
                    REQUEST_CAMERA);
        }
    }


//    /**
//     * @param activity:代表当前的activity
//     * @param cls:权限开启后要跳转的activity
//     * @param requestCode:得到的请求码
//     * @param grantResults：所有要请求的权限的数组
//     * @param flag:用来判断在权限成功开启之后是否要跳转到其他页面(为1时跳转)
//     */
//    public static void onRequestPermissionsResult(Activity activity, Class<?> cls, int requestCode, int[] grantResults, int flag) {
//        switch (requestCode) {
//            case Constants.REQUEST_CAMERA:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //开启权限后是否跳到其他页面
//                    if (flag == START_PERMISSION) {
//                        startPermissionActivity(activity, cls, Constants.REQUEST_CAMERA);
//                    }
//                } else {
//                    new CustomDialog(activity).setDialogMessage("权限被拒绝").setCancelables(true).show();
//                }
//                break;
//            case Constants.CODE_COARSE_LOCATION:
//                break;
//            case Constants.CODE_FINE_LOCATION:
//                break;
//            case Constants.CODE_WRITE_EXTERNAL_STORAGE:
//                break;
//            case Constants.CODE_READ_EXTERNAL_STORAGE:
//                break;
//            case Constants.CODE_READ_PHONE_STATE:
//                break;
//        }
//    }

    /**
     * @param activity:代表当前的activity
     * @param cls:权限开启后要跳转的activity
     */
    public static void startPermissionActivity(Activity activity, Class<?> cls, int RESULTCODE) {
        Intent intent = new Intent(activity, cls);
        activity.startActivityForResult(intent, RESULTCODE);
    }
}
