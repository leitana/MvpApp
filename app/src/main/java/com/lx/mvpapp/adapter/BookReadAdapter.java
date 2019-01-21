package com.lx.mvpapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.mvpapp.R;
import com.lx.mvpapp.baseadapter.BaseRecyclerPoAdapter;
import com.lx.mvpapp.baseadapter.ViewHolder;
import com.lx.mvpapp.entity.book.Books;
import com.lx.mvpapp.util.ImageLoaderUtils;
import com.lx.mvpapp.util.ScreenUtils;

import java.util.List;

/**
 * Created by linxiao on 2018/11/7.
 */

public class BookReadAdapter extends BaseRecyclerPoAdapter<Books>{
    private Context mContext;

    public BookReadAdapter(Context mContext, int layoutId, List<Books> datas) {
        super(mContext, layoutId, datas);
        this.mContext = mContext;
    }

    @Override
    public void convert(ViewHolder holder, Books book, int position) {
        ImageView iV_film = holder.getView(R.id.iV_film);
        TextView tv_film_name = holder.getView(R.id.tv_film_name);
        TextView tv_film_grade = holder.getView(R.id.tv_film_grade);

        ViewGroup.LayoutParams params=iV_film.getLayoutParams();
        int width= ScreenUtils.getScreenWidthDp(mContext);
        int ivWidth=(width-ScreenUtils.dipToPx(mContext,80))/3;
        params.width=ivWidth;
        double height=(420.0/300.0)*ivWidth;
        params.height=(int)height;
        iV_film.setLayoutParams(params);
        if(!TextUtils.isEmpty(book.getImages().getLarge())) {
            ImageLoaderUtils.display(mContext, iV_film, book.getImages().getLarge());
        }
        if(!TextUtils.isEmpty(book.getRating().getAverage())) {
            tv_film_grade.setText("评分:" + book.getRating().getAverage());
        }else{
            tv_film_grade.setText("暂无评分" );
        }
        if(!TextUtils.isEmpty(book.getTitle())) {
            tv_film_name.setText(book.getTitle());
        }
    }
}
