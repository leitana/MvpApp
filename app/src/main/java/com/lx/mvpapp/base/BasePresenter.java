package com.lx.mvpapp.base;

import android.content.Context;

import com.lx.mvpapp.api.ApiFactory;
import com.lx.mvpapp.api.DoubanApi;

/**
 * Created by linxiao on 2018/10/8.
 */

public abstract class BasePresenter {
    public Context mContext;

    public static final DoubanApi doubanApi = ApiFactory.getDoubanApiSingleton();

    public BasePresenter(Context mContext) {
        this.mContext = mContext;
    }
}
