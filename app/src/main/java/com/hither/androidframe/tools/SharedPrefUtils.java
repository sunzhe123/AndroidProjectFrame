package com.hither.androidframe.tools;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hither.androidframe.application.MyApplication;

/**
 * 关于SharedPreferences工具类
 */
public class SharedPrefUtils {
    private static SharedPreferences sp;

    static {
        if (sp == null) {
            MyApplication.getInstance();
            sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstanceContext());
        }
    }

    public static void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public static void putLong(String key, long value) {
        sp.edit().putLong(key, value).commit();
    }

    public static void putString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public static void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public static void putFloat(String key, float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public static int getInt(String key) {
        return sp.getInt(key, -1);
    }

    public static int getInt(String key, int defalutValue) {
        return sp.getInt(key, defalutValue);
    }

    public static long getLong(String key) {
        return sp.getLong(key, -1L);
    }

    public static String getString(String key) {
        return sp.getString(key, null);
    }

    public static String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public static float getFloat(String key) {
        return sp.getFloat(key, -1f);
    }

}
