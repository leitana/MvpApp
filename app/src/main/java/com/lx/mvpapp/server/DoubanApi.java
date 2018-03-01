package com.lx.mvpapp.server;

import com.lx.mvpapp.entity.BookBean;
import com.lx.mvpapp.entity.HotMovieBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 11300 on 2018/3/1.
 */

public interface DoubanApi {

    public final static String HOST = "Https://api.douban.com/";

    /**
     * 豆瓣热映电影，每日更新
     */
    @GET("v2/movie/in_theaters")
    Observable<HotMovieBean> getHotMovie();

    /**
     * 获取豆瓣电影top250
     *
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    @GET("v2/movie/top250")
    Observable<HotMovieBean> getMovieTop250(@Query("start") int start, @Query("count") int count);

    /**
     * 豆瓣图书搜索
     * @param name
     * @param tag
     * @param start
     * @param count
     * @return
     */
    @GET("v2/book/search")
    Observable<BookBean> getSearchBook(@Query("q") String name,
                                       @Query("tag") String tag,
                                       @Query("start") int start,
                                       @Query("count") int count);

}
