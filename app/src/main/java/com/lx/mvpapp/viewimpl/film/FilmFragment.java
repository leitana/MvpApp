package com.lx.mvpapp.viewimpl.film;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lx.mvpapp.R;
import com.lx.mvpapp.adapter.MyViewpagerAdapter;
import com.lx.mvpapp.base.BaseFragment;
import com.lx.mvpapp.util.ThemeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by linxiao on 2018/10/30.
 */

public class FilmFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.appbarlayout)
    AppBarLayout appbarlayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;

    private String[] mTitles;
    private List<Fragment> fragmentList;
    private MyViewpagerAdapter myViewpagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_film, null);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public static FilmFragment newInstance() {
        FilmFragment filmFragment = new FilmFragment();
        return filmFragment;
    }

    private void initView(){
        mTitles = getResources().getStringArray(R.array.tab_film);

        fragmentList = new ArrayList<>();
        fragmentList.add(FilmLiveFragment.newInstance());
        fragmentList.add(FilmTop250Fragment.newInstance());

        myViewpagerAdapter = new MyViewpagerAdapter(getChildFragmentManager(), mTitles, fragmentList);
        viewpager.setAdapter(myViewpagerAdapter);
        viewpager.setOffscreenPageLimit(3);
        tablayout.setSelectedTabIndicatorColor(ThemeUtil.getThemeColor());
        tablayout.setTabTextColors(getResources().getColor(R.color.text_gray_6), ThemeUtil.getThemeColor());
        tablayout.setupWithViewPager(viewpager);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
