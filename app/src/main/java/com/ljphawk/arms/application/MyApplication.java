package com.ljphawk.arms.application;


import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/*
 *@创建者       L_jp
 *@创建时间     2019/6/14 13:44.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class MyApplication extends Application {

    public static Context mContext;
    public static Boolean isDebug = false;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        Fresco.initialize(this);
    }
}
