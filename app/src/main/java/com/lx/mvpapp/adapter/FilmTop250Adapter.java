package com.lx.mvpapp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.mvpapp.R;
import com.lx.mvpapp.baseadapter.BaseRecyclerPoAdapter;
import com.lx.mvpapp.baseadapter.ViewHolder;
import com.lx.mvpapp.entity.filmusbox.Subject;
import com.lx.mvpapp.entity.top250.Root;
import com.lx.mvpapp.entity.top250.Subjects;
import com.lx.mvpapp.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by linxiao on 2018/11/2.
 */

public class FilmTop250Adapter extends BaseRecyclerPoAdapter<Subjects>{
    private Context mContext;
    public FilmTop250Adapter(Context context, int layoutId, List<Subjects> datas) {
        super(context, layoutId, datas);
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, Subjects subjects, int position) {
        TextView tv_rank = holder.getView(R.id.tv_rank);
        ImageView iv_film = holder.getView(R.id.iv_film);
        TextView tv_film = holder.getView(R.id.tv_film);
        TextView tv_film_english = holder.getView(R.id.tv_film_english);
        TextView tv_film_grade = holder.getView(R.id.tv_film_grade);

        tv_rank.setText(position + 1 + "");
        ImageLoaderUtils.display(mContext, iv_film, subjects.getImages().getLarge());
        tv_film.setText(subjects.getTitle());
        tv_film_english.setText(subjects.getOriginal_title());
        tv_film_grade.setText("评分:" + subjects.getRating().getAverage());
    }
}
