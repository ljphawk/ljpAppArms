package com.ljphawk.arms.base;

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

import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 *ljp
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    public Context mContext;
    public static String TAG = BaseActivity.class.getSimpleName();
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected CompositeDisposable disposables;
    protected P presenter;
    private final StatusManager mStatusManager = new StatusManager();
    // 根布局
    private View mRootView;

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
            presenter.attach( this);
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
        mStatusManager.showLoading(getActivity(),content);
    }

    @Override
    public void hideLoading() {
        mStatusManager.hideLoading();
    }

    @Override
    public void onLoadComplete() {
        mStatusManager.showComplete();
    }


    public void onLoadEmpty(Object object) {
        mStatusManager.showEmpty(object);
    }


    public void onLoadError(Object object) {
        mStatusManager.showError(object);
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
}
