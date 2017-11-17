package com.dapi.suse.core.utils;

/**
 * <pre>
 *  -_-!
 *
 *      Nothing is certain in this life.
 *      The only thing i know for sure is that.
 *      I love you and my life.
 *      That is the only thing i know.
 *      have a good day   :)
 *
 *                                              2017/5/16
 *
 *      (*≧▽≦)ツ┏━┓⌒ 〓▇3:) 睡什么睡，起来嗨！
 *
 * </pre>
 */

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 跟App相关的辅助类
 *
 *
 *
 */
public class AppUtils
{

    private AppUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取应用程序名称
     */
    public synchronized static String getAppName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public synchronized static String getVersionName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    public synchronized  static int getVersionCode(Context context){
            try
            {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(
                        context.getPackageName(), 0);
                return packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return 0;
    }


    /**
     * 是否debug模式
     *
     * @param context
     * @return
     */
    public synchronized static boolean isDebugMode(Context context) {
        if (context == null) {
            return false;
        }
        ApplicationInfo info = context.getApplicationInfo();
        return (0 != ((info.flags) & ApplicationInfo.FLAG_DEBUGGABLE));
    }


    /**
     *
     * 获取TracerPid
     *
     */
    public synchronized static int getTraceId(){
        int pid = android.os.Process.myPid();
        String info = null;
        File file = new File("/proc/" + pid + "/status");
        FileInputStream fileInputStream = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(file);
            reader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(reader);
            while((info = bufferedReader.readLine()) != null){
                if(info.contains("TracerPid")){
                    if(info.contains(":")){
                        String[] infoTemp = info.split(":");
                        if(infoTemp != null && infoTemp.length == 2){
                            int number = -1;
                            number = Integer.valueOf(infoTemp[2].trim());
                            return number;
                        }
                    }
                }
            }
        } catch (Exception e) {}finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }
}
