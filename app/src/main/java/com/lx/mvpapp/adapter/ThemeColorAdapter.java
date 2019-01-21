package com.lx.mvpapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.lx.mvpapp.R;
import com.lx.mvpapp.baseadapter.BaseRecyclerPoAdapter;
import com.lx.mvpapp.baseadapter.ViewHolder;
import com.lx.mvpapp.entity.home.ThemeColor;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by linxiao on 2018/10/29.
 */

public class ThemeColorAdapter extends BaseRecyclerPoAdapter<ThemeColor>{
    private int position;
    public ThemeColorAdapter(Context context, int layoutId, List<ThemeColor> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, ThemeColor themeColor, int position) {
        CircleImageView circleImageView = holder.getView(R.id.them_color);
        circleImageView.setImageResource(themeColor.getColor());
        ImageView imageView = holder.getView(R.id.choose);
        if (themeColor.isChosen()) {
            imageView.setVisibility(View.VISIBLE);
            this.position = position;
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    public int getPosition() {
        return position;
    }
}
