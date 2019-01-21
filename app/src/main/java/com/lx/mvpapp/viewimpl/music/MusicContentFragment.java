package com.lx.mvpapp.viewimpl.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lx.mvpapp.R;
import com.lx.mvpapp.adapter.MusicContentAdapter;
import com.lx.mvpapp.api.MusicApiUtils;
import com.lx.mvpapp.base.BaseFragment;
import com.lx.mvpapp.baseadapter.BaseRecyclerPoAdapter;
import com.lx.mvpapp.entity.book.Books;
import com.lx.mvpapp.entity.music.MusicRoot;
import com.lx.mvpapp.entity.music.Musics;
import com.lx.mvpapp.presenter.DoubanMusicPresenter;
import com.lx.mvpapp.util.BookApiUtils;
import com.lx.mvpapp.viewinterface.music.IgetMusicView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by linxiao on 2018/11/22.
 */

public class MusicContentFragment extends BaseFragment implements IgetMusicView{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout refreshlayout;
    Unbinder unbinder;

    private int position;
    private String tag;
    private List<String> listTag;
    private List<Musics> musicsList;

    private DoubanMusicPresenter doubanMusicPresenter;
    private MusicContentAdapter musicContentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_recyclerview, null);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
        }

        String[] tags = MusicApiUtils.getApiTag(position);
        listTag = Arrays.asList(tags);
        tag = MusicApiUtils.getRandomTAG(listTag);

        doubanMusicPresenter = new DoubanMusicPresenter(getContext());

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        musicsList = new ArrayList<>();
        musicContentAdapter = new MusicContentAdapter(getContext(), R.layout.item_music, musicsList);
        recyclerview.setAdapter(musicContentAdapter);

        SinaRefreshView sinaRefreshView = new SinaRefreshView(getActivity());
        sinaRefreshView.setArrowResource(R.mipmap.ic_load_arrow);
        refreshlayout.setHeaderView(sinaRefreshView);
        refreshlayout.setBottomView(new LoadingView(getActivity()));
        refreshlayout.setEnableLoadmore(true);
        refreshlayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refreshData();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                loadMoreData();
            }
        });
        refreshlayout.startRefresh();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static MusicContentFragment newInstance(int position, String title){
        MusicContentFragment musicContentFragment = new MusicContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("title", title);
        musicContentFragment.setArguments(bundle);
        return musicContentFragment;
    }

    @Override
    public void getMusicSuccess(MusicRoot musicRoot, boolean isLoadMore) {
        if (isLoadMore) {
            refreshlayout.finishRefreshing();
            musicsList.addAll(musicRoot.getMusics());
        } else {
            refreshlayout.finishRefreshing();
            musicsList.clear();
            musicsList.addAll(musicRoot.getMusics());
        }
        musicContentAdapter.notifyDataSetChanged();
        musicContentAdapter.setOnItemClickListener(new BaseRecyclerPoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                Intent intent=new Intent(getActivity(),MusicDetailActivity.class);
                intent.putExtra("id",musicsList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void getDataFail(String string) {
        showToast(string);
        refreshlayout.finishRefreshing();
    }

    @Override
    public void loadMoreData() {
        doubanMusicPresenter.searchMusicByTag(this, tag, true);
    }

    @Override
    public void refreshData() {
        doubanMusicPresenter.searchMusicByTag(this, tag, false);
    }
}
