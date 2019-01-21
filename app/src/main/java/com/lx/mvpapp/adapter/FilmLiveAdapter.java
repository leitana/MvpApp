package com.lx.mvpapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.mvpapp.R;
import com.lx.mvpapp.baseadapter.BaseRecyclerPoAdapter;
import com.lx.mvpapp.baseadapter.ViewHolder;
import com.lx.mvpapp.entity.top250.Subjects;
import com.lx.mvpapp.util.ImageLoaderUtils;
import com.lx.mvpapp.util.SizeUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by linxiao on 2018/11/6.
 */

public class FilmLiveAdapter extends BaseRecyclerPoAdapter<Subjects>{
    private Context mContext;

    public FilmLiveAdapter(Context context, int layoutId, List<Subjects> datas) {
        super(context, layoutId, datas);
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, Subjects subjects, int position) {
        ImageView iV_film = holder.getView(R.id.iV_film);
        TextView tv_film_name = holder.getView(R.id.tv_film_name);
        TextView tv_film_grade = holder.getView(R.id.tv_film_grade);

        ViewGroup.LayoutParams params = iV_film.getLayoutParams();
        params.height = SizeUtils.dp2px(mContext, getRandom(150, 250));
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        iV_film.setLayoutParams(params);
        ImageLoaderUtils.display(mContext, iV_film, subjects.getImages().getLarge());
        tv_film_name.setText(subjects.getTitle());
        if(!TextUtils.isEmpty(""+subjects.getRating().getAverage())) {
            tv_film_grade.setText("评分:"+String.valueOf(subjects.getRating().getAverage()));
        }else{
            tv_film_grade.setText("暂无评分");
        }
    }

    public static int getRandom(int min, int max){
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;

    }
}
