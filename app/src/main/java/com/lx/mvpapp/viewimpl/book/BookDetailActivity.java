package com.lx.mvpapp.viewimpl.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lx.mvpapp.R;
import com.lx.mvpapp.base.BaseActivity;
import com.lx.mvpapp.entity.book.Books;
import com.lx.mvpapp.other.WebviewActivity;
import com.lx.mvpapp.presenter.DoubanBookPresenter;
import com.lx.mvpapp.presenter.DoubanMusicPresenter;
import com.lx.mvpapp.util.ImageLoaderUtils;
import com.lx.mvpapp.viewinterface.book.IgetBookDetail;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by linxiao on 2019/1/21.
 */

public class BookDetailActivity extends BaseActivity implements IgetBookDetail{
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
    @BindView(R.id.tv_book_name)
    TextView tvBookName;
    @BindView(R.id.tv_book_grade)
    TextView tvBookGrade;
    @BindView(R.id.tv_book_art)
    TextView tvBookArt;
    @BindView(R.id.tv_book_publishtime)
    TextView tvBookPublishtime;
    @BindView(R.id.tv_book_publish_address)
    TextView tvBookPublishAddress;
    @BindView(R.id.tv_book_grade_num)
    TextView tvBookGradeNum;
    @BindView(R.id.tv_want_read)
    TextView tvWantRead;
    @BindView(R.id.tv_more_info)
    TextView tvMoreInfo;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_author_description)
    TextView tvAuthorDescription;
    @BindView(R.id.rl_author)
    RelativeLayout rlAuthor;
    @BindView(R.id.tv_chapters)
    TextView tvChapters;

    private DoubanBookPresenter doubanBookPresenter;
    private Books books;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView(){
        applyKitKatTranslucency();
        toolbar.setTitle("书籍");
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
            doubanBookPresenter = new DoubanBookPresenter(this);
            if (!TextUtils.isEmpty(id)) {
                doubanBookPresenter.getBookById(this, id);
            }
        }

    }

    @Override
    public void getBookDetailSucces(Books books) {
        if(books!=null){
            this.books=books;
        }
        if(books==null){
            return;
        }
        toolbar.setTitle(books.getTitle() + "-" + books.getAuthor().get(0));
        if(books.getImages()!=null) {
            ImageLoaderUtils.display(mContext, ivMusic, books.getImages().getLarge());
        }
        if(!TextUtils.isEmpty(books.getTitle())){
            tvBookName.setText(books.getTitle());
        }
        if(books.getAuthor()!=null&&books.getAuthor().size()>0){
            tvBookArt.setText(books.getAuthor().get(0));
        }
        if(!TextUtils.isEmpty(books.getPublisher())){
            tvBookPublishAddress.setText(books.getPublisher());
        }
        if(!TextUtils.isEmpty(books.getPubdate())){
            tvBookPublishtime.setText("出版时间"+books.getPubdate());
        }
        if(!TextUtils.isEmpty(books.getPublisher())){
            tvBookPublishAddress.setText(books.getPublisher());
        }

        if(!TextUtils.isEmpty(books.getSummary())){
            tvDescription.setText(books.getSummary());
        }
        if(!TextUtils.isEmpty(books.getAuthor_intro())){
            tvAuthorDescription.setText(books.getAuthor_intro());
        }
        if(!TextUtils.isEmpty(books.getCatalog())){
            tvChapters.setText(books.getCatalog());
        }
        if(!TextUtils.isEmpty(books.getRating().getAverage())){
            tvBookGrade.setText(books.getRating().getAverage()+"分");
        }
        if(!TextUtils.isEmpty(""+books.getRating().getNumRaters())){
            tvBookGradeNum.setText(books.getRating().getNumRaters()+"人评");
        }
    }

    @Override
    public void getDataFail() {

    }

    @OnClick({R.id.tv_want_read, R.id.tv_more_info, R.id.rl_author})
    public void onClick(View view) {
        Intent  intent;
        switch (view.getId()) {
            case R.id.tv_want_read:
            case R.id.tv_more_info:

                intent=new Intent(this, WebviewActivity.class);
                intent.putExtra(WebviewActivity.EXTRA_URL,books.getAlt());
                startActivity(intent);
                break;
            case R.id.rl_author:
                intent=new Intent(this, WebviewActivity.class);
                intent.putExtra(WebviewActivity.EXTRA_URL,books.getAlt());
                startActivity(intent);
                break;
        }
    }
}
