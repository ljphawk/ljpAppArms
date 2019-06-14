package com.ljphawk.arms.cache;

import android.content.Context;
import android.text.TextUtils;

import com.ljphawk.arms.application.MyApplication;
import com.ljphawk.arms.utils.CommonUtils;
import com.ljphawk.arms.utils.GsonUtil;
import com.ljphawk.arms.utils.MD5Utils;
import com.ljphawk.arms.utils.ToastUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
/*
 *@创建者       L_jp
 *@创建时间     2017/3/17 15:43
 *@描述       缓存管理类
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */

public class CacheManager {

    private final String dir;

    private CacheManager() {

        dir = MyApplication.mContext.getFilesDir() + File.separator + "cache";
        File dirFile = new File(dir);
        //手动创建多级目录
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

    }

    private static CacheManager sDownManger;

    public static CacheManager getInstance() {
        if (sDownManger == null) {
            synchronized (CacheManager.class) {
                if (sDownManger == null) {
                    sDownManger = new CacheManager();
                }
            }
        }
        return sDownManger;
    }


    //去缓存获取数据

    /**
     * 根据key返回对应的数据
     * @param key
     * @return
     */
    public String getCacheData(String key) {

        StringBuffer stringBuffer = new StringBuffer();
        FileInputStream fileInputStream = null;
        try {
            File file = new File(dir, getFileName(key));
            fileInputStream = new FileInputStream(file);
            InputStreamReader read = new InputStreamReader(fileInputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                stringBuffer.append(lineTxt);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return stringBuffer.toString();
    }

    public void saveCacheData(String key, String content) {
        try {
            File file = new File(dir, getFileName(key));
            OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            oStreamWriter.append(content);
            oStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getFileName(String key) {

        return MD5Utils.getMd5Pwd(key);
    }

    public File getFilePath(String key) {
        return new File(dir, getFileName(key));
    }

    //请求数据,返回最终的结果,自带缓存功能

    /**
     *
     * @param key key
     * @param clazz   数据的返回类型
     * @return 返回对象
     */
    public Object getBeanData(Context context, String key, Class clazz) {
        //先去网络获取最新的数据
        String content = HttpManager.getInstance().getData(context, key);
        //检测获取的数据
        if (TextUtils.isEmpty(content)) {
            //如果为空,去缓存获取数据
            content = getCacheData(key);
            ToastUtils.showToast("请检查网络连接");
            if (TextUtils.isEmpty(content)) {
                return null;
            }
        } else {
            //如果不为空
            //保存数据，缓存文件统一管理类
            saveCacheData(key, content);
        }

        //到这里了content肯定是有数据的
        //解析数据
        return GsonUtil.parseJsonToBean(content, clazz);
    }


    /**
     *获取保存在本地的数组  自己
     * @param key
     * @param clazz   数据的返回类型
     * @return 返回对象  肯定是加密 需要解密
     */
    public Object getUserData(String key, Class<?> clazz) {

        //如果为空,去缓存获取数据
        String content = getCacheData(key);

        if (!CommonUtils.StringHasValue(content)) {
            return null;
        }

        //到这里了content肯定是有数据的
        //解析数据
        return GsonUtil.parseJsonToBean(content, clazz);
    }


    // 同上边方法，这个返回的是集合
    public <T> List<T> getListBean(Context context, String key, Class<T> clazz) {
        //先去网络获取最近的数据
        String content = HttpManager.getInstance().getData(context, key);
        //检测获取的数据
        if (TextUtils.isEmpty(content)) {
            //如果为空,去缓存获取数据
            content = getCacheData(key);
            if (TextUtils.isEmpty(content)) {
                return null;
            }
        } else {
            //如果不为空
            //保存数据(缓存文件统一管理类)
            saveCacheData(key, content);
        }

        //到这里了content肯定是有数据的
        //解析数据
        return GsonUtil.fromJsonArray(content, clazz);
    }


}
