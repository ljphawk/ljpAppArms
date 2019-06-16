package com.ljphawk.arms.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljphawk.arms.R;
import com.ljphawk.arms.base.BaseFragment;
import com.ljphawk.arms.listener.OnSplashListener;
import com.ljphawk.arms.presenter.SplashPresenter;
import com.ljphawk.arms.ui.view.SplashView;

public class SplashFragment extends BaseFragment<SplashView, SplashPresenter>implements SplashView {

    private OnSplashListener mOnSplashListener;

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
        presenter.finish();
    }


    public void setOnSplashListener(OnSplashListener onSplashListener){
        this.mOnSplashListener = onSplashListener;
    }
}
