package com.lx.mvpapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by linxiao on 2018/10/10.
 */

public class BaseFragemntActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        ActivityCollector.getInstance().removeActivity(this);
        super.onDestroy();

    }
}
