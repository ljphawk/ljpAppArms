package com.ljphawk.arms.utils;



/*
 *@创建者       L_jp
 *@创建时间     2018/7/10 11:45.
 *@描述         ${""}
 *
 *@更新者         $Author$
 *@更新时间         $Date$
 *@更新描述         ${""}
 */

import android.util.Log;

import com.ljphawk.arms.application.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.Locale;

import okhttp3.ResponseBody;

public class FileUtils {

    public static File appRootPath = MyApplication.mContext.getFilesDir();
    public static String sdAppRootPath2 = "/sdcard/woniudanci";
//    public static String sdAppRootPath = MyApplication.mContext.getExternalFilesDir(null).getPath();


    public FileUtils() {
//        handlerSdCacheNoExists();
    }

    public static boolean isFileAndExists(String path) {
        if (!CommonUtils.StringHasValue(path)) {
            return false;
        }
        File file = new File(path);
        if (file.isFile() && file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isFileAndExists(File file) {
        return isFileAndExists(file.getPath());
    }

    public static void deleteFile(String filePath) {
        boolean exists = isFileAndExists(filePath);
        if (exists) {
            File file = new File(filePath);
            file.delete();
        }
    }

    public static boolean copyFile(String scrPath, String targetPath) {
        if (!CommonUtils.StringHasValue(targetPath) || !CommonUtils.StringHasValue(scrPath)) {
            return false;
        }

        File dest = new File(targetPath);
        if (dest.exists()) {
            dest.delete(); // delete file
        }
        try {
            dest.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileChannel srcChannel;
        FileChannel dstChannel;

        try {
            srcChannel = new FileInputStream(scrPath).getChannel();
            dstChannel = new FileOutputStream(dest).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), dstChannel);
            srcChannel.close();
            dstChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static final long MB = 1024 * 1024;

    public static String getSizeByUnit(double size) {

        if (size == 0) {
            return "0K";
        }
        if (size >= MB) {
            double sizeInM = size / MB;
            return String.format(Locale.getDefault(), "%.1f", sizeInM) + "M";
        }
        double sizeInK = size / 1024;
        return String.format(Locale.getDefault(), "%.1f", sizeInK) + "K";
    }

    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }


    public static String fileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + units[digitGroups];
    }

    public static String fileSize(String path) {
        if (CommonUtils.StringHasValue(path)) {
            if (isFileAndExists(path)) {
                File file = new File(path);
                return fileSize(file.length());
            }
        }
        return "";
    }

    public static String getParentFolderName(String path) {
        String sp[] = path.split("/");
        return sp[sp.length - 2];
    }

    /**
     * 重命名文件
     *
     * @param oldPath 原来的文件地址
     * @param newPath 新的文件地址
     */
    public static boolean renameFile(String oldPath, String newPath) {
        try {
            File oleFile = new File(oldPath);
            File newFile = new File(newPath);
            //执行重命名
            return oleFile.renameTo(newFile);
        } catch (Exception e) {
            Log.d("", "renameFile: ");
        }
        return false;
    }


    /**
     *
     * @param path  获取文件去后缀的名字
     * @return
     */
    public static String getFileName(String path) {
        if (!isFileAndExists(path)) {
            return "";
        }

        try {
            int start = path.lastIndexOf("/");
            int end = path.lastIndexOf(".");
            if (start != -1 && end != -1) {
                return path.substring(start + 1, end);
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String saveDownFile(ResponseBody responseBody, String savePath) {
        if (responseBody == null) {
            return savePath;
        }

        File file = new File(savePath);
        if (file.exists()) {
            file.delete();
        }

        FileOutputStream os;
        try {
            InputStream is = responseBody.byteStream();
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024 * 1024];

            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savePath;
    }
}
