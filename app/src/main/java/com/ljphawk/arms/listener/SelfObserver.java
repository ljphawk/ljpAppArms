package com.ljphawk.arms.listener;


import com.ljphawk.arms.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/*
 *@创建者       L_jp
 *@创建时间     2018/8/29 19:43.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */
public abstract class SelfObserver<T> implements Observer<T> {


    public abstract void onSubscribe(Disposable d);

    public abstract void onNext(T t);

    public abstract void onError(Throwable e);


    @Override
    public void onComplete() {

    }

    protected void netErrorToast() {
        ToastUtils.showToast("网络请求失败,请重试");
    }

    protected void dataNullToast() {
        ToastUtils.showToast("请求数据异常,请重试");
    }
}



