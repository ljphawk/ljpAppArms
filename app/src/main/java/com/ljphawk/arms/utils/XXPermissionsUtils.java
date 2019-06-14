package com.ljphawk.arms.utils;


import android.app.Activity;

import com.hjq.permissions.XXPermissions;

/*
 *@创建者       L_jp
 *@创建时间     2019/1/8 14:02.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class XXPermissionsUtils {

    //申请存储权限
    public static XXPermissions applyPermissions(Activity activity, String... permissions) {
        return XXPermissions.with(activity).permission(permissions); //不指定权限则自动获取清单中的危险权限;
    }

}
