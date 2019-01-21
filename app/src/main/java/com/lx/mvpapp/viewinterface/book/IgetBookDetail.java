package com.lx.mvpapp.viewinterface.book;

import com.lx.mvpapp.base.IBaseView;
import com.lx.mvpapp.entity.book.Books;
import com.lx.mvpapp.entity.filmdetail.FilmDetail;

/**
 * Created by linxiao on 2019/1/21.
 */

public interface IgetBookDetail extends IBaseView{
    void getBookDetailSucces(Books books);

    void getDataFail();
}
