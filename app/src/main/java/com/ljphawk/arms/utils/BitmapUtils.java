package com.ljphawk.arms.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 *
 */

public class BitmapUtils {


    /**
     * 文件转换为Bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap fileToBitmap(String filePath) {

        try {
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Bitmap转成文件
     */
    public static String BitmapToFile(Bitmap bitmap, File file) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getPath();
    }


}


