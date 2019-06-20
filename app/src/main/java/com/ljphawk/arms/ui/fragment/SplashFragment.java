package com.ljphawk.arms.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljphawk.arms.R;
import com.ljphawk.arms.base.BaseFragment;
import com.ljphawk.arms.presenter.SplashPresenter;
import com.ljphawk.arms.ui.view.SplashView;

public class SplashFragment extends BaseFragment<SplashPresenter> implements SplashView {


    public static SplashFragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public SplashPresenter initPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected View resFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData(View view, Bundle savedInstanceState) {
        new Handler().postDelayed(this::finish, 2000);
    }


    /**
     * 关闭当前页面
     * @param
     */
    private void finish() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            Fragment fragment = fragmentManager.findFragmentByTag(this.getClass().getSimpleName());
            if (fragment != null) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out)
                        .remove(fragment)
                        .commitAllowingStateLoss();
            }
        }
    }
}
