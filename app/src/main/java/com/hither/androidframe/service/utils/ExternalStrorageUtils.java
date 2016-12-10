package com.hither.androidframe.service.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.hither.androidframe.utils.Constants;

public class ExternalStrorageUtils {
    public static boolean isSDcardMounted() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            Log.i("isSDcard", "===sd卡不存在");
        }
        return false;
    }

    public static String getAbsolutePath(Context context) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constants.packageName;
        } else {
            return context.getFilesDir().getAbsolutePath() + "/" + Constants.packageName;
        }
    }

    public static boolean writeToExternalStrorage(String dirName,
                                                  String fileName, byte[] data) {
        if (isSDcardMounted()) {
            File path = Environment.getExternalStorageDirectory();
            File parentFile = new File(path, dirName);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            File file = new File(parentFile, fileName);
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(data);
                return true;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }
}
