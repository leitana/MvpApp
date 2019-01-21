package com.lx.mvpapp.baseadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dawn on 16/5/20.
 */
public abstract class BaseListViewAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Activity mContext;
    protected List<T> mDataes = new ArrayList<>();

    public BaseListViewAdapter(Activity context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<T> dataes) {
        if (mDataes == null) {
            mDataes = new ArrayList<T>();
        }

        mDataes.clear();

        if (dataes != null && dataes.size() > 0) {
            mDataes.addAll(dataes);
        }

        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mDataes;
    }

    @Override
    public int getCount() {
        if (mDataes == null || mDataes.size() == 0) {
            return 0;
        } else {
            return mDataes.size();
        }
    }

    public T getItem(int position) {
        if (mDataes != null && mDataes.size() > 0 && position < mDataes.size()) {
            return mDataes.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseListViewHolder viewHolder = null;
        if (mDataes == null || mDataes.size() <= 0) {
//            viewHolder = getViewHolder(position, convertView, parent, R.layout.boco_layout_nodata);
//
//            FrameLayout itemLayout = viewHolder.getView(R.id.layout_nodata_lv);
//
//            AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) itemLayout.getLayoutParams();
//
//            Resources resources = mContext.getResources();
//            DisplayMetrics dm = resources.getDisplayMetrics();
//
//
//
////            Rect frame = new Rect();
////            mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
////            int statusBarHeight = frame.top;
//
//            int statusBarHeight = getStatusBarHeight(mContext);
//
//            layoutParams.height = dm.heightPixels - mContext.getResources().getDimensionPixelSize(R.dimen.material_layout_app_bar_height) - 20;
//            layoutParams.width = dm.widthPixels;

        } else {
            viewHolder = initViewHolder(position, convertView, parent);

            convert(viewHolder, position, getItem(position));
        }

        return viewHolder.getConvertView();
    }

    private BaseListViewHolder initViewHolder(int position, View convertView, ViewGroup parent) {
        return BaseListViewHolder.get(mContext, convertView, parent, setLayoutResId(position),
                position);
    }

    public abstract void convert(BaseListViewHolder helper, int position, T item);

    protected abstract int setLayoutResId(int position);

}
