package com.lx.mvpapp.server;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;


/**
 * Created by linxiao on 2019/3/5.
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {

    private String msg;

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof UnknownHostException) {
            msg = "网络错误...";
        } else if (e instanceof SocketTimeoutException) {
            // 超时
            msg = "超时...";
        }else{
            msg = "请求失败，请稍后重试...";
        }
        _onError(msg);
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    public abstract void _onNext(T t);

    public abstract void _onError(String msg);
}
