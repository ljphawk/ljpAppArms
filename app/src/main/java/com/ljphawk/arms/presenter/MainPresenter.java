package com.ljphawk.arms.presenter;


import android.support.v4.app.FragmentTransaction;

import com.ljphawk.arms.R;
import com.ljphawk.arms.base.BasePresenter;
import com.ljphawk.arms.ui.activity.MainActivity;
import com.ljphawk.arms.ui.fragment.SplashFragment;
import com.ljphawk.arms.ui.view.MainView;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/14 17:44.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class MainPresenter extends BasePresenter<MainView> {

    private SplashFragment mSplashFragment;

    /**
     * 展示Splash
     */
    public void showSplashFragment() {

        FragmentTransaction transaction = ((MainActivity) mvpView).getSupportFragmentManager().beginTransaction();
        mSplashFragment = (SplashFragment) ((MainActivity) mvpView).getSupportFragmentManager().findFragmentByTag(SplashFragment.class.getSimpleName());
        if (mSplashFragment != null) {
            if (mSplashFragment.isAdded()) {
                transaction.show(mSplashFragment).commitAllowingStateLoss();
            } else {
                transaction.remove(mSplashFragment).commitAllowingStateLoss();
                mSplashFragment = SplashFragment.newInstance();
                transaction.add(R.id.fl_splash_content, mSplashFragment, SplashFragment.class.getSimpleName()).commitAllowingStateLoss();
            }
        } else {
            mSplashFragment = SplashFragment.newInstance();
            transaction.add(R.id.fl_splash_content, mSplashFragment, SplashFragment.class.getSimpleName()).commitAllowingStateLoss();
        }
        mSplashFragment.setOnSplashListener((time, totalTime) -> {
            //todo
        });
    }
}
