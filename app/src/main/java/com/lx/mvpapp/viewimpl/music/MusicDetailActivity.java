package com.lx.mvpapp.viewimpl.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.mvpapp.R;
import com.lx.mvpapp.base.BaseActivity;
import com.lx.mvpapp.entity.filmdetail.FilmDetail;
import com.lx.mvpapp.entity.music.Musics;
import com.lx.mvpapp.other.WebviewActivity;
import com.lx.mvpapp.presenter.DoubanMusicPresenter;
import com.lx.mvpapp.util.ImageLoaderUtils;
import com.lx.mvpapp.util.ThemeUtil;
import com.lx.mvpapp.viewinterface.music.IgetMusicDetail;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by linxiao on 2019/1/21.
 */

public class MusicDetailActivity extends BaseActivity implements IgetMusicDetail{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbarlayout)
    AppBarLayout appbarlayout;
    @BindView(R.id.iv_music)
    ImageView ivMusic;
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;
    @BindView(R.id.tv_music_grade)
    TextView tvMusicGrade;
    @BindView(R.id.tv_music_art)
    TextView tvMusicArt;
    @BindView(R.id.tv_music_publishtime)
    TextView tvMusicPublishtime;
    @BindView(R.id.tv_music_grade_num)
    TextView tvMusicGradeNum;
    @BindView(R.id.tv_listen)
    TextView tvListen;
    @BindView(R.id.tv_more_info)
    TextView tvMoreInfo;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_songs)
    TextView tvSongs;

    private DoubanMusicPresenter doubanMusicPresenter;
    private Musics musics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView(){
        applyKitKatTranslucency();
        toolbar.setTitle("音乐");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.black));
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
            String id = intent.getStringExtra("id");
            doubanMusicPresenter = new DoubanMusicPresenter(this);
            if (!TextUtils.isEmpty(id)) {
                doubanMusicPresenter.getMusicById(this, id);
            }
        }

    }

    @Override
    public void getMusicDetailSucces(Musics music) {
        musics=music;
        toolbar.setTitle(music.getTitle() + music.getAuthor().get(0).getName());
        ImageLoaderUtils.display(mContext,ivMusic, music.getImage());
        tvMusicName.setText(music.getTitle());
        if(music.getAuthor()!=null&&music.getAuthor().size()>0) {
            tvMusicArt.setText("艺术家："+music.getAuthor().get(0).getName());
        }
        if(music.getAttrs()!=null&&music.getAttrs().getPubdate()!=null&&music.getAttrs().getPubdate().size()>0) {
            tvMusicPublishtime.setText(music.getAttrs().getPubdate().get(0));
        }
        if(music.getRating()!=null){
            if(!TextUtils.isEmpty(music.getRating().getAverage()))
                tvMusicGrade.setText(music.getRating().getAverage());
            if(!TextUtils.isEmpty(""+music.getRating().getNumRaters())){
                tvMusicGradeNum.setText(music.getRating().getNumRaters()+"人评");
            }
        }
        if(!TextUtils.isEmpty(music.getSummary())){
            tvDescription.setText(music.getSummary());
        }
        if(music.getAttrs().getTracks()!=null&&music.getAttrs().getTracks().size()>0){
            tvSongs.setText(music.getAttrs().getTracks().get(0));
        }
    }

    @Override
    public void getDataFail() {

    }

    @OnClick({R.id.tv_listen, R.id.tv_more_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_listen:
            case R.id.tv_more_info:

                Intent  intent=new Intent(this, WebviewActivity.class);
                intent.putExtra(WebviewActivity.EXTRA_URL,musics.getAlt());
                startActivity(intent);
                break;
        }
    }
}
