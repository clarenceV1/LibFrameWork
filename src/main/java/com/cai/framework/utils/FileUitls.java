package com.cai.framework.utils;

import android.content.Context;

import java.io.File;

/**
 * Created by clarence on 2018/2/5.
 */

public class FileUitls {
    public static File getFile(Context context, String path, String fileName) {
        File dir;
        if (StringUtils.isEmpty(path)) {
            dir = context.getFilesDir();
        } else {
            dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        File cache = new File(dir, fileName);
        return cache;
    }

    public static boolean deleteFile(Context context,String fileName){
        if (context == null) {
            return false;
        } else {
            return context.deleteFile(fileName);
        }
    }
}
