package com.dapi.suse.zhongliaolibs;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.suse.dapi.views.modelview.DateTimeChoseView;

import in.srain.cube.views.ptr.PtrRecyclerView;

public class MainActivity extends AppCompatActivity {

    private PtrRecyclerView view;

    private DateTimeChoseView choseView;
    private LinearLayout rootLinearLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choseView = (DateTimeChoseView) findViewById(R.id.content);
        rootLinearLayout = (LinearLayout) findViewById(R.id.root);
        DateTimeChoseView timeChoseView = new DateTimeChoseView(this,"title","hint","yyyy-mm-dd",true,2018,2018,DateTimeChoseView.CHOSE_DATA);
        rootLinearLayout.addView(timeChoseView);
    } // end m

}