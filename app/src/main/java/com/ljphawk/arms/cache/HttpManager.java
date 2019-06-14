package com.ljphawk.arms.cache;

import android.content.Context;

import com.ljphawk.arms.http.RetrofitUtil;

import okhttp3.ResponseBody;
import retrofit2.Response;

/*
 *@创建者       L_jp
 *@创建时间     2017/3/17 15:42
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */

public class HttpManager {


    private HttpManager() {
    }

    private static HttpManager sDownManager = new HttpManager();

    public static HttpManager getInstance() {
        return sDownManager;
    }

    //获取网络数据,retrofit的同步请求，异步请求有待封装，先暂时用同步
    public String getData(Context context, String url) {
        try {
            RetrofitUtil.getHttpServiceInstance().getData(url).execute().body();
            Response<ResponseBody> execute = RetrofitUtil.getHttpServiceInstance().getData(url).execute();
            if (execute.isSuccessful()) {
                return execute.body().string();
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
