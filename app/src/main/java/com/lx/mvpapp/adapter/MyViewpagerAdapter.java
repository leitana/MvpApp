package com.lx.mvpapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by linxiao on 2018/11/6.
 */

public class MyViewpagerAdapter extends FragmentStatePagerAdapter{
    private String[] mTitles;
    private List<Fragment> fragmentList;

    public MyViewpagerAdapter(FragmentManager fm, String[] mTitles, List<Fragment> fragmentList) {
        super(fm);
        this.mTitles = mTitles;
        this.fragmentList = fragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
