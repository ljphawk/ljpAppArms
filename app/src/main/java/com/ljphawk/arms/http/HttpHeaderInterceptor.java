package com.ljphawk.arms.http;



/*
 *@创建者       L_jp
 *@创建时间     2018/8/24 15:46.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */

import android.os.Build;

import com.ljphawk.arms.application.MyApplication;
import com.ljphawk.arms.utils.PackageUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHeaderInterceptor implements Interceptor {

    public HttpHeaderInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
//        String headUserId = UserUtils.getUserId(MyApplication.mContext);
//        String headToken = UserUtils.getUserToken(MyApplication.mContext);
        String headUserId = "";
        String headToken = "";

        Request request = original.newBuilder()
                .addHeader("Cookie", "USERID=" + headUserId
                        + ";TOKEN=" + headToken
                        + ";device=" + Build.MODEL
                        + ";app_name=android"
                        + ";app_version=" + PackageUtil.getVersionCode(MyApplication.mContext, null))
                .build();

        return chain.proceed(request);
    }
}
