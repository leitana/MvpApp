package com.lx.mvpapp.other;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.lx.mvpapp.MainActivity;
import com.lx.mvpapp.R;
import com.lx.mvpapp.base.BaseActivity;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by linxiao on 2018/10/24.
 */

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.image)
    ImageView image;

    private static final int ANIMATION_TIME = 2000;

    private static final float SCALE_END = 1.13F;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        ButterKnife.bind(this);
        setTranslucentStatus(true);

        image.setImageResource(R.drawable.splash2);

        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startAnim();
                    }
                });

    }

    private void startAnim(){
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(image, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(image, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_TIME).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
