package com.lx.mvpapp.server;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by linxiao on 2019/2/27.
 * 以后任何使用
 * .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
 * 的地方都可以使用.compose(RxSchedulersHelper.io_main())代替
 */

public class RxSchedulerHelper {
    public static <T> Observable.Transformer<T, T> io_main(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
