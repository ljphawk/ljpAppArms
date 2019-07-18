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

public interface BaseRefreshView extends BaseView {

    void doneRefresh();

    /**
     * 完成加载更多
     */
    void doneLoadMore();

    /**
     *  完成加载更多
     * @param success 是否成功
     */
    void doneLoadMore(boolean success);


    /**
     *  完成加载更多
     * @param success 是否成功
     * @param noMoreData 是否无数据
     */
    void doneLoadMore(boolean success, boolean noMoreData);

}
