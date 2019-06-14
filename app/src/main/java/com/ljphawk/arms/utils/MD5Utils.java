package com.ljphawk.arms.utils;


import java.security.MessageDigest;

/*
 *@创建者       L_jp
 *@创建时间     2018/11/5 10:51.
 *@描述
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述
 */
public class MD5Utils {

    public static String getMd5Pwd(String key) {
        StringBuffer sb = new StringBuffer();
        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //加密内容
            messageDigest.update(key.getBytes());
            //获取加密的数据
            byte[] bytes = messageDigest.digest();

            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toHexString(bytes[i] & 0xFF));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }

}
