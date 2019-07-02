package com.ljphawk.arms.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.ljphawk.arms.R;
import com.ljphawk.arms.base.BaseActivity;
import com.ljphawk.arms.base.BasePresenter;


/*
 *@创建者       L_jp
 *@创建时间     2019/7/1 17:35.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class TestActivity extends BaseActivity {
    @Override
    protected int resView() {
        return R.layout.fragment_layout2;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoading();
            }
        }, 1000);
    }
}
