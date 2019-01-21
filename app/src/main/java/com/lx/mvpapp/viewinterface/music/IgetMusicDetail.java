package com.lx.mvpapp.viewinterface.music;

import com.lx.mvpapp.base.IBaseView;
import com.lx.mvpapp.entity.filmdetail.FilmDetail;
import com.lx.mvpapp.entity.music.Musics;

/**
 * Created by linxiao on 2019/1/21.
 */

public interface IgetMusicDetail extends IBaseView{
    void getMusicDetailSucces(Musics music);

    void getDataFail();
}
