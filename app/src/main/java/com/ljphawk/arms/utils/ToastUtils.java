package com.ljphawk.arms.utils;


import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.ljp.base.utils.CommonUtils;
import com.ljphawk.arms.application.MyApplication;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/14 15:15.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class ToastUtils {

    private static Toast sToast;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void showToast(final String msg) {
        if (!CommonUtils.StringHasValue(msg)) {
            return;
        }

        if (sToast == null) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                initToast(msg);
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initToast( msg);
                    }
                });
            }
        }
        //判断当前代码是否是主线程
        if (Looper.myLooper() == Looper.getMainLooper()) {
            sToast.setText(msg);
            sToast.show();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    sToast.setText(msg);
                    sToast.show();
                }
            });
        }
    }

    private static void initToast(String msg) {
        sToast = Toast.makeText(MyApplication.mContext, msg, Toast.LENGTH_SHORT);
        sToast.setText(msg);
    }

    public static void destroy(){
        sToast = null;
    }

}
