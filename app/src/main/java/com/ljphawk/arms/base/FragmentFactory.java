package com.ljphawk.arms.base;

import com.ljphawk.arms.ui.fragment.HomeFragment1;
import com.ljphawk.arms.ui.fragment.HomeFragment2;
import com.ljphawk.arms.ui.fragment.HomeFragment3;
import com.ljphawk.arms.ui.fragment.HomeFragment4;
import com.ljphawk.arms.utils.ToastUtils;


public class FragmentFactory {


    private static FragmentFactory sFragmentFactory;
    private HomeFragment1 mHomeFragment1;
    private HomeFragment2 mHomeFragment2;
    private HomeFragment3 mHomeFragment3;
    private HomeFragment4 mHomeFragment4;

    public static FragmentFactory getInstance() {
        if (sFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                if (sFragmentFactory == null) {
                    sFragmentFactory = new FragmentFactory();
                }
            }
        }
        return sFragmentFactory;
    }

    public BaseFragment getFragment(int position) {
        switch (position) {
            case 0:
                if (mHomeFragment1 == null) {
                    mHomeFragment1 = new HomeFragment1();
                }
                return mHomeFragment1;
            case 1:
                if (mHomeFragment2 == null) {
                    mHomeFragment2 = new HomeFragment2();
                }
                return mHomeFragment2;
            case 2:
                if (mHomeFragment3 == null) {
                    mHomeFragment3 = new HomeFragment3();
                }
                return mHomeFragment3;
            case 3:
                if (mHomeFragment4 == null) {
                    mHomeFragment4 = new HomeFragment4();
                }
                return mHomeFragment4;
        }
        return null;
    }

    public void onDestroy() {
        mHomeFragment1 = null;
        mHomeFragment2 = null;
        mHomeFragment3 = null;
        mHomeFragment4 = null;
        ToastUtils.destroy();
    }
}
