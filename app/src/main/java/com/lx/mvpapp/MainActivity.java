package com.lx.mvpapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.lx.mvpapp.adapter.ThemeColorAdapter;
import com.lx.mvpapp.base.ActivityCollector;
import com.lx.mvpapp.base.BaseActivity;
import com.lx.mvpapp.baseadapter.BaseRecyclerPoAdapter;
import com.lx.mvpapp.entity.home.ThemeColor;
import com.lx.mvpapp.util.ThemeUtil;
import com.lx.mvpapp.viewimpl.book.BookFragment;
import com.lx.mvpapp.viewimpl.film.FilmFragment;
import com.lx.mvpapp.viewimpl.music.MusicFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

//import com.lx.mvpapp.dagger.DaggerUserBeanComponent;

public class MainActivity extends BaseActivity {

    @BindView(R.id.appbarlayout)
    AppBarLayout appbarlayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.coordinatorlayout)
    LinearLayout coordinatorlayout;
    @BindView(R.id.id_navigationview)
    NavigationView idNavigationview;
    @BindView(R.id.drawerlayout_home)
    DrawerLayout drawerlayoutHome;


    private MyPageAdapter myPageAdapter;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, drawerlayoutHome, ThemeUtil.getThemeColor());
//        StatusBarUtil.setColor(MainActivity.this, ThemeUtil.getThemeColor());
//        StatusBarUtil.setTransparent(this);
        initTheme();
        initChangeTheme();
        initDrawable();
        initViewPager();
    }

    private void initTheme() {
//        ThemeUtil.setThemeColor(getResources().getColor(R.color.theme_red));
        applyKitKatTranslucency();//设置状态栏
        navigation.setItemIconTintList(ThemeUtil.getNaviItemIconTinkList());
        navigation.setItemTextColor(ThemeUtil.getNaviItemIconTinkList());
    }

    private MenuItem menuItem;
    private void initViewPager(){
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        viewpager.setAdapter(myPageAdapter);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar.setTitle("电影");

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (menuItem != null) {
//                    menuItem.setChecked(false);
//                } else {
//                    navigation.getMenu().getItem(0).setChecked(false);
//                }
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
                switch (position) {
                    case 0:
                        toolbar.setTitle("电影");
                        break;
                    case 1:
                        toolbar.setTitle("书籍");
                        break;
                    case 2:
                        toolbar.setTitle("音乐");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initDrawable(){
        // 设置Drawerlayout开关指示器，即Toolbar最左边的那个icon
        ActionBarDrawerToggle mActionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerlayoutHome, toolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        drawerlayoutHome.setDrawerListener(mActionBarDrawerToggle);

        toolbar.setBackgroundColor(ThemeUtil.getThemeColor());
//        toolbar.setNavigationIcon(R.mipmap.back);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        idNavigationview.inflateHeaderView(R.layout.layout_drawable);
        idNavigationview.inflateMenu(R.menu.menu_nav);
        idNavigationview.setItemIconTintList(ThemeUtil.getNaviItemIconTinkList());
        idNavigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_menu_home:
                        showToast("11111");
                        break;
                    case R.id.nav_menu_recommend:
                        break;
                    case R.id.nav_menu_theme:
                        View view = getLayoutInflater().inflate(R.layout.dialog_theme, null, false);
                        RecyclerView recyclerView = view.findViewById(R.id.theme_recycler_view);
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                        recyclerView.setAdapter(themeColorAdapter);
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("主题选择")
                                .setView(view)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ThemeUtil.setThemeColor(getResources().getColor(themeColorList.get(themeColorAdapter.getPosition()).getColor()));
                                        ThemeUtil.setThemePosition(themeColorAdapter.getPosition());
                                        Observable.timer(10, TimeUnit.MILLISECONDS)
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Action1<Long>() {
                                                    @Override
                                                    public void call(Long aLong) {
                                                        ActivityCollector.getInstance().refreshAllActivity();
                                                    }
                                                });
                                    }
                                })
                                .show();

                        break;
                    case R.id.nav_menu_feedback:
                        break;
                    case R.id.nav_menu_setting:
                        break;
                    case R.id.nav_menu_categories:
                        break;
                }
//                item.setChecked(true);
                drawerlayoutHome.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    viewpager.setCurrentItem(0);
                    return true;
                case R.id.navigation_book:
                    viewpager.setCurrentItem(1);
                    return true;
                case R.id.navigation_music:
                    viewpager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };

    private ThemeColorAdapter themeColorAdapter;
    private ArrayList<ThemeColor> themeColorList = new ArrayList<>();
    private void initChangeTheme(){
        themeColorList.add(new ThemeColor(R.color.theme_red_base));
        themeColorList.add(new ThemeColor(R.color.theme_blue));
        themeColorList.add(new ThemeColor(R.color.theme_blue_light));
        themeColorList.add(new ThemeColor(R.color.theme_balck));
        themeColorList.add(new ThemeColor(R.color.theme_teal));
        themeColorList.add(new ThemeColor(R.color.theme_brown));
        themeColorList.add(new ThemeColor(R.color.theme_green));
        themeColorList.add(new ThemeColor(R.color.theme_red));
        themeColorAdapter = new ThemeColorAdapter(mContext, R.layout.item_theme_color, themeColorList);
        themeColorAdapter.setOnItemClickListener(new BaseRecyclerPoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int position) {
                for (ThemeColor themeColor : themeColorList) {
                    themeColor.setChosen(false);
                }
                themeColorList.get(position).setChosen(true);
                themeColorAdapter.notifyDataSetChanged();
            }
        });
    }

    private class MyPageAdapter extends FragmentPagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FilmFragment.newInstance();
                case 1:
                    return BookFragment.newInstance();
                case 2:
                    return MusicFragment.newInstance();
                default:
                    return FilmFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
