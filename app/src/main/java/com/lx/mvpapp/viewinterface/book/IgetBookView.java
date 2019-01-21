package com.lx.mvpapp.viewinterface.book;

import com.lx.mvpapp.entity.book.BookRoot;

/**
 * Created by linxiao on 2018/11/7.
 */

public interface IgetBookView {
    void getBookSuccess(BookRoot bookRoot, boolean isLoadMore);

    void getDataFile(String message);

    void loadMoreData();

    void refreshData();
}
