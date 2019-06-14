package com.ljphawk.arms.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * https://www.cnblogs.com/yueliming/p/3300587.html
 */

public class ThreadUtil {

//    private static Executor sExecutor = Executors.newCachedThreadPool();
    private static Executor sExecutor = Executors.newFixedThreadPool(20);
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static void runOnSubThread(Runnable runnable) {
        sExecutor.execute(runnable);
    }

    public static void runOnUiThread(Runnable runnable) {
        sHandler.post(runnable);
    }
}
