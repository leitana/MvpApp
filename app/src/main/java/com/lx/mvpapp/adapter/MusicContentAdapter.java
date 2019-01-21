package com.lx.mvpapp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.mvpapp.R;
import com.lx.mvpapp.baseadapter.BaseRecyclerPoAdapter;
import com.lx.mvpapp.baseadapter.ViewHolder;
import com.lx.mvpapp.entity.music.Musics;
import com.lx.mvpapp.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by linxiao on 2018/11/26.
 */

public class MusicContentAdapter extends BaseRecyclerPoAdapter<Musics>{
    public MusicContentAdapter(Context context, int layoutId, List<Musics> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, Musics musics, int position) {
        ImageView iv_music = holder.getView(R.id.iv_music);
        TextView tv_music_name = holder.getView(R.id.tv_music_name);
        TextView tv_music_grade = holder.getView(R.id.tv_music_grade);
        TextView tv_music_art = holder.getView(R.id.tv_music_art);

        ImageLoaderUtils.display(mContext, iv_music, musics.getImage());
        tv_music_name.setText(musics.getTitle());
        tv_music_grade.setText("评分:" + musics.getRating().getAverage());
        tv_music_art.setText(musics.getAuthor().get(0).getName());
    }
}
