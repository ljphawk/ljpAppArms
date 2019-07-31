package com.ljphawk.arms.data;



/*
 *@创建者       L_jp
 *@创建时间     2018/8/23 18:05.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */

import com.ljphawk.arms.application.MyApplication;
import com.ljphawk.arms.utils.SPUtils;


public class CommonURL {
    /**
     * api的接口域名
     */
    //BASE_URL
    public static final String BASE_URL = getBaseUrl();
    //正式地址
    private static final String BASE_URL_RELEASE = "https://test-api.woniudanci.com/";
    //测试 1
    private static final String BASE_URL_TEST = "https://test-api.woniudanci.com/";
    //穿透地址
    private static final String BASE_URL_TEST2 = "https://test-api.woniudanci.com/";

    /**
     *html的路径域名
     */
    //BASE_WEB_URL
    private static final String BASE_WEB_URL = getBaseWebUrl();
    //正式
    private static final String BASE_WEB_URL_RELEASE = "https://www.ishiguangji.cn/";
    //测试
    private static final String BASE_WEB_URL_TEST = "https://test-static.ishiguangji.cn/web/";
    //穿透地址
    private static final String BASE_WEB_URL_TEST2 = BASE_URL_TEST2;//穿透地址

    /**
     * 获取h5的baseUrl
     * @return
     */
    private static String getBaseWebUrl() {
        if (!MyApplication.isDebug) {//线上模式
            return BASE_WEB_URL_RELEASE;
        } else {//debug
            int type = SPUtils.getInt(CommData.SPKEY_CHANGE_BASE_URL);
            switch (type) {
                case -1://没有设置过
                    return BASE_WEB_URL_TEST;
                case 0://穿透
                    return BASE_WEB_URL_TEST2;
                case 1://test
                    return BASE_WEB_URL_TEST;
                case 2://release
                    return BASE_WEB_URL_RELEASE;
            }
        }
        return BASE_WEB_URL_RELEASE;
    }

    /**
     * 获取api的baseUrl
     * @return
     */
    private static String getBaseUrl() {
        if (!MyApplication.isDebug) {//线上模式
            return BASE_URL_RELEASE;
        } else {//debug
            int type = SPUtils.getInt(CommData.SPKEY_CHANGE_BASE_URL);
            switch (type) {
                case -1://没有设置过
                    return BASE_URL_TEST;
                case 0://穿透
                    return BASE_URL_TEST2;
                case 1://test
                    return BASE_URL_TEST;
                case 2://release
                    return BASE_URL_RELEASE;
            }
        }
        return BASE_URL_RELEASE;
    }


}
