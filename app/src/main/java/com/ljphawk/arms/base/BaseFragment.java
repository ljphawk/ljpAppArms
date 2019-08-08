package com.ljphawk.arms.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ljphawk.arms.application.MyApplication;
import com.ljphawk.arms.http.RequestUrlUtils;
import com.ljphawk.arms.utils.StatusManager;
import com.ljphawk.arms.utils.ToastUtils;
import com.ljphawk.arms.widget.LoadHintLayout;

import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/*
 *@创建者       L_jp
 *@创建时间     2018/7/5 10:12.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */


public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    public Context mContext;
    public static String TAG = BaseActivity.class.getSimpleName();
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected CompositeDisposable disposables;
    protected P presenter;
    private final StatusManager mStatusManager = new StatusManager();
    // 根布局
    protected View mRootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if (null != mContext) {
            TAG = mContext.getClass().getSimpleName();
        }

        disposables = new CompositeDisposable();

        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            android.support.v4.app.FragmentTransaction ft = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commitAllowingStateLoss();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = resFragmentView(inflater, container, savedInstanceState);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = initPresenter();
        if (null != presenter) {
            presenter.attach(this);
        }
        initView(view, savedInstanceState);
        initData(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected abstract View resFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    //初始化presenter
    public abstract P initPresenter();

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract void initData(View view, Bundle savedInstanceState);


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void showToast(String content) {
        ToastUtils.showToast(content);
    }

    @Override
    public void showLoading() {
        mStatusManager.showLoading(getActivity());
    }

    @Override
    public void showLoading(String content) {
        mStatusManager.showLoading(getActivity(), content);
    }

    @Override
    public void hideLoading() {
        mStatusManager.hideLoading();
    }

    /*
     * 加载完成
     */
    @Override
    public void onLoadComplete() {
        mStatusManager.showComplete();
    }

    /*
     *设置为空状态
     */
    public void onLoadEmpty() {
        mStatusManager.showEmpty(mRootView);
    }

    /*
     *设置为加载错误的状态
     */
    public void onLoadError() {
        mStatusManager.showError(mRootView);
    }

    /*
     *设置为加载错误的状态
     * 带点击重试的回调
     */
    public void onLoadError(LoadHintLayout.PageRetryClickListener pageRetryClickListener) {
        mStatusManager.showError(mRootView);
        mStatusManager.setPageRetryClickListener(pageRetryClickListener);
    }

    /*
       显示其他样式的布局
     */
    public void showHintLayout(int drawable, CharSequence hint, boolean isCanClick) {
        mStatusManager.showLayout(mRootView, drawable, hint, isCanClick);
    }


    @Override
    public void startActivity(Class targetActivity) {
        mContext.startActivity(new Intent(mContext, targetActivity));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelAllRequest();
    }


    public void addDisposables(Disposable disposable) {
        try {
            if (disposables != null) {
                disposables.add(disposable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelAllRequest() {
        try {
            if (disposables != null) {
                disposables.clear();
            }
            if (presenter != null) {
                presenter.detach();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MyApplication getApp() {
        if (null == getActivity()) {
            return null;
        }
        return (MyApplication) getActivity().getApplication();
    }

    @Override
    public RequestUrlUtils getRequest() {
        return RequestUrlUtils.getInstance();
    }

    @Override
    public Activity getActivitys() {
        return this.getActivity();
    }
}
