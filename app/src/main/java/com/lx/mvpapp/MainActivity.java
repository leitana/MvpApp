package com.lx.mvpapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lx.mvpapp.dagger.DaggerUserBeanComponent;
import com.lx.mvpapp.dagger.ModuleUserBean;
import com.lx.mvpapp.dagger.UserBean;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.text)
    TextView text;
    @Inject
    UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerUserBeanComponent.builder()
                .moduleUserBean(new ModuleUserBean(getClass().getSimpleName()))
                .build().inject(this);

        userBean.setName("2222222");
        text.setText(userBean.getName());
    }
}
