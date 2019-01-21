package com.lx.mvpapp.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.mvpapp.R;
import com.lx.mvpapp.baseadapter.BaseRecyclerPoAdapter;
import com.lx.mvpapp.baseadapter.ViewHolder;
import com.lx.mvpapp.entity.filmdetail.FilmPeople;
import com.lx.mvpapp.util.ImageLoaderUtils;

import java.util.List;

/**
 * Created by linxiao on 2019/1/21.
 */

public class CastAdapter extends BaseRecyclerPoAdapter<FilmPeople>{

    private Context mContext;

    public CastAdapter(Context context, int layoutId, List<FilmPeople> datas) {
        super(context, layoutId, datas);
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, FilmPeople filmPeople, int position) {
        ImageView ivAvatar = holder.getView(R.id.iv_avatar);
        TextView tvNameChinease = holder.getView(R.id.tv_name_chinease);
        TextView tvNameEnglish = holder.getView(R.id.tv_name_english);
        ImageLoaderUtils.display(mContext, ivAvatar, filmPeople.getAvatars().getLarge());
        tvNameChinease.setText(filmPeople.getName());
        if(filmPeople.getType()==1){
            tvNameEnglish.setText("［导演］");
        }else{
            tvNameEnglish.setText("［演员］");
        }
    }
}
