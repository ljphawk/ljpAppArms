package com.ljphawk.arms.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.ljphawk.arms.R;
import com.ljphawk.arms.base.BaseActivity;
import com.ljphawk.arms.presenter.SplashPresenter;
import com.ljphawk.arms.ui.view.SplashView;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashView {


    @Override
    protected int resView() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        new Handler().postDelayed(() -> MainActivity.startActivity(mContext), 2000);
    }
}
