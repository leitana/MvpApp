package com.lx.mvpapp.server;

import com.lx.mvpapp.entity.RESTResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by linxiao on 2019/3/3.
 * RESTFUl返回封装
 *
 */

public class RxResultHelper {
    public static <T>Observable.Transformer<RESTResult<T>, T> handleResult() {
        return new Observable.Transformer<RESTResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<RESTResult<T>> restResultObservable) {
                return restResultObservable.flatMap(
                        new Func1<RESTResult<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(RESTResult<T> trestResult) {
                                if (trestResult.getStatus()) {
                                    return Observable.just(trestResult.getResult());
                                } else {
                                    return Observable.error(new Exception(trestResult.getErrorMessage()));
                                }
                            }
                        }
                );
            }
        };
    }
}

//_apiService.login(mobile, verifyCode)
//        .compose(RxSchedulersHelper.io_main())
//        .compose(RxResultHelper.handleResult())
//        .subscribe(new RxSubscriber<User user>() {
//@Override
//public void _onNext(User user) {
//        // 处理user
//        }
//
//@Override
//public void _onError(String msg) {
//        ToastUtil.showShort(mActivity, msg);
//        });


