package com.lx.mvpapp.baseadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by dawn on 16/5/20.
 */
public class BaseListViewHolder {

    private SparseArray<View> mViews;

    private int mPosition;
    private View mConvertView;

    private List<Integer> mCheckedPositionList;

    private BaseListViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        mPosition = position;
        mViews = new SparseArray<View>();

        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);

        mConvertView.setTag(this);
    }

    /**
     * 获取一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static BaseListViewHolder get(Context context, View convertView,
                                         ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new BaseListViewHolder(context, parent, layoutId, position);
        }

        return (BaseListViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getmPosition() {
        return mPosition;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);

        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }

        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public BaseListViewHolder setText(int viewId, String text)
    {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public BaseListViewHolder setImageResource(int viewId, int drawableId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param context
     * @param viewId
     * @param url
     * @return
     */
    public BaseListViewHolder setImageByUrl(Context context, int viewId, String url)
    {
//        ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
//                (ImageView) getView(viewId));

        Glide.with(context).load(url).fitCenter().into((ImageView) getView(viewId));
        return this;
    }

}
