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

public interface BaseView {


    void showToast(String content);

    void startActivity(Class targetActivity);

    void showLoading();

    void showLoading(String content);

    void hideLoading();
}
