package com.dapi.suse.core.utils;



/**
 * Created by dapi on 2016/9/29.
 */
public class Log {

    public static final boolean show = true;

    public static void i(String tag, String message) {
        if(show){
            android.util.Log.i(tag,message);
        }
    }

    public static void e(String tag, String message) {
        if(show){
            android.util.Log.e(tag,message);
        }
    }

    public static void v(String tag, String message) {
        if(show){
            android.util.Log.v(tag,message);
        }
    }

    public static void d(String tag, String message) {
        if(show){
            android.util.Log.d(tag,message);
        }
    }

}
