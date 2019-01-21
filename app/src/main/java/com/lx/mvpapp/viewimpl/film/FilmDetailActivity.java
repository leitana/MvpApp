package com.lx.mvpapp.viewimpl.film;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.mvpapp.R;
import com.lx.mvpapp.adapter.CastAdapter;
import com.lx.mvpapp.base.BaseActivity;
import com.lx.mvpapp.baseadapter.BaseRecyclerPoAdapter;
import com.lx.mvpapp.entity.filmdetail.FilmDetail;
import com.lx.mvpapp.entity.filmdetail.FilmPeople;
import com.lx.mvpapp.entity.top250.Casts;
import com.lx.mvpapp.entity.top250.Directors;
import com.lx.mvpapp.other.WebviewActivity;
import com.lx.mvpapp.presenter.DoubanFilmPresenter;
import com.lx.mvpapp.util.ImageLoaderUtils;
import com.lx.mvpapp.util.ThemeUtil;
import com.lx.mvpapp.viewinterface.film.IgetFilmDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by linxiao on 2019/1/21.
 */

public class FilmDetailActivity extends BaseActivity implements IgetFilmDetail{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbarlayout)
    AppBarLayout appbarlayout;
    @BindView(R.id.iv_film)
    ImageView ivFilm;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_rating_num)
    TextView tvRatingNum;
    @BindView(R.id.tv_date_and_film_time)
    TextView tvDateAndFilmTime;
    @BindView(R.id.tv_film_type)
    TextView tvFilmType;
    @BindView(R.id.tv_film_country)
    TextView tvFilmCountry;
    @BindView(R.id.tv_film_name)
    TextView tvFilmName;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.tv_more_info)
    TextView tvMoreInfo;

    public static String EXTRA_ID = "id";
    private String id;//
    private Context mContext;
    private String alt;

    private DoubanFilmPresenter doubanFilmPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_film_detail);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        initData();
    }


    private void initView() {
        applyKitKatTranslucency();
        toolbar.setBackgroundColor(ThemeUtil.getThemeColor());
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra(EXTRA_ID);
        }
        if (!TextUtils.isEmpty(id)) {
            doubanFilmPresenter = new DoubanFilmPresenter(this);
            doubanFilmPresenter.getFilmDetail(this, id);
        }
    }

    @Override
    public void getFilmDetailSucces(FilmDetail filmDetail) {
        if (filmDetail.getImages() != null && filmDetail.getImages().getLarge() != null) {
            ImageLoaderUtils.display(mContext, ivFilm, filmDetail.getImages().getLarge());
        }
        if (!TextUtils.isEmpty(filmDetail.getTitle())) {
            toolbar.setTitle(filmDetail.getTitle());
        }
        if (filmDetail.getRating() != null) {
            tvRating.setText("评分" + filmDetail.getRating().getAverage());
        }
        tvRatingNum.setText(filmDetail.getRatings_count() + "人评分");
        tvDateAndFilmTime.setText(filmDetail.getYear() + "年  出品");
        if (filmDetail.getCountries() != null && filmDetail.getCountries().size() > 0) {
            tvFilmCountry.setText(filmDetail.getCountries().get(0));
        }
        if (filmDetail.getGenres() != null && filmDetail.getGenres().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : filmDetail.getGenres()) {
                stringBuilder.append(s + "/");
            }
            tvFilmType.setText(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));
        }
        tvDescription.setText(filmDetail.getSummary());
        tvFilmName.setText(filmDetail.getOriginal_title() + " [原名]");
        initFilmData(filmDetail);
        CastAdapter castAdapter = new CastAdapter(mContext, R.layout.item_film_people,list);
        LinearLayoutManager fullyLinearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(fullyLinearLayoutManager);
        recyclerview.setAdapter(castAdapter);
        alt=filmDetail.getAlt();
        castAdapter.setOnItemClickListener(new BaseRecyclerPoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                Intent intent=new Intent(FilmDetailActivity.this, WebviewActivity.class);
                String alt=list.get(position).getAlt();
                intent.putExtra(WebviewActivity.EXTRA_URL,alt);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getDataFail() {

    }

    private List<FilmPeople> list = new ArrayList<>();

    private void initFilmData(FilmDetail filmDetail) {

        if (filmDetail.getDirectors() != null && filmDetail.getDirectors().size() > 0) {
            for (int i = 0; i < filmDetail.getDirectors().size(); i++) {
                Directors directors = filmDetail.getDirectors().get(i);
                FilmPeople filmPeople = new FilmPeople();

                filmPeople.setAlt(directors.getAlt());
                filmPeople.setAvatars(directors.getAvatars());
                filmPeople.setId(directors.getId());
                filmPeople.setName(directors.getName());
                filmPeople.setType(1);
                list.add(filmPeople);
            }
        }
        if (filmDetail.getCasts() != null && filmDetail.getCasts().size() > 0) {
            for (int i = 0; i < filmDetail.getCasts().size(); i++) {
                Casts casts = filmDetail.getCasts().get(i);
                FilmPeople filmPeople = new FilmPeople();

                filmPeople.setAlt(casts.getAlt());
                filmPeople.setAvatars(casts.getAvatars());
                filmPeople.setId(casts.getId());
                filmPeople.setName(casts.getName());
                filmPeople.setType(2);
                list.add(filmPeople);
            }
        }

    }

    @OnClick({R.id.iv_film, R.id.tv_more_info})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_film:
            case R.id.tv_more_info:
                intent=new Intent(this, WebviewActivity.class);
                intent.putExtra(WebviewActivity.EXTRA_URL,alt);
                startActivity(intent);
                break;
        }
    }
}
