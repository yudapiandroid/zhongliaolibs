package com.mylhyl.acp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 *
 *
 *  单独的权限请求
 */
public class AcpSignle {


    public static RequestCallBack cb;

    /**
     *
     * 权限请求方法
     *
     * @param ps
     */
    public static void requestPermisstions(Context context,String[] ps,RequestCallBack callBack){
        if(ps == null || ps.length == 0){
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (callBack != null)
                callBack.suceess();
            return;
        }
        List<String> mDeniedPermissions = getDeniedPermissions(ps,context);
        //检查如果没有一个拒绝响应 onGranted 回调
        if (mDeniedPermissions.isEmpty()) {
            if (callBack != null)
                callBack.suceess();
            return;
        }
        /**
         * 启动activity 申请权限
         */
        cb = callBack;
        startAcpActivity(context,ps);
    }//end m


    public static List<String> getDeniedPermissions(String[] ps,Context context){
        List<String> mDeniedPermissions = new ArrayList<>();
        List<String> mManifestPermissions = getManifestPermissions(context);
        for (String permission : ps) {
            //检查申请的权限是否在 AndroidManifest.xml 中
            if (mManifestPermissions.contains(permission)) {
                int checkSelfPermission = checkSelfPermission(context, permission);

                //如果是拒绝状态则装入拒绝集合中
                if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                    mDeniedPermissions.add(permission);
                }
            }
        }
        return mDeniedPermissions;
    }//end m

    /**
     *
     *  启动activity去申请权限
     *
     * @param ps
     */
    private static void startAcpActivity(Context context,String[] ps){
        Intent intent = new Intent(context, AcpActivity.class);
        intent.putExtra("EXTRA_DATA", ps);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private static int checkSelfPermission(Context context, String permission) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            int targetSdkVersion = info.applicationInfo.targetSdkVersion;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    return ContextCompat.checkSelfPermission(context, permission);
                } else {
                    return PermissionChecker.checkSelfPermission(context, permission);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ContextCompat.checkSelfPermission(context, permission);
    }


    /**
     *
     * 获取manifest里面声明的权限
     *
     * @param context
     * @return
     */
    private static synchronized List<String> getManifestPermissions(Context context) {
        List<String> ps = new ArrayList<>();
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            String[] permissions = packageInfo.requestedPermissions;
            if (permissions != null) {
                for (String perm : permissions) {
                    ps.add(perm);
                }
            }
        }
        return ps;
    }


    public interface RequestCallBack{
        void suceess();
        void error();
    }

}
