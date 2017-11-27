package com.mylhyl.acp;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.LeeTong on 2017/8/24.
 * 自定义确认权限选择框
 */

public class PermissionDialog extends Dialog implements View.OnClickListener {


    private Context mContext;

    private TextView tvFunctionAuthorization;
    private TextView tvAuthorizationUse;

    private ImageView ivCloseDialog;
    private LinearLayout llPermissionsImg;
    private Button btnDetermine;

    private OnClickListener positiveClickListener;
    private OnClickListener negativeClickListner;


    private String msgTitle;
    private ArrayList<Integer> imgResource;

    public PermissionDialog(Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
        imgResource = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissions_dialog_view);
        setCanceledOnTouchOutside(false);
        initView();
    }


    private void initView() {
        tvFunctionAuthorization = (TextView) findViewById(R.id.tv_function_authorization);
        tvAuthorizationUse = (TextView) findViewById(R.id.tv_authorization_use);
        ivCloseDialog = (ImageView) findViewById(R.id.iv_close_dialog);
        llPermissionsImg = (LinearLayout) findViewById(R.id.ll_permissions_img_layout);
        btnDetermine = (Button) findViewById(R.id.btn_permissions_determine);
    }


    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(PermissionDialog mDialog) {
        //设置标题
        if (!TextUtils.isEmpty(mDialog.msgTitle)) {
            String msgTitle = mDialog.msgTitle;
            tvFunctionAuthorization.setText(msgTitle.substring(0, msgTitle.indexOf("，") + 1));
            tvAuthorizationUse.setText(msgTitle.substring(msgTitle.indexOf("，") + 1, msgTitle.length()));
        }

        //设置显示图片
        if (null != mDialog.imgResource && mDialog.imgResource.size() > 0) {
            llPermissionsImg.setVisibility(View.VISIBLE);
            for (int i = 0; i < mDialog.imgResource.size(); i++) {
                ImageView imageView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.permissions_dialog_img, llPermissionsImg, false);
                imageView.setImageResource(mDialog.imgResource.get(i));
                llPermissionsImg.addView(imageView);
            }
        }

        //关闭Dialog
        ivCloseDialog.setOnClickListener(this);
        //确定
        btnDetermine.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_close_dialog) {
            negativeClickListner.onClick(this, DialogInterface.BUTTON_POSITIVE);

        }
        if (i == R.id.btn_permissions_determine) {
            positiveClickListener.onClick(this, DialogInterface.BUTTON_NEGATIVE);
        }
    }


    public static class Builder {

        private PermissionDialog pDialog;
        private List<Integer> imgResource = new ArrayList<>();

        public Builder(Context context) {
            pDialog = new PermissionDialog(context);
        }

        public Builder setMsgTitle(String msgTitle) {
            pDialog.msgTitle = msgTitle;
            return this;
        }

        public Builder setPermissions(String[] permisstions) {
            for (int i = 0; i < permisstions.length; i++) {
                if (permisstions[i].equals(Manifest.permission.CAMERA)) {
                    imgResource.add(R.mipmap.qx_camera);
                }
                if (permisstions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    imgResource.add(R.mipmap.qx_location);
                }
                if (permisstions[i].equals(Manifest.permission.CALL_PHONE)) {
                    if(!imgResource.contains(R.mipmap.qx_phone)){
                        imgResource.add(R.mipmap.qx_phone);
                    }
                }
                if(permisstions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE) || permisstions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    if(!imgResource.contains(R.mipmap.qx_document)){
                        imgResource.add(R.mipmap.qx_document);
                    }
                }
            }
            pDialog.imgResource.addAll(imgResource);
            return this;
        }

        public Builder setPositiveClickListener(OnClickListener clickInterface) {
            pDialog.positiveClickListener = clickInterface;
            return this;
        }

        public Builder setNegativeClickListner(OnClickListener clickInterface) {
            pDialog.negativeClickListner = clickInterface;
            return this;
        }

        public PermissionDialog create() {
            return pDialog;
        }
    }


}
