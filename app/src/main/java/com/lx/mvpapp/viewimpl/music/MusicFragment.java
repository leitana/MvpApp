package com.lx.mvpapp.viewimpl.music;

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
import com.lx.mvpapp.viewimpl.book.BookReadFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by linxiao on 2018/11/22.
 */

public class MusicFragment extends BaseFragment {
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_film, null);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public static MusicFragment newInstance(){
        MusicFragment musicFragment = new MusicFragment();
        return musicFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView() {
        mTitles = getResources().getStringArray(R.array.tab_music);

        fragmentList = new ArrayList<>();
        for (int i= 0; i < mTitles.length; i++) {
            fragmentList.add(MusicContentFragment.newInstance(i, mTitles[i]));
        }

        myViewpagerAdapter = new MyViewpagerAdapter(getChildFragmentManager(), mTitles, fragmentList);
        viewpager.setAdapter(myViewpagerAdapter);
        viewpager.setOffscreenPageLimit(mTitles.length);
        tablayout.setSelectedTabIndicatorColor(ThemeUtil.getThemeColor());
        tablayout.setTabTextColors(getResources().getColor(R.color.text_gray_6), ThemeUtil.getThemeColor());
        tablayout.setupWithViewPager(viewpager);
    }
}
