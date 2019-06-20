package com.ljphawk.arms.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.ljp.bottomtab.BottomTabItemClickListener;
import com.ljp.bottomtab.MainBottomTab;
import com.ljphawk.arms.R;
import com.ljphawk.arms.application.AppManager;
import com.ljphawk.arms.base.BaseActivity;
import com.ljphawk.arms.base.BaseFragment;
import com.ljphawk.arms.base.FragmentFactory;
import com.ljphawk.arms.presenter.MainPresenter;
import com.ljphawk.arms.ui.fragment.SplashFragment;
import com.ljphawk.arms.ui.view.MainView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView, BottomTabItemClickListener {

    private long mFirstPressedTime = 0;
    private MainBottomTab mMainBottomTab;

    @Override
    protected int resView() {
        setTheme(R.style.AppTheme);
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //显示闪屏的fragment
        showSplashFragment();

        mMainBottomTab = findViewById(R.id.bottom_tab);
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter();
    }


    @Override
    protected void initData() {
        //选中第一个图标
        mMainBottomTab.setTabSelect(0);
        //bottomTab的点击事件监听
        mMainBottomTab.setBottomTabItemClickListener(this);
        //显示第一个fragment
        showContentFragment(0);

    }


    private BaseFragment mFragment;

    /**
     * 展示Splash
     */
    private void showSplashFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SplashFragment splashFragment = (SplashFragment) getSupportFragmentManager().findFragmentByTag(SplashFragment.class.getSimpleName());
        if (splashFragment != null) {
            if (splashFragment.isAdded()) {
                transaction.show(splashFragment).commitAllowingStateLoss();
            } else {
                transaction.remove(splashFragment).commitAllowingStateLoss();
                splashFragment = SplashFragment.newInstance();
                transaction.add(R.id.fl_splash_content, splashFragment, SplashFragment.class.getSimpleName()).commitAllowingStateLoss();
            }
        } else {
            splashFragment = SplashFragment.newInstance();
            transaction.add(R.id.fl_splash_content, splashFragment, SplashFragment.class.getSimpleName()).commitAllowingStateLoss();
        }
    }

    @Override
    public void tabItemClick(int position, String name, View view) {
        mMainBottomTab.setTabSelect(position);
        showContentFragment(position);
    }

    /*
    切换fragment
     */
    private void showContentFragment(int position) {
        FragmentFactory fragmentFactory = FragmentFactory.getInstance();
        BaseFragment fragment = fragmentFactory.getFragment(position);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (null == fragment) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (null == mFragment) {
            mFragment = fragment;
        }

        if (mFragment == fragment) {
            if (!fragment.isAdded()) {
                transaction.add(R.id.fl_content, fragment).commitAllowingStateLoss();
            }
        } else {
            if (fragment.isAdded()) {
                transaction.hide(mFragment).show(fragment).commitAllowingStateLoss();
            } else {
                transaction.hide(mFragment).add(R.id.fl_content, fragment).commitAllowingStateLoss();
            }
            mFragment = fragment;
        }
    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mFirstPressedTime < 2000) {
            super.onBackPressed();
            AppManager.getInstance().removeAllActivity();
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mFirstPressedTime = System.currentTimeMillis();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentFactory.getInstance().onDestroy();
    }
}
