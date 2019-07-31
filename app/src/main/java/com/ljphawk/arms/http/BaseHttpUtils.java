package com.ljphawk.arms.http;


import com.ljphawk.arms.utils.RxUtils;

import io.reactivex.Flowable;

/*
 *@创建者       L_jp
 *@创建时间     2019/7/29 15:05.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class BaseHttpUtils {


    /**
     * 全量转换
     *
     * @param flowable
     * @param <T>
     * @return
     */
    protected <T> Flowable<T> transformFull(Flowable<T> flowable) {
        return flowable.compose(RxUtils.<T>rxSchedulerHelper());
    }
}
