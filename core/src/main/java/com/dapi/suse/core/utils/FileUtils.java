package com.dapi.suse.core.utils;


import android.content.Context;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 余鑫 on 2017/3/15.
 *
 * 临时文件 工具类
 *
 */
public class FileUtils {

    public static File getTempRootFile(Context context){
        if(context == null){
            return null;
        }
        File file = new File(context.getExternalCacheDir(), context.getPackageName());
        if(file.isDirectory()){
            return file;
        }else{
            if(file.mkdir()){
                return file;
            }else{
                return context.getCacheDir();
            }
        }
    }


    /**
     *
     *
     *
     * 根据名字来获取文件
     *
     *
     * @param fileName
     *
     * @return
     *
     */
    public static File getTempFile(String fileName,Context context){
        return new File(getTempRootFile(context),fileName);
    }


    public static File getTempFile(Context context){
        return new File(getTempRootFile(context),getMd5(String.valueOf(System.currentTimeMillis())));
    }


    public static File getTempFileByUrl(String url,Context context){
        return new File(getTempRootFile(context),getMd5(url));
    }


    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "temp_"+ System.currentTimeMillis();
        }
    }

}
