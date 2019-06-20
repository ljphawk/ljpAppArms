package com.ljphawk.arms.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.ljp.base.utils.CommonUtils;

public class PackageUtil {

	private static final String TAG = "PackageUtil";


	//获取版本名2.4.0
	public static String getVersionName(Context context, String packageName){
		//包管理器
				PackageManager packageManager = context.getPackageManager();
				//参数一：要获取谁的信息就传谁的包名，参数二：标记,获取什么数据就给什么标记，给0，只获取最基本信息
				try {
					PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
					String versionName = packageInfo.versionName;
					return versionName;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return "未知版本";
	}
	//获取版本号 319
	public static int getVersionCode(Context context, String packageName){
		//包管理器
				PackageManager packageManager = context.getPackageManager();
				//参数一：要获取谁的信息就传谁的包名，参数二：标记,获取什么数据就给什么标记，给0，只获取最基本信息
				try {
					if (!CommonUtils.StringHasValue(packageName)) {
						packageName = context.getPackageName();
					}
					PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
					int versionCode = packageInfo.versionCode;
					Log.d(TAG, "versionCode = " + versionCode);
					return versionCode;
				} catch (Exception e) {
					e.printStackTrace();
					Log.d(TAG, "exception = " + e.getMessage());
				}
				return 1;
	}
}
