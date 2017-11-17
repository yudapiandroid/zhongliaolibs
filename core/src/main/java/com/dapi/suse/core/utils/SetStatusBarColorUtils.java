package com.dapi.suse.core.utils;


import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;


/**
 * 状态栏着色工具类 (API >= 21)
 *
 *
 * Created by Elimenzo on 2016/10/10.
 *
 *
 */
public class SetStatusBarColorUtils {

    /**
     * 自定义颜色(自己传参定义颜色)
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static public void setStatusColor(Activity activity, String color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window win = activity.getWindow();
            win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            win.setStatusBarColor(Color.parseColor(color));
        }
    }
}