package com.ljphawk.arms.base;



/*
 *@创建者       L_jp
 *@创建时间     2018/7/5 10:12.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */


import android.app.Activity;
import android.content.Context;

import com.ljphawk.arms.application.MyApplication;
import com.ljphawk.arms.http.RequestUrlUtils;
import com.ljphawk.arms.utils.ToastUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<T> {
    public T mvpView;
    public Context mContext;
    public static final String TAG = "BasePresenter";
    public RequestUrlUtils mRequestUrlUtils;
    protected CompositeDisposable disposables = new CompositeDisposable();

    void attach(Context context, T view) {
        this.mvpView = view;
        this.mContext = context;
    }

    void detach() {
        cancelAllRequest();
        this.mvpView = null;
    }

    void showToast(String content) {
        ToastUtils.showToast(content);
    }

    void setRequestUrlUtils(RequestUrlUtils requestUrlUtils) {
        this.mRequestUrlUtils = requestUrlUtils;
    }

//    protected void umOnEvent(String eventId) {
//        UmengEventTj.onEvent(mContext, eventId);
//    }


    void addDisposables(Disposable disposable) {
        try {
            if (disposables != null) {
                disposables.add(disposable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cancelAllRequest() {
        try {
            if (disposables != null) {
                disposables.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void postCatchedException(Throwable throwable) {
//        if (!MyApplication.isDebug) {
//            CrashReport.postCatchedException(throwable);
//        }
//    }

    public MyApplication getApp() {
        if (mContext == null) {
            return null;
        }
        return (MyApplication) ((Activity) mContext).getApplication();
    }
}
