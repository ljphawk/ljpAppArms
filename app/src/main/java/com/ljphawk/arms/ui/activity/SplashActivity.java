package com.ljphawk.arms.ui.activity;

import android.content.Intent;
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
        try {
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                //完美解决：APP下载安装后，点击“直接打开”，启动应用后，按下HOME键，再次点击桌面上的应用，会重启一个新的应用问题
                finish();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Handler().postDelayed(() -> MainActivity.startActivity(mContext), 2000);
    }
}
