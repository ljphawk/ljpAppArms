package com.ljphawk.arms.utils;



/*
 *@创建者       L_jp
 *@创建时间     2018/7/9 14:08.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */

import android.text.TextUtils;

import java.util.List;


public class CommonUtils {


    public static boolean StringHasValue(CharSequence content) {
        if (null == content || TextUtils.isEmpty(content)) {
            return false;
        } else {
            return true;
        }
    }


    public static boolean ListHasValue(List list) {
        if (null != list && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
