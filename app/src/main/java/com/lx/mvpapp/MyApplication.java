package com.lx.mvpapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by 11300 on 2018/3/1.
 */

public class MyApplication extends Application{
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
