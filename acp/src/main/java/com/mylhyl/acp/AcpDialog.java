package com.mylhyl.acp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by Mr.LeeTong on 2017/8/31.
 */

public class AcpDialog {


    private static final int REQUEST_CODE_PERMISSION = 0x38;
    private static final int REQUEST_CODE_SETTING = 0x39;
    private static final String DEF_RATIONAL_MESSAGE = "此功能需要您授权，否则将不能正常使用。";
    private static final String DEF_DENIED_MESSAGE = "此功能需要您手动授权，确定后跳转至设置界面。";


    /**
     * 申请权限
     *
     * @param activity
     * @param permisstions
     */
    public static void showPermissionDialog(final Activity activity, String[] permisstions) {
        new PermissionDialog.Builder(activity)
                .setMsgTitle(DEF_RATIONAL_MESSAGE)
                .setPermissions(permisstions)
                .setPositiveClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Acp.getInstance(activity).getAcpManager().checkRequestPermissionRationale(activity);
                    }
                })
                .setNegativeClickListner(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(activity, "取消授权", Toast.LENGTH_SHORT).show();
                    }
                })
                .create().show();
    }


    /**
     * 跳转设置
     *
     * @param activity
     * @param permisstions
     */
    public static void showSettingDialog(final Activity activity, String[] permisstions) {
        new PermissionDialog.Builder(activity)
                .setMsgTitle(DEF_DENIED_MESSAGE)
                .setPermissions(permisstions)
                .setPositiveClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(activity != null){
                            startSetting(activity);
                            activity.finish();
                        }
                    }
                })
                .setNegativeClickListner(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(AcpSignle.cb != null){
                            AcpSignle.cb.error();
                        }
                        if(activity != null){
                            activity.finish();
                        }
                    }
                })
                .create().show();
    }


    /**
     * 跳转到设置界面
     */
    private static void startSetting(Activity activity) {
        if (MiuiOs.isMIUI()) {
            Intent intent = MiuiOs.getSettingIntent(activity);
            if (MiuiOs.isIntentAvailable(activity, intent)) {
                activity.startActivityForResult(intent, REQUEST_CODE_SETTING);
                return;
            }
        }
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, REQUEST_CODE_SETTING);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                activity.startActivityForResult(intent, REQUEST_CODE_SETTING);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }


}
