package com.raise.utils;

import android.os.Environment;

import com.raise.application.App;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class SdcardUtils {

    public static String getAppPath() {
        if (isSdCardExist()) {
            File app_path = new File(getSdCardPath() + File.separator + App.APP_NAME);
            if (!app_path.exists()) {
                app_path.mkdirs();
            }
            return app_path.getAbsolutePath();
        }
        return null;
    }


    /**
     * 返回的path:/storage/emulated/0
     *
     * @return null if the sdCard is not find
     */
    public static String getSdCardPath() {
        if (isSdCardExist()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    private static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     *
     * @param file_path 文件的绝对路径
     * @return
     */
    public static byte[] getByteArrayByPath(String file_path) {
        File file = new File(file_path);
        byte[] result = null;
        if (file.exists() && file.isFile()) {
            FileInputStream fis = null;
            ByteArrayOutputStream bos = null;
            try {
                fis = new FileInputStream(file);
                bos = new ByteArrayOutputStream();
                int len = 0;
                byte[] bytes = new byte[1024];
                while ((len = fis.read(bytes)) != -1) {
                    bos.write(bytes, 0, len);
                }
                result = bos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
        return null;
    }


}
