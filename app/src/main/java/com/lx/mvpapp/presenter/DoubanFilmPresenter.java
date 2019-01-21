package com.lx.mvpapp.presenter;

import android.content.Context;

import com.lx.mvpapp.base.BasePresenter;
import com.lx.mvpapp.entity.filmdetail.FilmDetail;
import com.lx.mvpapp.entity.filmlive.FilmLive;
import com.lx.mvpapp.entity.top250.Root;
import com.lx.mvpapp.viewinterface.film.IgetLiveView;
import com.lx.mvpapp.viewinterface.film.IgetTop250View;
import com.lx.mvpapp.viewinterface.film.IgetFilmDetail;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by linxiao on 2018/11/1.
 */

public class DoubanFilmPresenter extends BasePresenter{
    public DoubanFilmPresenter(Context mContext) {
        super(mContext);
    }

    /**
     * 正在热映
     */
    public void getFilmLive(final IgetLiveView igetLiveView){
        doubanApi.getLiveFilm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FilmLive>() {
                    @Override
                    public void call(FilmLive filmLive) {
                        igetLiveView.getLiveFilmSuccess(filmLive);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        igetLiveView.getDataFail(throwable.getMessage());
                    }
                });
    }

    /**
     * TOP250
     * @param igetTop250View
     * @param start
     * @param count
     * @param isLoadMore
     */
    public void getTop250(final IgetTop250View igetTop250View, int start, int count, final boolean isLoadMore){
        doubanApi.getTop250(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Root>() {
                    @Override
                    public void call(Root root) {
                        igetTop250View.getTop250Success(root, isLoadMore);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        igetTop250View.getDataFail(throwable.getMessage());
//                        Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void getFilmDetail(final IgetFilmDetail igetFilmDetail, String id){
        doubanApi.getFilmDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FilmDetail>() {
                    @Override
                    public void call(FilmDetail filmDetail) {
                        igetFilmDetail.getFilmDetailSucces(filmDetail);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        igetFilmDetail.getDataFail();
                    }
                });
    }
}
