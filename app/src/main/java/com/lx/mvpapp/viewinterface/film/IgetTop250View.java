package com.lx.mvpapp.viewinterface.film;

import com.lx.mvpapp.base.IBaseView;
import com.lx.mvpapp.entity.top250.Root;

/**
 * Created by linxiao on 2018/10/31.
 */

public interface IgetTop250View extends IBaseView{
    void getTop250Success(Root root, boolean isLoadMore);

    void getDataFail(String fileMessage);

    void refreshData();

    void loadMoreData();
}
