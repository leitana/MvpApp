package com.lx.mvpapp.viewinterface.music;

import com.lx.mvpapp.entity.music.MusicRoot;

/**
 * Created by linxiao on 2018/11/22.
 */

public interface IgetMusicView {
    void getMusicSuccess(MusicRoot musicRoot, boolean isLoadMore);

    void getDataFail(String string);

    void loadMoreData();

    void refreshData();
}
