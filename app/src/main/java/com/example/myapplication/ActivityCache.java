package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class ActivityCache {
    public static void clearApplicationData(Context context) {
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String list : children) {
                Log.d("캐시삭제 ", list);
                if(list.equals("shared_prefs")) continue;
                deleteDir(new File(appDir, list));
            }
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
