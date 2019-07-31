package com.ljphawk.arms.http;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/*
 *@创建者       L_jp
 *@创建时间     2019/7/29 15:05.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */

public interface HttpServiceApi {

    //缓存管理类的统一get请求, 动态传入url ，可以在已经定义base Url的情况下被解析的
    @GET
    retrofit2.Call<ResponseBody> getData(@Url String url);

    //下载文件
    @Streaming //大文件时要加不然会OOM
    @GET
    Flowable<ResponseBody> downloadFile(@Url String fileUrl);

}
