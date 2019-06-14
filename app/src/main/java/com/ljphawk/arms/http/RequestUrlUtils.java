package com.ljphawk.arms.http;



/*
 *@创建者       L_jp
 *@创建时间     2018/8/24 14:56.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RequestUrlUtils {

    private HttpServiceApi mHttpServiceInstance;

    public RequestUrlUtils() {
        if (mHttpServiceInstance == null) {
            synchronized (RequestUrlUtils.class) {
                if (mHttpServiceInstance == null) {
                    mHttpServiceInstance = RetrofitUtil.getHttpServiceInstance();
                }
            }
        }
    }

    public Observable<ResponseBody> downloadFile(String fileUrl) {
        return mHttpServiceInstance.downloadFile(fileUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
