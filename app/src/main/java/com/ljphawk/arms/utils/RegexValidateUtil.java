package com.ljphawk.arms.utils;



/*
 *@创建者       L_jp
 *@创建时间     2017/4/19 13:47.
 *@描述         使用正则表达式进行表单验证
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexValidateUtil {
    private static boolean flag = false;
    private static String regexs = "";

    public static boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证非空
     *
     * @return
     */
    public static boolean checkNotEmputy(String notEmputy) {
        regexs = "^\\s*$";
        return check(notEmputy, regexs) ? false : true;
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

        return check(email, regex);
    }

    /**
     * 验证手机号码
     *
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189、199
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
//        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
        String regex = "(13\\d|14[57]|15[^4,\\D]|17[13678]|18\\d)\\d{8}|19[9]\\d{8}|170[0589]\\d{7}";
        return check(cellphone, regex);
    }


    /**
     * 验证QQ号码
     *
     * @param QQ
     * @return
     */
    public static boolean checkQQ(String QQ) {
        String regex = "[1-9]\\d{4,11}";
        return check(QQ, regex);
    }
}
