package com.ljphawk.arms.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljphawk.arms.R;
import com.ljphawk.arms.base.BaseFragment;
import com.ljphawk.arms.base.BasePresenter;

public class SplashFragment extends BaseFragment {

    @Override
    public BasePresenter initPresenter() {
        return null;
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

    }
}
