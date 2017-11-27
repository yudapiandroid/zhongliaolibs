package com.mylhyl.acp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import java.util.LinkedList;
import java.util.List;

public class AcpActivity extends AppCompatActivity {


    private static final int REQUEST_CODE_PERMISSION = 0x0011;//申请权限时候的请求码
    private static final int REQUEST_CODE_SETTING = 0x0012;//设置权限

    private static final String DEF_RATIONAL_MESSAGE = "此功能需要您授权，否则将不能正常使用。";
    private static final String DEF_DENIED_MESSAGE = "此功能需要您手动授权，确定后跳转至设置界面。";

    private static final String LOG_TAG = "CertificationActivity";
    private String[] permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不接受触摸屏事件
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        permissions = getIntent().getExtras().getStringArray("EXTRA_DATA");
        if(permissions == null || permissions.length == 0){
            finish();
            return;
        }
        if (savedInstanceState == null)
            preShowPermisstionDialog(permissions);
    }

    private void preShowPermisstionDialog(String[] permissions) {
        boolean rationale = false;
        //如果有拒绝则提示申请理由提示框，否则直接向系统请求权限
        for (String permission : permissions) {
            rationale = rationale || shouldShowRequestPermissionRationale(this, permission);
        }
        if(rationale){
            showPermisstionDialog(permissions);
        }else{
            showToSettingDialog(permissions);
        }
    }

    private void showPermisstionDialog(final String[] ps) {
        new PermissionDialog.Builder(this).setMsgTitle(DEF_RATIONAL_MESSAGE)
                .setPermissions(ps)
                .setPositiveClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        preToGetPermisstions(ps);
                    }
                })
                .setNegativeClickListner(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        preToGetPermisstions(ps);
                    }
                })
                .create().show();
    }


    /**
     *
     * 申请权限
     *
     * @param ps
     */
    private void preToGetPermisstions(String[] ps) {
        List<String> deniedPermissions = AcpSignle.getDeniedPermissions(ps, this);
        if(deniedPermissions == null || deniedPermissions.size() == 0){
            finish();
            return;
        }
        requestPermissions(this,permissions,REQUEST_CODE_PERMISSION);
    }//end m


    /**
     * 向系统请求权限
     *
     * @param activity
     * @param permissions
     * @param requestCode
     */
    void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    /**
     *
     *  跳转到设置界面的dialog
     *
     * @param permissions
     */
    private void showToSettingDialog(String[] permissions) {
        /*if(AcpSignle.cb != null){
            AcpSignle.cb.error();
        }*/
        AcpDialog.showSettingDialog(this,permissions);
    }

    /**
     * 检查权限是否存在拒绝不再提示
     *
     * @param activity
     *
     * @param permission
     *
     * @return
     *
     */
    boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        boolean result = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                LinkedList<String> grantedPermissions = new LinkedList<>();
                LinkedList<String> deniedPermissions = new LinkedList<>();
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        grantedPermissions.add(permission);
                    else deniedPermissions.add(permission);
                }
                //全部允许才回调 onGranted 否则只要有一个拒绝都回调 onDenied
                if (!grantedPermissions.isEmpty() && deniedPermissions.isEmpty()) {
                    if (AcpSignle.cb != null)
                        AcpSignle.cb.suceess();
                } else{
                    if(AcpSignle.cb != null){
                        AcpSignle.cb.error();
                    }
                }
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_SETTING){
            AcpSignle.requestPermisstions(this,permissions,AcpSignle.cb);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //AcpSignle.cb = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("CertificationActivity","onNewIntent.");
    }
}
