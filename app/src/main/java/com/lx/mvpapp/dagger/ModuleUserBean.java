package com.lx.mvpapp.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 11300 on 2018/2/27.
 */

@Module
public class ModuleUserBean {
    String tag;

    public ModuleUserBean(String tag) {
        this.tag = tag;
    }

    @Provides
    UserBean provideUserbean() {
        return new UserBean(tag);
    }
}
