package com.lx.mvpapp.api;

import com.lx.mvpapp.server.RetrofitServer;

/**
 * Created by linxiao on 2018/10/8.
 */

public class ApiFactory {
    public static final String DOUBAN_BASE_URL = "https://api.douban.com/";
    protected static final Object monitor = new Object();//作用是当没有明确的对象作为锁，只是想让一段代码同步时，可以创建一个特殊的对象来充当锁：
    static DoubanApi doubanApiSingleton = null;

    public static DoubanApi getDoubanApiSingleton(){
        synchronized (monitor) {
            if (doubanApiSingleton == null) {
                doubanApiSingleton = RetrofitServer.createApi(DoubanApi.class, DOUBAN_BASE_URL);
            }
            return doubanApiSingleton;
        }
    }
}
