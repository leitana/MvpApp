package com.lx.mvpapp.presenter;

import android.content.Context;

import com.lx.mvpapp.base.BasePresenter;
import com.lx.mvpapp.entity.book.BookRoot;
import com.lx.mvpapp.entity.book.Books;
import com.lx.mvpapp.viewinterface.book.IgetBookDetail;
import com.lx.mvpapp.viewinterface.book.IgetBookView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by linxiao on 2018/11/7.
 */

public class DoubanBookPresenter extends BasePresenter{

    public DoubanBookPresenter(Context mContext) {
        super(mContext);
    }

    /**
     * 根据Tag获取book
     * @param igetBookView
     * @param TAG
     * @param isLoadMore
     */
    public void searchBookByTag(final IgetBookView igetBookView, String TAG, final boolean isLoadMore) {
        doubanApi.searchBookByTag(TAG)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BookRoot>() {
                    @Override
                    public void call(BookRoot bookRoot) {
                        igetBookView.getBookSuccess(bookRoot, isLoadMore);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        igetBookView.getDataFile(throwable.getMessage());
                    }
                });
    }

    public void getBookById(final IgetBookDetail igetBookDetail, String id){
        doubanApi.getBookDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Books>() {
                    @Override
                    public void call(Books books) {
                        igetBookDetail.getBookDetailSucces(books);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        igetBookDetail.getDataFail();
                    }
                });
    }
}
