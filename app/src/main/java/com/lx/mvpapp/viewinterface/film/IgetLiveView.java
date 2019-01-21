package com.lx.mvpapp.viewinterface.film;

import com.lx.mvpapp.entity.filmlive.FilmLive;

/**
 * Created by linxiao on 2018/11/6.
 */

public interface IgetLiveView {
    void getLiveFilmSuccess(FilmLive filmLive);

    void getDataFail(String str);
}
