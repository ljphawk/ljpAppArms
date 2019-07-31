package com.ljphawk.arms.http;


import com.ljphawk.arms.application.MyApplication;
import com.ljphawk.arms.data.CommonURL;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ljp
 */

public class RetrofitUtil {
    private static Retrofit retrofit;

    /**
     * @return retrofit 实例
     */


    public static Retrofit getRetrofitInstance() {


        if (retrofit == null) {
            synchronized (RetrofitUtil.class) {
                if (retrofit == null) {

                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(MyApplication.isDebug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

                    OkHttpClient.Builder builder = new OkHttpClient.Builder();

                    HttpsUtil httpsUtil = new HttpsUtil();

                    builder.sslSocketFactory(httpsUtil.createSSLSocketFactory())
                            .hostnameVerifier(new HttpsUtil.TrustAllHostnameVerifier());
                    OkHttpClient client = builder
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(10, TimeUnit.SECONDS)
                            .addInterceptor(logging)
                            .addInterceptor(new HttpHeaderInterceptor())
                            .build();


                    retrofit = new Retrofit.Builder()
                            .client(client)
//                            .client(new OkHttpClient())//默认10秒
                            .baseUrl(CommonURL.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }



    /**
     * @return 接口实例
     *
     */
    public static HttpServiceApi getHttpServiceInstance() {

        return getRetrofitInstance().create(HttpServiceApi.class);
    }

}


