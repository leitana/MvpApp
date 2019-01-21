package com.lx.mvpapp.viewimpl.book;

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
import com.lx.mvpapp.adapter.BookReadAdapter;
import com.lx.mvpapp.base.BaseFragment;
import com.lx.mvpapp.baseadapter.BaseRecyclerPoAdapter;
import com.lx.mvpapp.entity.book.BookRoot;
import com.lx.mvpapp.entity.book.Books;
import com.lx.mvpapp.presenter.DoubanBookPresenter;
import com.lx.mvpapp.util.BookApiUtils;
import com.lx.mvpapp.viewinterface.book.IgetBookView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by linxiao on 2018/11/7.
 */

public class BookReadFragment extends BaseFragment implements IgetBookView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshlayout)
    TwinklingRefreshLayout refreshlayout;
    Unbinder unbinder;

    private DoubanBookPresenter doubanBookPresenter;
    private BookReadAdapter bookReadAdapter;

    private List<String> listTag;
    private List<Books> booksList;
    private int position;
    private String tag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_recyclerview, null);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    public static BookReadFragment newInstance(int position, String title) {
        BookReadFragment bookReadFragment = new BookReadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("position", position);
        bookReadFragment.setArguments(bundle);
        return bookReadFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if(args!=null){
            position=args.getInt("position");
        }
        booksList=new ArrayList<>();
        String[] strTags= BookApiUtils.getApiTag(position);
        listTag= Arrays.asList(strTags);

        tag = BookApiUtils.getRandomTAG(listTag);

        doubanBookPresenter = new DoubanBookPresenter(getContext());

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
//        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        booksList = new ArrayList<>();
        bookReadAdapter = new BookReadAdapter(getContext(), R.layout.item_book_reading, booksList);
        recyclerview.setAdapter(bookReadAdapter);

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
    public void getBookSuccess(BookRoot bookRoot, boolean isLoadMore) {
        if (isLoadMore) {
            refreshlayout.finishLoadmore();
            booksList.addAll(bookRoot.getBooks());
        } else {
            refreshlayout.finishRefreshing();
            booksList.clear();
            booksList.addAll(bookRoot.getBooks());
        }
        bookReadAdapter.notifyDataSetChanged();
        bookReadAdapter.setOnItemClickListener(new BaseRecyclerPoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                Intent intent=new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("id",booksList.get(position).getId());

                startActivity(intent);
            }
        });
    }

    @Override
    public void getDataFile(String message) {
        showToast(message);
    }

    @Override
    public void loadMoreData() {
        doubanBookPresenter.searchBookByTag(this, tag, true);
    }

    @Override
    public void refreshData() {
        doubanBookPresenter.searchBookByTag(this, tag, false);
    }
}
