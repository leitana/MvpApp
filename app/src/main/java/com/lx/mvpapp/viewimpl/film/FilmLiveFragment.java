package com.lx.mvpapp.viewimpl.film;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.lx.mvpapp.R;
import com.lx.mvpapp.adapter.FilmLiveAdapter;
import com.lx.mvpapp.base.BaseFragment;
import com.lx.mvpapp.baseadapter.BaseRecyclerPoAdapter;
import com.lx.mvpapp.entity.filmlive.FilmLive;
import com.lx.mvpapp.entity.top250.Subjects;
import com.lx.mvpapp.presenter.DoubanFilmPresenter;
import com.lx.mvpapp.viewinterface.film.IgetLiveView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by linxiao on 2018/11/6.
 */

public class FilmLiveFragment extends BaseFragment implements IgetLiveView{
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout refreshlayout;
    Unbinder unbinder;

    private DoubanFilmPresenter doubanFilmPresenter;
    private FilmLiveAdapter filmLiveAdapter;
    private List<Subjects> subjects;

    public static FilmLiveFragment newInstance(){
        FilmLiveFragment filmLiveFragment = new FilmLiveFragment();
        return filmLiveFragment;
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
        doubanFilmPresenter = new DoubanFilmPresenter(getContext());

        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        subjects = new ArrayList<>();
        filmLiveAdapter = new FilmLiveAdapter(getContext(), R.layout.item_film_live, subjects);
        recyclerview.setAdapter(filmLiveAdapter);

        SinaRefreshView sinaRefreshView = new SinaRefreshView(getActivity());
        sinaRefreshView.setArrowResource(R.mipmap.ic_load_arrow);
        refreshlayout.setHeaderView(sinaRefreshView);
        refreshlayout.setBottomView(new LoadingView(getActivity()));
        refreshlayout.setEnableLoadmore(false);
        refreshlayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                refreshData();
            }
        });
        refreshlayout.startRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void refreshData() {
        doubanFilmPresenter.getFilmLive(this);
    }

    private void loadMoreData() {

    }

    @Override
    public void getLiveFilmSuccess(FilmLive filmLive) {
        refreshlayout.finishRefreshing();
        subjects.clear();
        subjects.addAll(filmLive.getSubjects());
        filmLiveAdapter.notifyDataSetChanged();
        filmLiveAdapter.setOnItemClickListener(new BaseRecyclerPoAdapter.OnItemClickListener() {
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
}
