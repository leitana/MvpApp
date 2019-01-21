package com.lx.mvpapp.base;

import android.content.Context;

/**
 * Created by linxiao on 2018/10/10.
 */

public interface IBaseView {
    /**
     * 显示进度条
     * @param message
     */
    void showProgress(String message);

    /**
     * 显示无文字的进度条
     */
    void showProgress();

    /**
     * 取消显示进度条
     */
    void cancelProgress();

    /**
     * 根据资源文件id弹出toast
     * @param resId
     */
    void showToast(int resId);

    /**
     * 根据字符串弹出toast
     * @param msg
     */
    void showToast(String msg);

    /**
     * 获取上下文对象
     * @return
     */
    Context getContext();


}
