package com.ljphawk.arms.base;



/*
 *@创建者       L_jp
 *@创建时间     2018/7/5 10:11.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */

import android.app.Activity;
import android.content.Context;

import com.ljphawk.arms.http.RequestUrlUtils;

public interface BaseView {


    void showToast(String content);

    void startActivity(Class<? extends Activity> targetActivity);

    /**
     * 获取上下文
     * @return
     */
    Context getContext();

    /**
     * 获取请求
     * @return
     */
    RequestUrlUtils getRequest();

    /**
     * 加载中
     */
    void showLoading();

    /**
     * 加载中
     */
    void showLoading(String content);

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 加载完成
     */
    void onLoadComplete();

}
