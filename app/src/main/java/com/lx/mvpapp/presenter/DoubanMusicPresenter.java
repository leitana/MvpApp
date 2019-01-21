package com.lx.mvpapp.presenter;

import android.content.Context;

import com.lx.mvpapp.base.BasePresenter;
import com.lx.mvpapp.entity.book.BookRoot;
import com.lx.mvpapp.entity.filmdetail.FilmDetail;
import com.lx.mvpapp.entity.music.MusicRoot;
import com.lx.mvpapp.entity.music.Musics;
import com.lx.mvpapp.viewinterface.music.IgetMusicDetail;
import com.lx.mvpapp.viewinterface.music.IgetMusicView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linxiao on 2018/11/22.
 */

public class DoubanMusicPresenter extends BasePresenter{
    public DoubanMusicPresenter(Context mContext) {
        super(mContext);
    }

    public void searchMusicByTag(final IgetMusicView igetMusicView, String Tag, final boolean isLoadMore){
        doubanApi.searchMusicByTag(Tag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MusicRoot>() {
                    @Override
                    public void call(MusicRoot musicRoot) {
                        igetMusicView.getMusicSuccess(musicRoot, isLoadMore);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        igetMusicView.getDataFail(throwable.getMessage());
                    }
                });
    }

    public void getMusicById(final IgetMusicDetail igetMusicDetail, String id){
        doubanApi.getMusicDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Musics>() {
                    @Override
                    public void call(Musics musics) {
                        igetMusicDetail.getMusicDetailSucces(musics);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        igetMusicDetail.getDataFail();
                    }
                });

    }
}
