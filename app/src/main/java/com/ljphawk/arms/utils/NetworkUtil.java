package com.ljphawk.arms.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ljphawk.arms.application.MyApplication;

/*
 *@创建者       L_jp
 *@创建时间     2019/7/29 15:05.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class NetworkUtil {

    /**
     * 检测当前网络状态
     *
     * @return 当且仅当已经连接或者正在连接网络时返回true, 否则返回false
     */
    public static boolean isNetConnected() {
        return isNetConnected(MyApplication.mContext);
    }

    private static boolean isNetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == cm) {
            return false;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (null != info) && info.isAvailable() && info.isConnected();
    }
}
