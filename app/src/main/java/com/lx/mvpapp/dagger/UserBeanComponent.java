package com.lx.mvpapp.dagger;

import com.lx.mvpapp.MainActivity;

import dagger.Component;

/**
 * Created by 11300 on 2018/2/27.
 */
@Component(modules = ModuleUserBean.class)
public interface UserBeanComponent {
    void inject(MainActivity activity);
}
