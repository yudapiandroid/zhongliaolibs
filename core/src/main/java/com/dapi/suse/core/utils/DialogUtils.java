package com.dapi.suse.core.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


import java.lang.reflect.Field;


/**
 * 探出框
 * <p>
 * Created by dapi on 2016/10/11.
 */
public class DialogUtils {


    public interface OnOkOrCancel {
        public void ok();
        public void cancel();
    }


    /**
     * 拨打电话
     */
    public static android.support.v7.app.AlertDialog.Builder callDialog(final Context context, final String phone) {
        return callDialog(context,phone,"联系我们","欢迎拨打电话联系客户");
    }


    public static android.support.v7.app.AlertDialog.Builder callDialog(final Context context, final String phone, String title, String message) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent()
                        .setAction(Intent.ACTION_CALL)
                        .setData(Uri.parse("tel:" + phone)));
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        return builder;
    }


    /**
     * 前往应用的权限设置界面
     *
     * @param context
     *
     * @return
     */
    public static android.support.v7.app.AlertDialog.Builder toSetApp(final Context context) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("设置权限");
        builder.setMessage("前往设置界面并开启权限请求");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri packageUri = Uri.parse("package:" + "com.huayizhiao.huitelong");
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
        return builder;
    }


    public static void showNormalDialog(Context context, String message, DialogInterface.OnClickListener confirmListener){
        normalDialog(context,"提示信息",message,confirmListener).show();
    }


    /**
     *
     * 显示是否放弃操作的dialog
     *
     */
    public static void showAbandonDialog(Context context, DialogInterface.OnClickListener confirmListener){
        normalDialog(context,"提示信息","是否放弃本次操作?",confirmListener).show();
    }



    /**
     *
     * 显示WiFi Dialog
     *
     * @param context
     * @param confirmListener
     */
    public static void showWifiDialog(Context context, DialogInterface.OnClickListener confirmListener){
        normalDialog(context,"提示信息","现在不是WIFI网路是否继续？",confirmListener).show();
    }


    public static void showDeleteDialog(Context context, DialogInterface.OnClickListener confirmListener){
        normalDialog(context,"提示信息","确定删除？",confirmListener).show();
    }


    public static void showConfrimDialog(Context context, String message, DialogInterface.OnClickListener listener){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("提示信息");
        builder.setMessage(message);
        builder.setPositiveButton("确定", listener);
        builder.setCancelable(false);
        builder.show();
    }


    /**
     *
     * 通用提示窗口
     */
    public static android.support.v7.app.AlertDialog.Builder normalDialog(final Context context, String title, String message, DialogInterface.OnClickListener listener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", listener);
        builder.setCancelable(false);
        return builder;
    }


    /**
     *
     *  按钮文字的 Dialog
     *
     */
    public static void showNormalDialog(Context context, String title, String message, String cancelStr, String confirmStr, final DialogInterface.OnClickListener confirmListener){

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setNegativeButton(cancelStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton(confirmStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmListener.onClick(null,0);
            }
        });

        builder.setCancelable(false);
        builder.show();

    }


    public static android.support.v7.app.AlertDialog.Builder normalDialog(final Context context, String title, String message,
                                                                          DialogInterface.OnClickListener listener,
                                                                          final DialogInterface.OnClickListener cancelListener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("取消", cancelListener);
        builder.setPositiveButton("确定", listener);
        builder.setCancelable(false);
        return builder;
    }

}
