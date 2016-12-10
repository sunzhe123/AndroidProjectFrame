package com.hither.androidframe.tools;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.hither.androidframe.project.zxing.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qianxiaoai on 2016/7/7.
 */
public class PermissionTools {

    private static final String TAG = PermissionTools.class.getSimpleName();

    private PermissionCallback permissionCallback;

    private static void requestMultiResult(Activity activity, String[] permissions, int[] grantResults, PermissionCallback permissionCallback) {
        LogUtils.i(TAG, "===>" + activity);
        if (activity == null) {
            return;
        }
        Map<String, Integer> perms = new HashMap<>();
        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            perms.put(permissions[i], grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permissions[i]);
            }
        }
        if (notGranted.size() == 0) {
            //在这里进行权限打开成功之后的其余操作
            permissionCallback.onPermissiCallback();
        }
    }


    /**
     * 一次申请多个权限
     */
    public static boolean requestMultiPermissions(final Activity activity, String[] requestPermissions, int CODE_MULTI_PERMISSION) {
        final List<String> permissionsList = getNoGrantedPermission(activity, requestPermissions);
        if (permissionsList == null) {
            return true;
        }
        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                    CODE_MULTI_PERMISSION);
        }
        return false;
    }

    /**
     * @param activity
     * @param permissions
     * @param grantResults
     */
    public static void requestPermissionsResult(Activity activity, String[] permissions, int[] grantResults, PermissionCallback permissionCallback) {
        if (activity == null) {
            return;
        }
        requestMultiResult(activity, permissions, grantResults, permissionCallback);
    }


    /**
     * @param activity
     * @return
     */
    public static ArrayList<String> getNoGrantedPermission(Activity activity, String[] requestPermissions) {
        ArrayList<String> permissions = new ArrayList<>();
        for (int i = 0; i < requestPermissions.length; i++) {
            String requestPermission = requestPermissions[i];
            int checkSelfPermission = -1;
            try {
                checkSelfPermission = ActivityCompat.checkSelfPermission(activity, requestPermission);
            } catch (RuntimeException e) {
                Toast.makeText(activity, "please open those permission", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(requestPermission);
            }
        }
        return permissions;
    }

    public interface PermissionCallback {
        void onPermissiCallback();
    }
}
