package com.ljphawk.arms.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ljphawk.arms.application.MyApplication;

import java.util.HashSet;
import java.util.Set;

/**
 * sp的工具类
 * by gezhipeng
 */
public class SPUtils {
    //存入boolean

    private static String SP_NAME;

    public SPUtils() {
        SP_NAME = "woniudanci";
    }

    public SPUtils(String spName) {
        SP_NAME = spName;
    }


    /**
     * 在sp中存入boolean值
     *
     * @param toggle  boolean值
     */
    public static boolean saveBoolean(String key, boolean toggle) {
        SharedPreferences preferences = MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
        Editor edit = preferences.edit();
        edit.putBoolean(key, toggle);
        return edit.commit();
    }

    //读取boolean状态,默认为true
    public static boolean getBoolean_true(String key) {
        SharedPreferences preferences = MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
        return preferences.getBoolean(key, true);

    }

    //读取boolean状态,默认为false
    public static boolean getBoolean_false(String key) {
        SharedPreferences preferences = MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
        return preferences.getBoolean(key, false);

    }

    //1. 保存String类型数据
    public static boolean saveString(String key, String value) {
        SharedPreferences preferences = MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
        Editor edit = preferences.edit();
        edit.putString(key, value);
        return edit.commit();
    }


    //获取String数据
    public static String getString(String key) {
        SharedPreferences preferences = MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
        return preferences.getString(key, "");
    }

    //1. 保存Long类型数据
    public static boolean saveLong(String key, Long value) {
        SharedPreferences preferences = MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
        Editor edit = preferences.edit();
        edit.putLong(key, value);
        return edit.commit();
    }


    //获取Long数据
    public static Long getLong(String key) {
        SharedPreferences preferences = MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
        return preferences.getLong(key, 0);
    }

    //1. 保存int类型数据
    public static boolean saveInt(String key, int value) {
        SharedPreferences preferences = MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
        Editor edit = preferences.edit();
        edit.putInt(key, value);
        return edit.commit();
    }


    //获取int数据
    public static int getInt(String key) {
        SharedPreferences preferences = MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
        return preferences.getInt(key, -1);
    }

    //1. 保存set类型数据
    public static boolean saveSet(String key, Set<String> value) {
        SharedPreferences preferences = MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
        Editor edit = preferences.edit();
        edit.putStringSet(key, value);
        return edit.commit();
    }

    //获取set数据
    public static Set<String> getSet(String key) {
        SharedPreferences preferences = MyApplication.mContext.getSharedPreferences(SP_NAME, Context.MODE_MULTI_PROCESS);
        return preferences.getStringSet(key, new HashSet<String>());
    }


}
