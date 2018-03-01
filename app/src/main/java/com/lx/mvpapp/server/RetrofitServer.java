package com.lx.mvpapp.server;

import com.lx.mvpapp.MyApplication;
import com.lx.mvpapp.util.NetUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 11300 on 2018/3/1.
 */

public class RetrofitServer {

    //设缓存有效期为两天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private static RetrofitServer instance = null;

    private static OkHttpClient mOkHttpClient;

    public static RetrofitServer getInstance() {
        if (instance == null) {
            synchronized (RetrofitServer.class) {
                if (instance == null) {
                    instance = new RetrofitServer();
                    initOkHttpClient();
                }
            }
        }
        return instance;
    }

    private static void initOkHttpClient() {
        if (mOkHttpClient == null) {
            // 因为BaseUrl不同所以这里Retrofit不为静态，但是OkHttpClient配置是一样的,静态创建一次即可
            File cacheFile = new File(MyApplication.getContext().getCacheDir(),
                    "HttpCache"); // 指定缓存路径
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 指定缓存大小100Mb
            // 云端响应头拦截器，用来配置缓存策略
            Interceptor rewriteCacheControlInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetUtil.isConnected(MyApplication.getContext())) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE).build();
                        Logger.e("no network");
                    }
                    Response originalResponse = chain.proceed(request);
                    if (NetUtil.isConnected(MyApplication.getContext())) {
                        //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                        String cacheControl = request.cacheControl().toString();
                        return originalResponse.newBuilder()
                                .header("Cache-Control", cacheControl)
                                .removeHeader("Pragma").build();
                    } else {
                        return originalResponse.newBuilder().header("Cache-Control",
                                "public, only-if-cached," + CACHE_STALE_SEC)
                                .removeHeader("Pragma").build();
                    }
                }
            };
            mOkHttpClient = new OkHttpClient.Builder().cache(cache)
                    .addNetworkInterceptor(rewriteCacheControlInterceptor)
                    .addInterceptor(rewriteCacheControlInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS).build();

        }
    }

    public static <T> T createApi(Class<T> clazz, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

}
