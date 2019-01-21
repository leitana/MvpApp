package com.lx.mvpapp.base;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * Created by linxiao on 2018/10/10.
 */

public class ActivityCollector {
    private static Stack<Activity> activityStack;
    private static ActivityCollector instance;

    private ActivityCollector(){

    }

    public static ActivityCollector getInstance(){
        if (instance == null) {
            synchronized (ActivityCollector.class){
                if (instance == null) {
                    instance = new ActivityCollector();
                }
            }
        }
        return instance;
    }

    /**
     * 添加activity
     * @param activity
     */
    public void addActivity(Activity activity){
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 移除activity
     * @param activity
     */
    public void removeActivity(Activity activity){
        activityStack.remove(activity);
    }

    /**
     * 获取当前activity
     * @return
     */
    public Activity currentActivity(){
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前activity
     */
    public void finishActivity(){
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if (activity != null && !activity.isFinishing()) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
                break;
            }
        }
        activityStack.clear();
    }

    /**
     * 获取指定的Activity
     *
     */
    public static Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    public void refreshAllActivity(){
        for (Activity activity : activityStack) {
            activity.recreate();
        }
    }

}
