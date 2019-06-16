package com.ljphawk.arms.presenter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.ljphawk.arms.R;
import com.ljphawk.arms.base.BasePresenter;
import com.ljphawk.arms.ui.fragment.SplashFragment;
import com.ljphawk.arms.ui.view.SplashView;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/16 14:42.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class SplashPresenter extends BasePresenter<SplashView> {


    private boolean mIsFinish = false;
    /**
     * 关闭当前页面
     * @param
     */
    public void finish() {
        FragmentManager fragmentManager = ((SplashFragment) mvpView).getFragmentManager();
        if (fragmentManager != null) {
            Fragment fragment = fragmentManager.findFragmentByTag(mvpView.getClass().getSimpleName());
            if (fragment != null) {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out)
                        .remove(fragment)
                        .commitAllowingStateLoss();
            }
        }
        mIsFinish = true;
    }
}
