package com.lx.mvpapp.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.lx.mvpapp.util.ThemeUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by linxiao on 2018/10/10.
 */

public abstract class BaseActivity extends BaseFragemntActivity implements IBaseView{

    public Context mContext = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    protected void toast(String msg){
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void showProgress(String message) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void cancelProgress() {

    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(this, getResources().getText(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    protected void applyKitKatTranslucency() {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);

            // mTintManager.setStatusBarTintResource(R.color.red_base);//通知栏所需颜色
            mTintManager.setStatusBarTintColor(ThemeUtil.getThemeColor());
        }

    }

    protected void applyKitKatTranslucency(int res) {

        // KitKat translucent navigation/status bar.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);

            // mTintManager.setStatusBarTintResource(R.color.red_base);//通知栏所需颜色
            mTintManager.setStatusBarTintColor(res);
        }

    }

    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
