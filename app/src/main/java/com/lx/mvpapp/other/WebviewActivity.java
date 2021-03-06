package com.lx.mvpapp.other;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lx.mvpapp.R;
import com.lx.mvpapp.base.BaseActivity;
import com.lx.mvpapp.util.ThemeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by linxiao on 2019/1/21.
 */

public class WebviewActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbarlayout)
    AppBarLayout appbarlayout;
    @BindView(R.id.webview)
    WebView webview;

    private String url;
    public static String EXTRA_URL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        applyKitKatTranslucency();
        initData();
        initView();
    }

    private void initData(){
        Intent intent=getIntent();
        if(intent!=null){
            url=  intent.getStringExtra(EXTRA_URL);
        }
    }

    private void initView(){
        toolbar.setBackgroundColor(ThemeUtil.getThemeColor());
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);// 支持JS
        //settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
        //settings.setUseWideViewPort(true);// 支持双击放大缩小
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


            }

            /**
             * 所有跳转的链接都在此方法中回调
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {

                toolbar.setTitle(title);
                super.onReceivedTitle(view, title);
            }
        });
        webview.loadUrl(url);
    }
}
