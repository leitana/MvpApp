package com.lx.mvpapp.viewinterface.film;

import com.lx.mvpapp.base.IBaseView;
import com.lx.mvpapp.entity.filmdetail.FilmDetail;

/**
 * Created by linxiao on 2019/1/21.
 */

public interface IgetFilmDetail extends IBaseView{
    void getFilmDetailSucces(FilmDetail filmDetail);

    void getDataFail();
}
