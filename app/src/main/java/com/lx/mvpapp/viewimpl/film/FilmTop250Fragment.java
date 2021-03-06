package com.lx.mvpapp.viewimpl.film;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
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
import com.lx.mvpapp.adapter.FilmTop250Adapter;
import com.lx.mvpapp.base.BaseFragment;
import com.lx.mvpapp.baseadapter.BaseRecyclerPoAdapter;
import com.lx.mvpapp.entity.top250.Root;
import com.lx.mvpapp.entity.top250.Subjects;
import com.lx.mvpapp.presenter.DoubanFilmPresenter;
import com.lx.mvpapp.viewinterface.film.IgetTop250View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by linxiao on 2018/10/31.
 */

public class FilmTop250Fragment extends BaseFragment implements IgetTop250View{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout refreshlayout;
    Unbinder unbinder;

    private DoubanFilmPresenter doubanFilmPresenter;
    private FilmTop250Adapter filmTop250Adapter;

    private int pageCount;
    private final int PAGE_SIZE=10;
    private List<Subjects> subjects;

    public static FilmTop250Fragment newInstance(){
        FilmTop250Fragment filmTop250Fragment = new FilmTop250Fragment();
        return filmTop250Fragment;
    }

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

        doubanFilmPresenter = new DoubanFilmPresenter(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

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

    @Override
    public void getTop250Success(Root root, boolean isLoadMore) {
        if (isLoadMore) {
            subjects.addAll(root.getSubjects());
//            refreshlayout.onFinishLoadMore();
            refreshlayout.finishLoadmore();
        } else {
            refreshlayout.finishRefreshing();
            subjects = new ArrayList<>();
            subjects = root.getSubjects();
            filmTop250Adapter = new FilmTop250Adapter(getActivity(), R.layout.item_film250, subjects);
            recyclerview.setAdapter(filmTop250Adapter);
        }
        filmTop250Adapter.notifyDataSetChanged();
        filmTop250Adapter.setOnItemClickListener(new BaseRecyclerPoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                Intent intent=new Intent(getActivity(),FilmDetailActivity.class);
                intent.putExtra(FilmDetailActivity.EXTRA_ID,subjects.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void getDataFail(String str) {
        showToast(str);
    }

    @Override
    public void refreshData() {
        doubanFilmPresenter.getTop250(this, pageCount * PAGE_SIZE, PAGE_SIZE, false);
    }

    @Override
    public void loadMoreData() {
        pageCount++;
        doubanFilmPresenter.getTop250(this, pageCount * PAGE_SIZE, PAGE_SIZE, true);
    }

}
