package com.ljphawk.arms.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljphawk.arms.R;
import com.ljphawk.arms.base.BaseFragment;
import com.ljphawk.arms.base.BasePresenter;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/19 17:25.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class HomeFragment4 extends BaseFragment {
    @Override
    protected View resFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout4,container,false);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData(View view, Bundle savedInstanceState) {

    }
}
