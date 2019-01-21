package com.lx.mvpapp;

import android.app.Application;
import android.content.Context;

import com.yjx.sharelibrary.Share;

import java.io.File;

/**
 * Created by 11300 on 2018/3/1.
 */

public class MyApplication extends Application{
    private static MyApplication instance;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        // 用来缓存的share
        String shareFilePath = getShareDir(this);
        Share.init("CACHE", 50 * 1024, shareFilePath.toString());
    }

    public static Context getContext() {
        return instance;
    }

    public static String getShareDir(Context mContext) {
        String dir = mContext.getApplicationContext().getFilesDir().getAbsolutePath() + File.separator + "sharefile";
        return checkDir(dir);
    }

    private static String checkDir(String dir) {
        File directory = new File(dir);
        if (!directory.exists() || !directory.isDirectory()) {
            directory.mkdirs();
        }
        return dir;
    }
}
