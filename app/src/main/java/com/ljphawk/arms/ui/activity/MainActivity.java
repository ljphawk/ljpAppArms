package com.ljphawk.arms.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.ljphawk.arms.R;
import com.ljphawk.arms.application.ActivityManager;
import com.ljphawk.arms.base.BaseActivity;
import com.ljphawk.arms.presenter.MainPresenter;

public class MainActivity extends BaseActivity {

    private long mFirstPressedTime = 0;

    @Override
    protected int resView() {
        setTheme(R.style.AppTheme);
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public MainPresenter initPresenter() {
        return null;
    }


    @Override
    protected void initData() {

    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mFirstPressedTime < 2000) {
            super.onBackPressed();
            ActivityManager.getInstance().removeAllActivity();
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mFirstPressedTime = System.currentTimeMillis();
        }
    }

}
